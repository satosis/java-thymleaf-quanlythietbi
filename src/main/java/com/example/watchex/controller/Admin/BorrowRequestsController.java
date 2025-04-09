package com.example.watchex.controller.Admin;

import com.example.watchex.entity.BorrowHistory;
import com.example.watchex.entity.BorrowRequest;
import com.example.watchex.entity.Devices;
import com.example.watchex.service.BorrowHistoryService;
import com.example.watchex.service.BorrowRequestService;
import com.example.watchex.service.DeviceService;
import com.example.watchex.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

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
}
