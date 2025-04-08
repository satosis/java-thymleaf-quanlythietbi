package com.example.watchex.controller;

import com.example.watchex.dto.UserDto;
import com.example.watchex.entity.User;
import com.example.watchex.service.ContactService;
import com.example.watchex.service.UserService;
import com.example.watchex.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @GetMapping("")
    public String index(Model model) {
        return "user/profile";
    }

    @GetMapping("/edit")
    public String edit(Model model) {
        return "user/edit";
    }

    @PostMapping("/edit")
    public String register(@Valid @ModelAttribute("editProfileDto") UserDto editProfileDto, BindingResult result) throws Exception {
        User user = CommonUtils.getCurrentUser();
        if (result.hasErrors()) {
            return "user/edit";
        }
        if (!Objects.equals(editProfileDto.getAvatar().getOriginalFilename(), "")) {
            CommonUtils.deleteImage(user.getOriginalAvatar());
            String path = CommonUtils.saveImageToStorage("user", editProfileDto.getAvatar());
            user.setAvatar(path);
        }
        if (!Objects.equals(editProfileDto.getPassword(), "")) {
            boolean checkSamePassword = editProfileDto.getRe_password().equals(editProfileDto.getPassword());
            if (!checkSamePassword) {
                result.rejectValue("re_password", "error.re_password", "Mật khẩu xác nhận không chính xác !");
                return "user/edit";
            }
            boolean checkPassword = new BCryptPasswordEncoder().matches(editProfileDto.getOldpassword(), user.getPassword());
            if (!checkPassword) {
                result.rejectValue("oldpassword", "error.oldpassword", "Mật khẩu không chính xác !");
                return "user/edit";
            }
            user.setPassword(editProfileDto.getPassword());
        }
        user.setName(editProfileDto.getName());
        user.setPhone(editProfileDto.getPhone());
        userService.save(user);

        return "redirect:/profile";
    }
}
