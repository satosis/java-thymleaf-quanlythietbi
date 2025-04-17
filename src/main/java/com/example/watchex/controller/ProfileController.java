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


}
