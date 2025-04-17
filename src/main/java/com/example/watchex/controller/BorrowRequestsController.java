package com.example.watchex.controller;

import com.example.watchex.dto.SearchDto;
import com.example.watchex.entity.*;
import com.example.watchex.service.*;
import com.example.watchex.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
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
    @Autowired
    private UserService userService;

    @GetMapping("")
    public String get(Model model, @RequestParam Map<String, String> params) {
        int page = 1;
        if (params.get("page") != null) {
            page = Integer.parseInt(params.get("page"));
        }
        List<User> users = userService.getAll();
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
        if (params.get("name") != null&& !Objects.equals(params.get("name"), "")) {
            dto.setName(params.get("name"));
        }

        if (params.get("user") != null && !Objects.equals(params.get("user"), "")) {
            dto.setUser(Integer.parseInt(params.get("user")));
        }
        Page<BorrowRequest> borrowRequests = borrowRequestService.get(dto);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchDto", dto);
        model.addAttribute("totalPages", borrowRequests.getTotalPages());
        model.addAttribute("totalItems", borrowRequests.getTotalElements());
        model.addAttribute("borrowRequests", borrowRequests);
        model.addAttribute("models", "borrow");
        model.addAttribute("id", params.get("id"));
        model.addAttribute("users", users);
        model.addAttribute("title", "Quản lý yêu cầu mượn");
        return "borrow/index";
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
        if (borrowHistory != null) {
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
            borrowHistoryService.save(borrowHistory);
        }
        deviceService.save(devices);
        borrowRequestService.save(borrowRequest);
        return "redirect:/borrow";
    }


}
