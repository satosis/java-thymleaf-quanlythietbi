package com.example.watchex.controller.Admin;

import com.example.watchex.dto.ProductDto;
import com.example.watchex.entity.BorrowRequest;
import com.example.watchex.entity.Devices;
import com.example.watchex.service.CategoryService;
import com.example.watchex.service.KeywordService;
import com.example.watchex.service.ProductImageService;
import com.example.watchex.service.ProductService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ProductService productService;
    @Autowired
    private KeywordService keywordService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductImageService productImageService;

    @GetMapping("")
    public String get(Model model, @RequestParam Map<String, String> params) {
        int page = 1;
        if (params.get("page") != null) {
            page = Integer.parseInt(params.get("page"));
        }
        findPaginate(page, model);
        model.addAttribute("title", "Quản lý sản phẩm");
        return "admin/products/index";
    }

    private void findPaginate(int page, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("pageSize", "10");
        params.put("page", String.valueOf(page));
        Page<Devices> products = productService.get(params);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("totalItems", products.getTotalElements());
        model.addAttribute("products", products);
        model.addAttribute("models", "product");
        model.addAttribute("title", "Products Management");
    }

    @GetMapping("create")
    public String create(Model model, ProductDto productDto) {
        List<Keyword> keywords = keywordService.getAll();
        List<Category> categories = categoryService.getAll();
        model.addAttribute("product", new Devices());
        model.addAttribute("title", "Thêm sản phẩm");
        model.addAttribute("productDto", productDto);
        model.addAttribute("keywords", keywords);
        model.addAttribute("categories", categories);
        return "admin/products/create";
    }

    @PostMapping("save")
    public String save(@Valid @ModelAttribute("productDto") ProductDto productDto,
                       BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "admin/products/create";
        }
        Devices devices = new Devices();
        devices.setPro_name(productDto.getPro_name());
        devices.setPro_amount(productDto.getPro_amount());
        devices.setCategory(productDto.getCategory());
        devices.setPro_avatar(productDto.getPro_avatar());
        devices.setPro_view(0);
        devices.setPro_price(productDto.getPro_price());
        devices.setPro_sale(productDto.getPro_sale());
        devices.setPro_favourite(0);
        devices.setPro_hot(0);
        devices.setPro_active(1);
        devices.setPro_admin_id(CommonUtils.getCurrentAdmin().getId());
        devices.setPro_pay(0);
        devices.setPro_description(productDto.getPro_description());
        devices.setPro_content(productDto.getPro_content());
        devices.setPro_review_total(0);
        devices.setPro_review_star(0);
        devices.set_wysihtml5_mode("");

        productService.save(devices);
        ra.addFlashAttribute("message", messageSource.getMessage("create_product_success", new Object[0], LocaleContextHolder.getLocale()));
        return "redirect:/admin/product";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes ra, ProductDto productDto) {
        try {
            List<Keyword> keywords = keywordService.getAll();
            List<Category> categories = categoryService.getAll();
            Devices devices = productService.show(id);
            List<BorrowRequest> borrowRequests = productImageService.findByProduct(devices);
            productDto.setPro_name(devices.getPro_name());
            productDto.setPro_amount(devices.getPro_amount());
            productDto.setCategory(devices.getCategory());
            productDto.setPro_avatar(devices.getPro_avatar());
            productDto.setPro_price(devices.getPro_price());
            productDto.setPro_sale(devices.getPro_sale());
            productDto.setPro_description(devices.getPro_description());
            productDto.setPro_content(devices.getPro_content());
            productDto.setPro_review_total(0);
            productDto.setPro_review_star(0);
            productDto.set_wysihtml5_mode("");
            productDto.setId(devices.getId());
            model.addAttribute("title", "Sửa sản phẩm " + devices.getPro_name());
            model.addAttribute("productDto", productDto);
            model.addAttribute("keywords", keywords);
            model.addAttribute("categories", categories);
            model.addAttribute("productImages", borrowRequests);
            return "admin/products/edit";
        } catch (ClassNotFoundException exception) {
            ra.addFlashAttribute("message", exception.getMessage());
            return "redirect:/admin/product";
        }
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("productDto") ProductDto productDto,
                         BindingResult result,
                         RedirectAttributes ra) throws ClassNotFoundException {
        if (result.hasErrors()) {
            return "admin/products/edit";
        }
        Devices devices = productService.show(id);
        devices.setPro_name(productDto.getPro_name());
        devices.setPro_amount(productDto.getPro_amount());
        devices.setCategory(productDto.getCategory());
        devices.setPro_avatar(productDto.getPro_avatar());
        devices.setPro_price(productDto.getPro_price());
        devices.setPro_sale(productDto.getPro_sale());
        devices.setPro_admin_id(CommonUtils.getCurrentAdmin().getId());
        devices.setPro_description(productDto.getPro_description());
        devices.setPro_content(productDto.getPro_content());

        productService.save(devices);
        ra.addFlashAttribute("message", messageSource.getMessage("update_product_success", new Object[0], LocaleContextHolder.getLocale()));
        return "redirect:/admin/product";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            productService.show(id);
            ra.addFlashAttribute("message", messageSource.getMessage("delete_product_success", new Object[0], LocaleContextHolder.getLocale()));
            productService.delete(id);
            return "redirect:/admin/product";
        } catch (ClassNotFoundException exception) {
            ra.addFlashAttribute("message", exception.getMessage());
            return "redirect:/admin/product";
        }
    }

    @GetMapping("hot/{id}")
    public String hot(@PathVariable("id") Integer id) throws ClassNotFoundException {
        Devices devices = productService.show(id);
        if (devices.getPro_hot() == 0) {
            devices.setPro_hot(1);
        }
        if (devices.getPro_hot() == 1) {
            devices.setPro_hot(0);
        }
        productService.save(devices);
        return "redirect:/admin/product";
    }

    @GetMapping("active/{id}")
    public String active(@PathVariable("id") Integer id) throws ClassNotFoundException {
        Devices devices = productService.show(id);
        if (devices.getPro_active() == 0) {
            devices.setPro_active(1);
        }
        if (devices.getPro_active() == 1) {
            devices.setPro_active(0);
        }
        productService.save(devices);
        return "redirect:/admin/product";
    }
}
