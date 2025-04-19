package com.example.watchex.controller;

import com.example.watchex.dto.SearchDto;
import com.example.watchex.dto.UserDto;
import com.example.watchex.entity.User;
import com.example.watchex.service.ImageService;
import com.example.watchex.service.UserService;
import com.example.watchex.utils.CommonUtils;
import com.example.watchex.utils.ExportExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
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
    private ImageService imageService;
    @GetMapping("")
    public String get(Model model, @RequestParam Map<String, String> params) {
        if (!Objects.equals(CommonUtils.getCurrentUser().getRole(), "ADMIN")) {
            return "redirect:/user/edit/" + CommonUtils.getCurrentUser().getId();
        }
        SearchDto dto = new SearchDto();
        if (params.get("id") != null && !Objects.equals(params.get("id"), "")) {
            dto.setPageIndex(Integer.parseInt(params.get("id")));
        }
         int page = 1;
        if (params.get("page") != null) {
            page = Integer.parseInt(params.get("page"));
            dto.setPageIndex(Integer.parseInt(params.get("page")) - 1);
         }

        if (params.get("pageSize") != null) {
            dto.setPageSize(Integer.parseInt(params.get("pageSize")));
        }

        if (params.get("status") != null && !Objects.equals(params.get("status"), "")) {
            dto.setStatus(params.get("status"));
        }
        if (params.get("name") != null&& !Objects.equals(params.get("name"), "")) {
            dto.setName(params.get("name"));
        }
        if (params.get("email") != null&& !Objects.equals(params.get("email"), "")) {
            dto.setEmail(params.get("email"));
        }
        if (params.get("student_id") != null&& !Objects.equals(params.get("student_id"), "")) {
            dto.setStudent_id(params.get("student_id"));
        }
        if (params.get("role") != null&& !Objects.equals(params.get("role"), "")) {
            dto.setRole(params.get("role"));
        }
        Page<User> users = userService.get(dto);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchDto", dto);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("totalItems", users.getTotalElements());
        model.addAttribute("users", users);
        model.addAttribute("models", "user");
        model.addAttribute("title", "Users Management");
        model.addAttribute("title", "Quản lý người dùng");
        return "users/index";
    }

    @GetMapping("create")
    public String create(Model model, UserDto userDto) {
        if (!Objects.equals(CommonUtils.getCurrentUser().getRole(), "ADMIN")) {
            return "redirect:/user/edit/" + CommonUtils.getCurrentUser().getId();
        }
        model.addAttribute("user", userDto);
        model.addAttribute("title", "Thêm người dùng");
        return "users/create";
    }

    @PostMapping("save")
    public String save(UserDto userDto, RedirectAttributes ra) throws IOException {
        int strength = 10; // work factor of bcrypt
        User user = new User();
        if (userDto.getAvatarFile().getOriginalFilename() != null && !Objects.equals(userDto.getAvatarFile().getOriginalFilename(), "")) {
            String filePath = imageService.saveImage(userDto.getAvatarFile());
            user.setAvatar(filePath);
        }
        user.setRole(userDto.getRole());
        user.setStatus(userDto.getStatus());
        user.setName(userDto.getName());
        user.setStudent_id(userDto.getStudentId());
        user.setPhone(userDto.getPhone());
        user.setPassword(userDto.getPassword());
        userService.save(user);
        ra.addFlashAttribute("message", messageSource.getMessage("create_user_success", new Object[0], LocaleContextHolder.getLocale()));
        return "redirect:/user";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes ra, UserDto userDto) {
        try {
            if (Objects.equals(CommonUtils.getCurrentUser().getRole(), "USER") && !Objects.equals(id, CommonUtils.getCurrentUser().getId())) {
                return "redirect:/user/edit/" + CommonUtils.getCurrentUser().getId();
            }
            User user = userService.show(id);
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setAvatar(user.getAvatar());
            userDto.setEmail(user.getEmail());
            userDto.setPhone(user.getPhone());
            userDto.setRole(user.getRole());
            userDto.setStatus(user.getStatus());
            userDto.setStudentId(user.getStudent_id());
            model.addAttribute("title", "Sửa người dùng " + user.getName());
            model.addAttribute("user", userDto);
            return "users/edit";
        } catch (UserPrincipalNotFoundException exception) {
            ra.addFlashAttribute("message", exception.getMessage());
            return "redirect:/user";
        }
    }

    @PostMapping("update")
    public String update(UserDto userDto, RedirectAttributes ra) throws IOException {
        User user = userService.getById(userDto.getId());
        if (!Objects.equals(CommonUtils.getCurrentUser().getRole(), "ADMIN")) {
            user.setRole(CommonUtils.getCurrentUser().getRole());
            user.setStatus(CommonUtils.getCurrentUser().getStatus());
        } else {
            user.setRole(userDto.getRole());
            user.setStatus(userDto.getStatus());
        }
        if (userDto.getAvatarFile().getOriginalFilename() != null && !Objects.equals(userDto.getAvatarFile().getOriginalFilename(), "")) {
            String filePath = imageService.saveImage(userDto.getAvatarFile());
            user.setAvatar(filePath);
        }
        user.setName(userDto.getName());
        user.setStudent_id(userDto.getStudentId());
        user.setPhone(userDto.getPhone());
        if (!userDto.getPassword().isEmpty()) {
            user.setPassword(userDto.getPassword());
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

  @GetMapping("export")
    public ResponseEntity<byte[]> exportToExcel() throws IOException {
        List<User> users = userService.getActive();
        ExportExcel<User> exportExcel = new ExportExcel<>();
        ByteArrayInputStream in = exportExcel.export(users);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Danh_sach_nguoi_dung.xlsx");
        if (users.isEmpty()) {
            return (ResponseEntity<byte[]>) ResponseEntity.ok();
        }
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(in.readAllBytes());
    }

}
