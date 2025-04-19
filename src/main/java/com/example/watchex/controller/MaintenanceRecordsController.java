package com.example.watchex.controller;

import com.example.watchex.config.CommonConfigurations;
import com.example.watchex.dto.SearchDto;
import com.example.watchex.entity.Devices;
import com.example.watchex.entity.MaintenanceRecords;
import com.example.watchex.entity.User;
import com.example.watchex.service.BorrowHistoryService;
import com.example.watchex.service.DeviceService;
import com.example.watchex.service.MaintenanceRecordsService;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

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
    @Autowired
    private UserService userService;


    @ModelAttribute
    public String beforeEveryRequest() {
        String[] listStatus = {"BANNED", "SUSPENDED", "INACTIVE"};
        if (Arrays.asList(listStatus).contains(CommonConfigurations.getCurrentUser().getStatus())) {
            CommonUtils.setCookie("Authorization", "");
            return "redirect:/";
        }
        return null;
    }


    @GetMapping("")
    public String get(Model model, @RequestParam Map<String, String> params) {
        int page = 1;
        if (params.get("page") != null) {
            page = Integer.parseInt(params.get("page"));
        }
        SearchDto dto = new SearchDto();
        if (params.get("id") != null && !Objects.equals(params.get("id"), "")) {
            dto.setPageIndex(Integer.parseInt(params.get("id")));
        }
        if (params.get("page") != null) {
            dto.setPageIndex(Integer.parseInt(params.get("page")) - 1);
        }
        if (params.get("pageSize") != null) {
            dto.setPageSize(Integer.parseInt(params.get("pageSize")));
        }
        if (params.get("status") != null && !Objects.equals(params.get("status"), "")) {
            dto.setStatus(params.get("status"));
        }
        if (params.get("name") != null && !Objects.equals(params.get("name"), "")) {
            dto.setName(params.get("name"));
        }

        if (params.get("user") != null && !Objects.equals(params.get("user"), "")) {
            dto.setUser(Integer.parseInt(params.get("user")));
        }
        if (params.get("reporter") != null && !Objects.equals(params.get("reporter"), "")) {
            dto.setReporter(Integer.parseInt(params.get("reporter")));
        }
        Page<MaintenanceRecords> maintenanceRecords = maintenanceRecordsService.get(dto);
        List<User> users = userService.getAll();
        List<User> reporters = userService.getAll();
        model.addAttribute("currentPage", page);
        model.addAttribute("users", users);
        model.addAttribute("reporters", reporters);
        model.addAttribute("searchDto", dto);
        model.addAttribute("models", "maintenance");
        model.addAttribute("totalPages", maintenanceRecords.getTotalPages());
        model.addAttribute("totalItems", maintenanceRecords.getTotalElements());
        model.addAttribute("maintenanceRecords", maintenanceRecords);
        model.addAttribute("id", params.get("id"));
        model.addAttribute("title", "Quản lý bảo trì thiết bị");
        return "maintenance/index";
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

    @GetMapping("export")
    public ResponseEntity<byte[]> exportToExcel() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("pageSize", "100");
        params.put("page", "0");
        List<MaintenanceRecords> maintenanceRecords = maintenanceRecordsService.getAll();
        ExportExcel<MaintenanceRecords> exportExcel = new ExportExcel();
        ByteArrayInputStream in = exportExcel.export(maintenanceRecords);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Danh_sach_thiet_bi_bao_tri.xlsx");
        if (maintenanceRecords.isEmpty()) {
            return (ResponseEntity<byte[]>) ResponseEntity.ok();
        }
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(in.readAllBytes());
    }
}
