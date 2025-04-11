package com.example.watchex.controller;

import com.example.watchex.entity.Devices;
import com.example.watchex.entity.MaintenanceRecords;
import com.example.watchex.service.BorrowHistoryService;
import com.example.watchex.service.DeviceService;
import com.example.watchex.service.MaintenanceRecordsService;
import com.example.watchex.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceRecordsController {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MaintenanceRecordsService maintenanceRecordsService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private BorrowHistoryService borrowHistoryService;

    @GetMapping("")
    public String get(Model model, @RequestParam Map<String, String> params) {
        int page = 1;
        if (params.get("page") != null) {
            page = Integer.parseInt(params.get("page"));
        }
        findPaginate(page, model);
        model.addAttribute("id", params.get("id"));
        model.addAttribute("title", "Quản lý bảo trì thiết bị");
        return "maintenance/index";
    }

    private Model findPaginate(int page, Model model) {
        Page<MaintenanceRecords> maintenanceRecords = maintenanceRecordsService.get(page);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", maintenanceRecords.getTotalPages());
        model.addAttribute("totalItems", maintenanceRecords.getTotalElements());
        model.addAttribute("maintenanceRecords", maintenanceRecords);
        return model;
    }

    @GetMapping("create")
    public String create(Model model) {
        List<Devices> devicesList = deviceService.getAll();
        model.addAttribute("title", "Thêm thiết bị cần bảo trì");
        model.addAttribute("maintenanceRecords", new MaintenanceRecords());
        model.addAttribute("devicesList", devicesList);
        return "maintenance/create";
    }

    @PostMapping("create")
    public String save(MaintenanceRecords maintenanceRecords,
                       BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "maintenance/create";
        }
        maintenanceRecords.setReportedUser(CommonUtils.getCurrentUser());
        maintenanceRecords.setMaintenanceUser(CommonUtils.getCurrentUser());
        maintenanceRecordsService.save(maintenanceRecords);
        ra.addFlashAttribute("message", messageSource.getMessage("create_maintenancerecords_success", new Object[0], LocaleContextHolder.getLocale()));
        return "redirect:/maintenance";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        List<Devices> devicesList = deviceService.getAll();
        MaintenanceRecords maintenancerecords = maintenanceRecordsService.show(id);
        model.addAttribute("title", "Sửa thiết bị cần bảo trì");
        model.addAttribute("devicesList", devicesList);
        model.addAttribute("maintenance", maintenancerecords);
        return "maintenance/edit";
    }

    @PostMapping("edit")
    public String update(
                         MaintenanceRecords maintenanceRecords,
                         RedirectAttributes ra) throws ClassNotFoundException {
        maintenanceRecords.setMaintenanceUser(CommonUtils.getCurrentUser());
        maintenanceRecordsService.save(maintenanceRecords);
        if (Objects.equals(maintenanceRecords.getMaintenance_status(), "COMPLETED")) {
            Devices devices = maintenanceRecords.getDevices();
            devices.setOperational_status("WORKING");
            devices.setAvailability_status("AVAILABLE");
            deviceService.save(devices);
        }
        ra.addFlashAttribute("message", messageSource.getMessage("update_maintenancerecords_success", new Object[0], LocaleContextHolder.getLocale()));
        return "redirect:/maintenance";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
        ra.addFlashAttribute("message", messageSource.getMessage("delete_maintenancerecords_success", new Object[0], LocaleContextHolder.getLocale()));
        maintenanceRecordsService.delete(id);
        return "redirect:/maintenance";
    }

}
