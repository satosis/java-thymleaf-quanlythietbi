package com.example.watchex.controller;

import com.example.watchex.dto.SearchDto;
import com.example.watchex.entity.*;
import com.example.watchex.repository.BorrowHistoryRepository;
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
@RequestMapping("/history")
public class BorrowHistoryController {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private BorrowHistoryService borrowHistoryervice;

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
        Page<BorrowHistory> borrowHistory = borrowHistoryService.get(dto);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchDto", dto);
        model.addAttribute("totalPages", borrowHistory.getTotalPages());
        model.addAttribute("totalItems", borrowHistory.getTotalElements());
        model.addAttribute("borrowHistories", borrowHistory);
        model.addAttribute("models", "borrow");
        model.addAttribute("id", params.get("id"));
        model.addAttribute("users", users);
        model.addAttribute("title", "Quản lý lịch sử mượn");
        return "history/index";
    }

}
