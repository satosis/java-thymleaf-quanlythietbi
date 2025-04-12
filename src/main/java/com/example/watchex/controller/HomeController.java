package com.example.watchex.controller;

import com.example.watchex.dto.SearchDto;
import com.example.watchex.entity.Devices;
import com.example.watchex.service.BorrowRequestService;
import com.example.watchex.service.CategoryService;
import com.example.watchex.service.DeviceService;
import com.example.watchex.service.UserService;
import com.example.watchex.utils.CommonUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;

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
        if (CommonUtils.getCurrentUser().getRole() == "USER") {
            return "redirect:/device";
        }
        Gson gson = new Gson();
        SearchDto dto = new SearchDto();
        dto.setPageIndex(0);
        dto.setPageSize(10);
        Page<Devices> devices = deviceService.get(dto);
        int totalUser = userService.getActive().size();
        int totalDevices = deviceService.getActive().size();
        ArrayList<String> listDay = CommonUtils.getListDayAndMonth();
        Page<Devices> hotDevices = deviceService.get(dto);

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
