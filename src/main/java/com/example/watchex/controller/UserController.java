package com.example.watchex.controller;

import com.example.watchex.entity.User;
import com.example.watchex.service.DeviceService;
import com.example.watchex.service.UserService;
import com.example.watchex.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;
    @GetMapping("")
    public String get(Model model, @RequestParam Map<String, String> params) {
        if (!Objects.equals(CommonUtils.getCurrentUser().getRole(), "ADMIN")) {
            return "redirect:/user/edit/" + CommonUtils.getCurrentUser().getId();
        }
        int page = 1;
        if (params.get("page") != null) {
            page = Integer.parseInt(params.get("page"));
        }
        Page<User> users = userService.get(params);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("totalItems", users.getTotalElements());
        model.addAttribute("users", users);
        model.addAttribute("models", "user");
        model.addAttribute("title", "Users Management");
        model.addAttribute("title", "Quản lý người dùng");
        return "users/index";
    }

    @GetMapping("create")
    public String create(Model model) {
        if (!Objects.equals(CommonUtils.getCurrentUser().getRole(), "ADMIN")) {
            return "redirect:/user/edit/" + CommonUtils.getCurrentUser().getId();
        }
        model.addAttribute("user", new User());
        model.addAttribute("title", "Thêm người dùng");
        return "users/create";
    }

    @PostMapping("save")
    public String save(User user, RedirectAttributes ra) {
        int strength = 10; // work factor of bcrypt

        userService.save(user);
        ra.addFlashAttribute("message", messageSource.getMessage("create_user_success", new Object[0], LocaleContextHolder.getLocale()));
        return "redirect:/user";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            if (Objects.equals(CommonUtils.getCurrentUser().getRole(), "USER") && !Objects.equals(id, CommonUtils.getCurrentUser().getId())) {
                return "redirect:/user/edit/" + CommonUtils.getCurrentUser().getId();
            }
            User user = userService.show(id);
            model.addAttribute("title", "Sửa người dùng " + user.getName());
            model.addAttribute("user", user);
            return "users/edit";
        } catch (UserPrincipalNotFoundException exception) {
            ra.addFlashAttribute("message", exception.getMessage());
            return "redirect:/user";
        }
    }

    @PostMapping("update")
    public String update(User user, RedirectAttributes ra) {
        if (!Objects.equals(CommonUtils.getCurrentUser().getRole(), "ADMIN")) {
            user.setRole(CommonUtils.getCurrentUser().getRole());
            user.setStatus(CommonUtils.getCurrentUser().getStatus());
        }
        userService.save(user);
        ra.addFlashAttribute("message", messageSource.getMessage("update_user_success", new Object[0], LocaleContextHolder.getLocale()));
        return "redirect:/user";
    }

    @GetMapping("delete/{id}")
    public String save(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            if (Objects.equals(CommonUtils.getCurrentUser().getRole(), "USER") && !Objects.equals(id, CommonUtils.getCurrentUser().getId())) {
                return "redirect:/user/edit/" + CommonUtils.getCurrentUser().getId();
            }
            User user = userService.show(id);
            ra.addFlashAttribute("message", messageSource.getMessage("delete_user_success", new Object[0], LocaleContextHolder.getLocale()));
            user.setStatus("DELETED");
            userService.save(user);
            return "redirect:/user";
        } catch (UserPrincipalNotFoundException exception) {
            ra.addFlashAttribute("message", exception.getMessage());
            return "redirect:/user";
        }
    }


}
