package com.example.watchex.controller;

import com.example.watchex.common.TokenType;
import com.example.watchex.dto.JwtResponse;
import com.example.watchex.dto.LoginDto;
import com.example.watchex.dto.RegisterDto;
import com.example.watchex.entity.Token;
import com.example.watchex.entity.User;
import com.example.watchex.response.AuthenticationResponse;
import com.example.watchex.service.EmailSenderService;
import com.example.watchex.service.JwtService;
import com.example.watchex.service.TokenService;
import com.example.watchex.service.UserService;
import com.example.watchex.utils.CommonUtils;
import com.example.watchex.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private TokenService tokenService;
    private final JwtUtils jwtUtil;

    private final JwtService jwtService;

    @Autowired
    private EmailSenderService emailService;

    @ModelAttribute("registerDto")
    public RegisterDto registerDto() {
        return new RegisterDto();
    }

    public AuthController(JwtUtils jwtUtil, JwtService jwtService) {
        this.jwtUtil = jwtUtil;
        this.jwtService = jwtService;
    }

    @GetMapping("auth/login")
    public String loginForm(@NonNull HttpServletRequest request, Model model, LoginDto loginDto) {
        if (CommonUtils.getCookie(request, "Authorization") != null) {
            return "redirect:/";
        }
        model.addAttribute("loginDto", loginDto);
        String errorLogin = CommonUtils.getCookie(request, "error_login");
        if (errorLogin != null) {
            model.addAttribute("message",
                    messageSource.getMessage(errorLogin, new Object[0], LocaleContextHolder.getLocale()));
            CommonUtils.setCookie("error_login", "");
        }

        return "auth/login";
    }

    @PostMapping("auth/login")
    public String login(HttpServletRequest request,
                        @Valid @ModelAttribute("loginDto") LoginDto loginDto,
                        BindingResult result,
                        Model model, RedirectAttributes ra)
            throws Exception {
        AuthenticationResponse jwt;
        User user = userService.findByEmail(loginDto.getEmail());
        if (user == null) {
            result.rejectValue("email", "error.email", "Tài khoản không tồn tại !");
            return "auth/login";
        }
        boolean checkPassword = new BCryptPasswordEncoder().matches(loginDto.getPassword(), user.getPassword());
        if (!checkPassword) {
            result.rejectValue("email", "error.email", "Tài khoản hoặc mật khẩu không chính xác !");
            return "auth/login";
        }
        if (result.hasErrors()) {
            return "auth/login";
        }
        user.setProvider("direct");
        userService.save(user);
        if (Objects.equals(user.getRole(), "USER") && Objects.equals(user.getStatus(), "INACTIVE")) {
            result.rejectValue("email", "error.email", "Tài khoản của bạn chưa được kích hoạt. Vui lòng truy cập lại email để kích hoạt tài khoản !");
           String subject = "Xác thực tài khoản";
           String template = "email/user-register-template";
           model.addAttribute("name", user.getName());
           model.addAttribute("email", user.getEmail());

           emailService.sendEmail(user.getEmail(), subject, template, model);
            return "auth/login";
        }
        if (Objects.equals(user.getStatus(), "SUSPENDED")) {
            result.rejectValue("email", "error.email", messageSource.getMessage("suspended_user_success", new Object[0], LocaleContextHolder.getLocale()));
            return "auth/login";
        } else if (Objects.equals(user.getStatus(), "BANNED")) {
            result.rejectValue("email", "error.email", messageSource.getMessage("banned_user_success", new Object[0], LocaleContextHolder.getLocale()));
            return "auth/login";
        }

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        jwt = AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(user)
                .build();
        CommonUtils.setCookie("Authorization", "Bearer " + jwtToken);
        if (Objects.equals(user.getRole(), "USER")) {
            return "redirect:/device";
        }
        return "redirect:/";
//        return ResponseEntity.ok(new MessageEntity(200, jwt));
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenService.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenService.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenService.saveAll(validUserTokens);
    }

    @GetMapping("auth/register")
    public String registerForm(@NonNull HttpServletRequest request, Model model) {
        if (CommonUtils.getCookie(request, "Authorization") != null) {
            return "redirect:/";
        }
        return "auth/register";
    }

    @PostMapping("auth/register")
    public String register(@Valid @ModelAttribute("registerDto") RegisterDto registerDto, BindingResult result, Model model, RedirectAttributes ra) throws SQLException, IOException, MessagingException {
        if (result.hasErrors()) {
            return "auth/register";
        }
        boolean checkSamePassword = registerDto.getPassword_confirm().equals(registerDto.getPassword());
        if (!checkSamePassword) {
            result.rejectValue("password_confirm", "error.password_confirm", "Mật khẩu xác nhận không chính xác !");
            return "auth/register";
        }
        if (userService.existsByEmail(registerDto.getEmail())) {
            result.rejectValue("email", "error.email", "Email đã được đăng ký !");
            return "auth/register";
        }
        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPhone(registerDto.getPhone());
        user.setPassword(registerDto.getPassword());
        user.setStudent_id(registerDto.getStudentId());
        user.setRole("USER");
        user.setStatus("INACTIVE");
        userService.save(user);

        Token token = new Token();
        token.setToken(jwtUtil.generateToken(user));
        token.setTokenExpDate(jwtUtil.generateExpirationDate());
        token.setUser(user);
        tokenService.createToken(token);
        JwtResponse jwt = new JwtResponse(token.getToken(), user.getEmail());
        String subject = "Xác thực tài khoản";
        String template = "email/user-register-template";

        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        emailService.sendEmail(registerDto.getEmail(), subject, template, model);
        ra.addFlashAttribute("message_success", "Kích hoạt tài khoản thành công. Vui lòng kiểm tra email để kích hoạt tài khoản !");

        return "redirect:/auth/login";
    }

    @GetMapping("auth/logout")
    public String Logout() {
        CommonUtils.setCookie("Authorization", "");
        return "redirect:/";
    }

    @GetMapping("auth/active/{email}")
    public String active(@PathVariable("email") String email, RedirectAttributes ra) {
        User user = userService.findByEmail(email);
        if (Objects.equals(user.getStatus(), "ACTIVE")) {
            ra.addFlashAttribute("message", messageSource.getMessage("user_actived", new Object[0], LocaleContextHolder.getLocale()));
        } else if (Objects.equals(user.getStatus(), "SUSPENDED")) {
            ra.addFlashAttribute("message", messageSource.getMessage("suspended_user_success", new Object[0], LocaleContextHolder.getLocale()));
        } else if (Objects.equals(user.getStatus(), "BANNED")) {
            ra.addFlashAttribute("message", messageSource.getMessage("banned_user_success", new Object[0], LocaleContextHolder.getLocale()));
        } else {
            ra.addFlashAttribute("message_success", messageSource.getMessage("active_user_success", new Object[0], LocaleContextHolder.getLocale()));
            user.setStatus("ACTIVE");
            userService.save(user);
        }
        return "redirect:/auth/login";
    }
}
