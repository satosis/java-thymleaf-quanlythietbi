package com.example.watchex.controller.Admin;

import com.example.watchex.entity.BorrowRequest;
import com.example.watchex.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/product_image")
public class AdminProductImageController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ProductImageService productImageService;

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra, @NonNull HttpServletRequest request) {
        try {
            BorrowRequest borrowRequest = productImageService.show(id);
            ra.addFlashAttribute("message", messageSource.getMessage("delete_category_success", new Object[0], LocaleContextHolder.getLocale()));
            productImageService.delete(id);
        } catch (ClassNotFoundException exception) {
            ra.addFlashAttribute("message", exception.getMessage());
        }
        return "redirect:" + request.getHeader("Referer");
    }
}
