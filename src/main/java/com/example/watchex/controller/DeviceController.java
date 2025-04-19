package com.example.watchex.controller;

import com.example.watchex.config.CommonConfigurations;
import com.example.watchex.dto.DeviceDto;
import com.example.watchex.dto.SearchDto;
import com.example.watchex.dto.StoreBorrowRequestDto;
import com.example.watchex.entity.BorrowHistory;
import com.example.watchex.entity.BorrowRequest;
import com.example.watchex.entity.Category;
import com.example.watchex.entity.Devices;
import com.example.watchex.service.*;
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

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
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
    @Autowired
    private BorrowHistoryService borrowHistoryService;
    @Autowired
    private ImageService imageService;

    @ModelAttribute
    public String beforeEveryRequest() {
        String[] listStatus = {"BANNED", "SUSPENDED", "INACTIVE"};
        if (Arrays.asList(listStatus).contains(CommonConfigurations.getCurrentUser().getStatus())) {
            CommonUtils.setCookie("Authorization", "");
            return "redirect:/";
        }
        return null;
    }


    @GetMapping("device")
    public String get(Model model, @RequestParam Map<String, String> params) {
        if (CommonUtils.getCurrentUser() == null) {
            return "redirect:/auth/login";
        }
        int page = 1;
        if (params.get("page") != null) {
            page = Integer.parseInt(params.get("page"));
        }

        params.put("pageSize", "10");
        params.put("page", String.valueOf(page));
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
        if (Objects.equals(CommonConfigurations.getCurrentUser().getRole(), "USER")) {
            dto.setStatus("AVAILABLE");
        }
        if (params.get("status") != null && !Objects.equals(params.get("status"), "")) {
            dto.setStatus(params.get("status"));
        }
        if (params.get("operationalStatus") != null&& !Objects.equals(params.get("operationalStatus"), "")) {
            dto.setOperationalStatus(params.get("operationalStatus"));
        }
        if (params.get("name") != null&& !Objects.equals(params.get("name"), "")) {
            dto.setName(params.get("name"));
        }

        if (params.get("category") != null && !Objects.equals(params.get("category"), "")) {
            dto.setCategory(Integer.parseInt(params.get("category")));
        }
        List<Category> categories = categoryService.getAll();
        Page<Devices> devices = deviceService.get(dto);
        model.addAttribute("searchDto", dto);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", devices.getTotalPages());
        model.addAttribute("totalItems", devices.getTotalElements());
        model.addAttribute("devices", devices);
        model.addAttribute("models", "device");
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Devices Management");
        model.addAttribute("title", "Quản lý sản phẩm");
        return "devices/index";
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
                       BindingResult result, RedirectAttributes ra, Model model) throws IOException {
        List<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        if (result.hasErrors()) {
            return "devices/create";
        }

        Devices devices = new Devices();
        devices.setName(deviceDto.getName());
        devices.setSlug(CommonUtils.toSlug(deviceDto.getName()));
        devices.setCategory(deviceDto.getCategory());
        devices.setDescription(deviceDto.getDescription());
        devices.setSerial_number(deviceDto.getSerial_number());
        if (deviceDto.getAvatar().getOriginalFilename() != null && !Objects.equals(deviceDto.getAvatar().getOriginalFilename(), "")) {
            String filePath = imageService.saveImage(deviceDto.getAvatar());
            devices.setAvatar(filePath);
        }
        devices.setUser(CommonUtils.getCurrentUser());
        devices.setLocation(deviceDto.getLocation());
        devices.setOperational_status("WORKING");
        devices.setAvailability_status("AVAILABLE");

        deviceService.save(devices);
        ra.addFlashAttribute("message", messageSource.getMessage("create_device_success", new Object[0], LocaleContextHolder.getLocale()));
        return "redirect:/device";
    }

    @GetMapping("devices/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes ra, DeviceDto deviceDto) {
        try {

            List<Category> categories = categoryService.getAll();
            Devices devices = deviceService.show(id);
            deviceDto.setId(devices.getId());
            deviceDto.setName(devices.getName());
            deviceDto.setAvatarName(devices.getAvatar());
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
            return "redirect:/device";
        }
    }

    @PostMapping("devices/edit/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("deviceDto") DeviceDto deviceDto,
                         BindingResult result,
                         RedirectAttributes ra, Model model) throws ClassNotFoundException, IOException {
        List<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        if (result.hasErrors()) {
            return "devices/edit";
        }
        if (CommonUtils.getCurrentUser().getRole() == "USER") {
            return "redirect:/device";
        }
        Devices devices = deviceService.show(id);
        devices.setName(deviceDto.getName());
        devices.setSlug(CommonUtils.toSlug(deviceDto.getName()));
        devices.setCategory(deviceDto.getCategory());
        if (deviceDto.getAvatar().getOriginalFilename() != null && !Objects.equals(deviceDto.getAvatar().getOriginalFilename(), "")) {
            String filePath = imageService.saveImage(deviceDto.getAvatar());
            devices.setAvatar(filePath);
        }
        devices.setDescription(deviceDto.getDescription());
        devices.setSerial_number(deviceDto.getSerial_number());
        devices.setUser(CommonUtils.getCurrentUser());
        devices.setLocation(deviceDto.getLocation());

        deviceService.save(devices);
        ra.addFlashAttribute("message", messageSource.getMessage("update_device_success", new Object[0], LocaleContextHolder.getLocale()));
        return "redirect:/device";
    }

    @GetMapping("devices/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            if (CommonUtils.getCurrentUser().getRole() == "USER") {
                return "redirect:/device";
            }
            Devices devices = deviceService.show(id);
            BorrowHistory borrowHistory = borrowHistoryService.findByDevices(devices);
            if (Objects.equals(devices.getAvailability_status(), "BORROWED") || Objects.equals(devices.getAvailability_status(), "UNDER_MAINTENANCE")) {
                ra.addFlashAttribute("message_error", "Không thể xóa thiết bị");
            } else if (borrowHistory != null) {
                devices.setOperational_status("BROKEN");
                deviceService.save(devices);
            } else {
                borrowRequestService.deleteByDevice(devices.getId());
                ra.addFlashAttribute("message", messageSource.getMessage("delete_device_success", new Object[0], LocaleContextHolder.getLocale()));
                deviceService.delete(id);
            }
            return "redirect:/device";
        } catch (ClassNotFoundException exception) {
            ra.addFlashAttribute("message", exception.getMessage());
            return "redirect:/device";
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
        return "redirect:/device";
    }

    @GetMapping("devices/availability/{id}/{action}")
    public String availability(@PathVariable("id") Integer id, @PathVariable("action") String action) throws ClassNotFoundException {
        Devices devices = deviceService.show(id);
        devices.setAvailability_status(action);
        deviceService.save(devices);
        return "redirect:/device";
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
            return "redirect:/device";
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
        return "redirect:/device";
    }

    @GetMapping("devices/export")
    public ResponseEntity<byte[]> exportToExcel() throws IOException {
        List<Devices> devices = deviceService.getActive();
        ExportExcel<Devices> exportExcel = new ExportExcel();
        ByteArrayInputStream in = exportExcel.export(devices);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Danh_sach_thiet_bi.xlsx");
        if (devices.isEmpty()) {
            return (ResponseEntity<byte[]>) ResponseEntity.ok();
        }
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(in.readAllBytes());
    }

}
