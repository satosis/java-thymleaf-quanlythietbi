package com.example.watchex.controller;

import com.example.watchex.entity.BorrowHistory;
import com.example.watchex.entity.BorrowRequest;
import com.example.watchex.entity.Devices;
import com.example.watchex.entity.MaintenanceRecords;
import com.example.watchex.service.BorrowHistoryService;
import com.example.watchex.service.BorrowRequestService;
import com.example.watchex.service.DeviceService;
import com.example.watchex.service.MaintenanceRecordsService;
import com.example.watchex.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/borrow")
public class BorrowRequestsController {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private BorrowRequestService borrowRequestService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private BorrowHistoryService borrowHistoryService;
    @Autowired
    private MaintenanceRecordsService maintenanceRecordsService;

    @GetMapping("")
    public String get(Model model, @RequestParam Map<String, String> params) {
        int page = 1;
        if (params.get("page") != null) {
            page = Integer.parseInt(params.get("page"));
        }
        findPaginate(page, model);
        model.addAttribute("id", params.get("id"));
        model.addAttribute("title", "Quản lý yêu cầu mượn");
        return "borrow/index";
    }

    private Model findPaginate(int page, Model model) {
        Page<BorrowRequest> borrowRequests = borrowRequestService.get(page);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", borrowRequests.getTotalPages());
        model.addAttribute("totalItems", borrowRequests.getTotalElements());
        model.addAttribute("borrowRequests", borrowRequests);
        return model;
    }

    @GetMapping("/accept/{id}")
    public String accept(@PathVariable("id") Integer id) throws ClassNotFoundException {
        BorrowRequest borrowRequest = borrowRequestService.show(id);

        Devices devices = borrowRequest.getDevices();
        devices.setAvailability_status("BORROWED");

        BorrowHistory borrowHistory = new BorrowHistory();
        borrowHistory.setBorrowRequest(borrowRequest);
        borrowHistory.setBorrowDate(new Date());
        borrowHistory.setExpectedReturnDate(borrowRequest.getDueDate());
        borrowHistory.setDevices(devices);
        borrowHistory.setUser(CommonUtils.getCurrentUser());
        borrowHistoryService.save(borrowHistory);

        borrowRequest.setStatus("APPROVED");
        borrowRequestService.save(borrowRequest);
        return "redirect:/borrow";
    }

    @GetMapping("/deny/{id}")
    public String deny(@PathVariable("id") Integer id) throws ClassNotFoundException {
        BorrowRequest borrowRequest = borrowRequestService.show(id);

        borrowRequest.setStatus("REJECTED");
        borrowRequestService.save(borrowRequest);
        return "redirect:/borrow";
    }

    @PostMapping("/return/{id}")
    public String accept(@PathVariable("id") Integer id, @RequestParam Map<String, String> params) throws ClassNotFoundException {
        BorrowRequest borrowRequest = borrowRequestService.show(id);
        BorrowHistory borrowHistory = borrowHistoryService.findByBorrowRequest(borrowRequest);
        Devices devices = borrowRequest.getDevices();
        borrowRequest.setStatus("RETURNED");
        if (Objects.equals(params.get("status"), "GOOD")) {
            borrowHistory.setExpectedReturnDate(new Date());
            devices.setAvailability_status("AVAILABLE");
        }
        if (Objects.equals(params.get("status"), "MINOR_DAMAGE") || Objects.equals(params.get("status"), "MAJOR_DAMAGE")) {
            borrowHistory.setStatusDevice(params.get("status"));
            borrowHistory.setExpectedReturnDate(new Date());

            devices.setOperational_status("NEEDS_REPAIR");
            devices.setAvailability_status("UNDER_MAINTENANCE");

            MaintenanceRecords maintenanceRecords = new MaintenanceRecords();
            maintenanceRecords.setDevices(devices);
            maintenanceRecords.setLoiThietBi(params.get("loi"));
            maintenanceRecords.setReportedUser(CommonUtils.getCurrentUser());
            maintenanceRecords.setMaintenance_status("PENDING");
            maintenanceRecordsService.save(maintenanceRecords);
        }
        deviceService.save(devices);
        borrowHistoryService.save(borrowHistory);
        borrowRequestService.save(borrowRequest);
        return "redirect:/borrow";
    }


}
