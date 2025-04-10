package com.example.watchex.controller;

import com.example.watchex.dto.DeviceDto;
import com.example.watchex.dto.StoreBorrowRequestDto;
import com.example.watchex.entity.BorrowRequest;
import com.example.watchex.entity.Category;
import com.example.watchex.entity.Devices;
import com.example.watchex.service.BorrowRequestService;
import com.example.watchex.service.CategoryService;
import com.example.watchex.service.DeviceService;
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

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("")
public class DeviceController {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BorrowRequestService borrowRequestService;

    @GetMapping("")
    public String get(Model model, @RequestParam Map<String, String> params) {
        int page = 1;
        if (params.get("page") != null) {
            page = Integer.parseInt(params.get("page"));
        }
        findPaginate(page, model);
        model.addAttribute("title", "Quản lý sản phẩm");
        return "devices/index";
    }

    private void findPaginate(int page, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("pageSize", "10");
        params.put("page", String.valueOf(page));
        Page<Devices> devices = deviceService.get(params);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", devices.getTotalPages());
        model.addAttribute("totalItems", devices.getTotalElements());
        model.addAttribute("devices", devices);
        model.addAttribute("models", "device");
        model.addAttribute("title", "Devices Management");
    }

    @GetMapping("devices/create")
    public String create(Model model, DeviceDto deviceDto) {
        List<Category> categories = categoryService.getAll();
        model.addAttribute("deviceDto", deviceDto);
        model.addAttribute("title", "Thêm thiết bị");
        model.addAttribute("categories", categories);
        return "devices/create";
    }

    @PostMapping("devices/create")
    public String save(@Valid @ModelAttribute("deviceDto") DeviceDto deviceDto,
                       BindingResult result, RedirectAttributes ra, Model model) {
        List<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        if (result.hasErrors()) {
            return "devices/create";
        }
        Devices devices = new Devices();
        devices.setName(deviceDto.getName());
        devices.setCategory(deviceDto.getCategory());
        devices.setDescription(deviceDto.getDescription());
        devices.setSerial_number(deviceDto.getSerial_number());
        devices.setUser(CommonUtils.getCurrentUser());
        devices.setLocation(deviceDto.getLocation());
        devices.setOperational_status("WORKING");
        devices.setAvailability_status("AVAILABLE");

        deviceService.save(devices);
        ra.addFlashAttribute("message", messageSource.getMessage("create_device_success", new Object[0], LocaleContextHolder.getLocale()));
        return "redirect:/";
    }

    @GetMapping("devices/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes ra, DeviceDto deviceDto) {
        try {
            List<Category> categories = categoryService.getAll();
            Devices devices = deviceService.show(id);
            deviceDto.setId(devices.getId());
            deviceDto.setName(devices.getName());
            deviceDto.setCategory(devices.getCategory());
            deviceDto.setSerial_number(devices.getSerial_number());
            deviceDto.setDescription(devices.getDescription());
            deviceDto.setLocation(devices.getLocation());
            model.addAttribute("title", "Sửa sản phẩm " + devices.getName());
            model.addAttribute("deviceDto", deviceDto);
            model.addAttribute("categories", categories);
            return "devices/edit";
        } catch (ClassNotFoundException exception) {
            ra.addFlashAttribute("message", exception.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("devices/edit/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("deviceDto") DeviceDto deviceDto,
                         BindingResult result,
                         RedirectAttributes ra, Model model) throws ClassNotFoundException {
        List<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        if (result.hasErrors()) {
            return "devices/edit";
        }
        Devices devices = deviceService.show(id);
        devices.setName(deviceDto.getName());
        devices.setCategory(deviceDto.getCategory());
        devices.setDescription(deviceDto.getDescription());
        devices.setSerial_number(deviceDto.getSerial_number());
        devices.setUser(CommonUtils.getCurrentUser());
        devices.setLocation(deviceDto.getLocation());

        deviceService.save(devices);
        ra.addFlashAttribute("message", messageSource.getMessage("update_device_success", new Object[0], LocaleContextHolder.getLocale()));
        return "redirect:/";
    }

    @GetMapping("devices/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            deviceService.show(id);
            ra.addFlashAttribute("message", messageSource.getMessage("delete_device_success", new Object[0], LocaleContextHolder.getLocale()));
            deviceService.delete(id);
            return "redirect:/";
        } catch (ClassNotFoundException exception) {
            ra.addFlashAttribute("message", exception.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("devices/operational/{id}")
    public String operational(@PathVariable("id") Integer id) throws ClassNotFoundException {
        Devices devices = deviceService.show(id);
        if (Objects.equals(devices.getOperational_status(), "AVAILABLE")) {
            devices.setOperational_status("UNDER_MAINTENANCE");
        }
        if (Objects.equals(devices.getOperational_status(), "UNDER_MAINTENANCE")) {
            devices.setOperational_status("AVAILABLE");
        }
        deviceService.save(devices);
        return "redirect:/";
    }

    @GetMapping("devices/availability/{id}/{action}")
    public String availability(@PathVariable("id") Integer id, @PathVariable("action") String action) throws ClassNotFoundException {
        Devices devices = deviceService.show(id);
        devices.setAvailability_status(action);
        deviceService.save(devices);
        return "redirect:/";
    }

    @GetMapping("devices/request/{id}")
    public String request(@PathVariable("id") Integer id, Model model, RedirectAttributes ra, DeviceDto deviceDto) {
        try {
            Devices devices = deviceService.show(id);
            StoreBorrowRequestDto storeBorrowRequestDto = new StoreBorrowRequestDto();
            model.addAttribute("title", "Gửi yêu cầu mượn sản phẩm " + devices.getName());
            model.addAttribute("devices", devices);
            model.addAttribute("date", new Date());
            model.addAttribute("storeBorrowRequestDto", storeBorrowRequestDto);
            return "devices/request";
        } catch (ClassNotFoundException exception) {
            ra.addFlashAttribute("message", exception.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("devices/request/{id}")
    public String request(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("storeBorrowRequestDto") StoreBorrowRequestDto storeBorrowRequestDto,
                         BindingResult result,
                         RedirectAttributes ra, Model model) throws ClassNotFoundException {
        Devices devices = deviceService.show(id);
        model.addAttribute("devices", devices);
        if (result.hasErrors()) {
            return "devices/request";
        }
        BorrowRequest borrowRequest = new BorrowRequest();
        borrowRequest.setDevices(devices);
        borrowRequest.setUser(CommonUtils.getCurrentUser());
        borrowRequest.setRequestDate(new Date());
        borrowRequest.setReason(storeBorrowRequestDto.getReason());
        borrowRequest.setStatus("PENDING");
        borrowRequest.setDueDate(storeBorrowRequestDto.getDueDate());

        borrowRequestService.save(borrowRequest);
        ra.addFlashAttribute("message", messageSource.getMessage("borrow_device_success", new Object[0], LocaleContextHolder.getLocale()));
        return "redirect:/";
    }
}
