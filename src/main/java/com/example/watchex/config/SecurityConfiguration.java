package com.example.watchex.config;

import com.example.watchex.common.TokenType;
import com.example.watchex.entity.Token;
import com.example.watchex.entity.User;
import com.example.watchex.service.JwtService;
import com.example.watchex.service.TokenService;
import com.example.watchex.service.UserService;
import com.example.watchex.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Objects;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    private final JwtService jwtService;

    //    private final LogoutHandler logoutHandler;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/user/auth/**", "/bower_components/**", "/component/**", "/static/**", "/resources/**", "/favicon.ico", "/plugins/**",
                        "/ckeditor/**",
                        "/dist/**",
                        "/js/**",
                        "/plugins/**",
                        "/tagsinput/**",
                        "/toastr/**",
                        "/uploads/**",
                        "/vendor/**",
                        "/video/**",
                        "/view/**",
                        "/product/**",
                        "/auth/**"
                )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .loginPage("/auth/login")
                .successHandler((request, response, authentication) -> {
                    OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
                    OAuth2User oauthUser = oauthToken.getPrincipal();
                    String email = oauthUser.getAttribute("email");
                    String name = oauthUser.getAttribute("name");
                    String sub = oauthUser.getAttribute("sub");

                    assert email != null;
                    if (!email.contains("@eaut.edu.vn")) {
                        CommonUtils.setCookie("error_login", "error_login_google");
                        response.sendRedirect("/auth/login");
                    } else {

                        User user = userService.findByEmail(email);
                        if (user == null) {
                            User newuser = new User();
                            newuser.setName(name);
                            newuser.setEmail(email);
                            newuser.setRole("USER");
                            newuser.setProvider("google");
                            newuser.setProvider_id(sub);
                            newuser.setStatus("ACTIVE");
                            userService.save(newuser);
                            user = newuser;
                        } else if (Objects.equals(user.getRole(), "USER") && Objects.equals(user.getStatus(), "INACTIVE")) {
                            user.setProvider("google");
                            user.setProvider_id(sub);
                            user.setStatus("ACTIVE");
                            userService.save(user);
                        } else if (Objects.equals(user.getStatus(), "ACTIVE")) {
                            user.setProvider("google");
                            user.setProvider_id(sub);
                            userService.save(user);
                        }
                        String jwtToken = jwtService.generateToken(user);
                        String refreshToken = jwtService.generateRefreshToken(user);
                        revokeAllUserTokens(user);
                        saveUserToken(user, jwtToken);
                        CommonUtils.setCookie("Authorization", "Bearer " + jwtToken);
                        response.sendRedirect("/");
                    }
                })
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();

        return http.build();
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

}
