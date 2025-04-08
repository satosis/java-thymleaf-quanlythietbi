package com.example.watchex.controller.Admin;

import com.example.watchex.dto.ConfirmBorrowRequestDto;
import com.example.watchex.dto.StoreBorrowRequestDto;
import com.example.watchex.entity.BorrowHistory;
import com.example.watchex.entity.BorrowRequest;
import com.example.watchex.entity.Devices;
import com.example.watchex.entity.User;
import com.example.watchex.service.BorrowRequestService;
import com.example.watchex.service.DeviceService;
import com.example.watchex.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/borrowRequest")
public class BorrowRequestsController {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private BorrowRequestService borrowRequestService;
    @Autowired
    private DeviceService deviceService;

    @GetMapping("")
    public String get(Model model, @RequestParam Map<String, String> params) {
        int page = 1;
        if (params.get("page") != null) {
            page = Integer.parseInt(params.get("page"));
        }
        findPaginate(page, model);
        model.addAttribute("id", params.get("id"));
        model.addAttribute("email", params.get("email"));
        model.addAttribute("status", params.get("status"));
        model.addAttribute("title", "Quản lý đơn hàng");
        return "admin/borrowRequests/index";
    }

    private Model findPaginate(int page, Model model) {
        Page<BorrowRequest> borrowRequests = borrowRequestService.get(page);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", borrowRequests.getTotalPages());
        model.addAttribute("totalItems", borrowRequests.getTotalElements());
        model.addAttribute("borrowRequests", borrowRequests);
        model.addAttribute("models", "borrowRequest");
        model.addAttribute("title", "BorrowRequests Management");
        return model;
    }

    @PostMapping("/store")
    public String store(@Valid @ModelAttribute("StoreBorrowRequestDto") StoreBorrowRequestDto storeBorrowRequestDto, Model model) {
        Integer total = 0;
        User user = CommonUtils.getCurrentUser();
        BorrowRequest borrowRequest = new BorrowRequest();
        borrowRequest.setUser(CommonUtils.getCurrentUser());
        borrowRequest.setDevices(storeBorrowRequestDto.getDeviceId());
        borrowRequest.setRequestDate(storeBorrowRequestDto.getRequestDate());
        borrowRequest.setReason(storeBorrowRequestDto.getReason());
        borrowRequest.setStatus("PENDING");
        borrowRequest.setDueDate(storeBorrowRequestDto.getDueDate());
        borrowRequestService.save(borrowRequest);

        return "redirect:/";
    }


    @PostMapping("/status")
    public String changeStatus(@Valid @ModelAttribute("ConfirmBorrowRequestDto") ConfirmBorrowRequestDto confirmBorrowRequestDto, Model model) throws ClassNotFoundException {
        BorrowRequest borrowRequest = borrowRequestService.show(confirmBorrowRequestDto.getId());
        borrowRequest.setStatus(confirmBorrowRequestDto.getAction());
        if (confirmBorrowRequestDto.getAction().equals("APPROVED")) {
            Devices devices = deviceService.show(confirmBorrowRequestDto.getDeviceId());
            devices.setAvailability_status("BORROWED");

            BorrowHistory borrowHistory = new BorrowHistory();
            borrowHistory.setBorrowDate(confirmBorrowRequestDto.getBorrow_date());
            borrowHistory.setExpectedReturnDate(confirmBorrowRequestDto.getExpected_return_date());
            borrowHistory.setDevices(devices);
            borrowHistory.setUser(CommonUtils.getCurrentUser());
        }
        borrowRequestService.save(borrowRequest);
        return "redirect:/admin/borrowRequest";
    }


}
