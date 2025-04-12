package com.example.watchex.controller;

import com.example.watchex.dto.DeviceDto;
import com.example.watchex.dto.StoreBorrowRequestDto;
import com.example.watchex.entity.BorrowHistory;
import com.example.watchex.entity.BorrowRequest;
import com.example.watchex.entity.Category;
import com.example.watchex.entity.Devices;
import com.example.watchex.service.*;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("")
public class HomeController {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BorrowRequestService borrowRequestService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String get(Model model, @RequestParam Map<String, String> params) {
        if (CommonUtils.getCurrentUser() == null) {
            return "redirect:/auth/login";
        }
        Gson gson = new Gson();
        Page<Devices> devices = deviceService.get(params);
        int totalUser = userService.getActive().size();
        int totalDevices = deviceService.getActive().size();
        ArrayList<String> listDay = CommonUtils.getListDayAndMonth();
        Page<Devices> hotDevices = deviceService.get(params);

        model.addAttribute("totalPages", devices.getTotalPages());
        model.addAttribute("listDay", gson.toJson(listDay));
        model.addAttribute("totalUser", totalUser);
        model.addAttribute("totalDevices", totalDevices);
        model.addAttribute("hotDevices", hotDevices);
        model.addAttribute("totalItems", devices.getTotalElements());
        model.addAttribute("devices", devices);
        model.addAttribute("models", "device");
        model.addAttribute("title", "Devices Management");
        model.addAttribute("title", "Quản lý sản phẩm");
        return "home";
    }

}
