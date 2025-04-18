package com.example.watchex.controller;

import com.example.watchex.dto.DeviceDetailDto;
import com.example.watchex.dto.SearchDto;
import com.example.watchex.dto.TransactionRevenueDto;
import com.example.watchex.entity.Devices;
import com.example.watchex.service.BorrowRequestService;
import com.example.watchex.service.CategoryService;
import com.example.watchex.service.DeviceService;
import com.example.watchex.service.UserService;
import com.example.watchex.utils.CommonUtils;
import com.google.gson.Gson;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        if (Objects.equals(CommonUtils.getCurrentUser().getRole(), "USER")) {
            return "redirect:/device";
        }
        Gson gson = new Gson();
        SearchDto dto = new SearchDto();
        dto.setPageIndex(0);
        dto.setPageSize(10);
        Page<Devices> devices = deviceService.get(dto);
        int totalUser = userService.getActive().size();
        int totalDevices = deviceService.getActive().size();
        List<String> listDay = CommonUtils.getFormattedDaysInCurrentMonth();
        List<DeviceDetailDto> hotDevices = deviceService.getHot();

        int borrowRequestProcess = borrowRequestService.getByStatus("PENDING").size();
        int borrowRequestApproved = borrowRequestService.getByStatus("APPROVED").size();
        int borrowRequestReturned = borrowRequestService.getByStatus("RETURNED").size();
        int borrowRequestRejected = borrowRequestService.getByStatus("REJECTED").size();

        List<Object> target = new LinkedList<>();
        target.add("Đang chờ");
        target.add(borrowRequestProcess);
        target.add(false);
        List<List<Object>> statusBorrowRequest = Lists.newArrayList();
        statusBorrowRequest.add(target);

        List<Object> target1 = new LinkedList<>();
        target1.add("Đã cho mượn");
        target1.add(borrowRequestApproved);
        target1.add(false);
        statusBorrowRequest.add(target1);

        List<Object> target2 = new LinkedList<>();
        target2.add("Đã hoàn trả");
        target2.add(borrowRequestReturned);
        target2.add(false);
        statusBorrowRequest.add(target2);


        List<Object> target3 = new LinkedList<>();
        target3.add("Đã hủy yêu cầu");
        target3.add(borrowRequestRejected);
        target3.add(false);
        statusBorrowRequest.add(target3);

        ArrayList<Integer> arrRevenueTransactionMonth = new ArrayList<>();
        ArrayList<Integer> arrRevenueTransactionMonthDefault = new ArrayList<>();

        List<TransactionRevenueDto> revenueTransactionMonthDefault = borrowRequestService.getTotalIdsGroupedByCreatedAt("APPROVED");
        List<TransactionRevenueDto> revenueTransactionMonth = borrowRequestService.getTotalIdsGroupedByCreatedAt("PENDING");

        for (String day : listDay) {
            Integer total = 0;
            for (TransactionRevenueDto revenue : revenueTransactionMonthDefault) {
                if (Objects.equals(String.valueOf(revenue.getDay()), day)) {
                        total = revenue.getTotalMoney();
                    break;
                }
            }
            arrRevenueTransactionMonth.add(total);
            total = 0;
            for (TransactionRevenueDto revenue : revenueTransactionMonth) {
                if (Objects.equals(String.valueOf(revenue.getDay()), day)) {
                    total = revenue.getTotalMoney();
                    break;
                }
            }
            arrRevenueTransactionMonthDefault.add(total);
        }

        model.addAttribute("totalPages", devices.getTotalPages());
        model.addAttribute("listDay", gson.toJson(listDay));
        model.addAttribute("totalUser", totalUser);
        model.addAttribute("totalDevices", totalDevices);
        model.addAttribute("hotDevices", hotDevices);
        model.addAttribute("totalItems", devices.getTotalElements());
        model.addAttribute("devices", devices);
        model.addAttribute("models", "device");
        model.addAttribute("statusBorrowRequest", gson.toJson(statusBorrowRequest));
        model.addAttribute("title", "Devices Management");
        model.addAttribute("title", "Quản lý sản phẩm");
        model.addAttribute("arrRevenueTransactionMonth", gson.toJson(arrRevenueTransactionMonth));
        model.addAttribute("arrRevenueTransactionMonthDefault", gson.toJson(arrRevenueTransactionMonthDefault));
        return "home";
    }

}
