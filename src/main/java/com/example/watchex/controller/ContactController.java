package com.example.watchex.controller;

import com.example.watchex.config.CommonConfigurations;
import com.example.watchex.dto.ContactDto;
import com.example.watchex.entity.Contact;
import com.example.watchex.service.ContactService;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ContactService contactService;


    @ModelAttribute
    public String beforeEveryRequest() {
        String[] listStatus = {"BANNED", "SUSPENDED", "INACTIVE"};
        if (CommonUtils.getCurrentUser() == null) {
            return "redirect:/auth/login";
        }
        if (Arrays.asList(listStatus).contains(CommonConfigurations.getCurrentUser().getStatus())) {
            CommonUtils.setCookie("Authorization", "");
            return "redirect:/";
        }
        return null;
    }


    @GetMapping("")
    public String get(Model model, @RequestParam Map<String, String> params) {
        int page = 1;
        if (params.get("page") != null) {
            page = Integer.parseInt(params.get("page"));
        }
        findPaginate(page, model);
        model.addAttribute("title", "Quản lý liên hệ");
        return "contact/index";
    }

    private void findPaginate(int page, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("pageSize", "10");
        params.put("page", String.valueOf(page));
        Page<Contact> contacts = contactService.get(params);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contacts.getTotalPages());
        model.addAttribute("totalItems", contacts.getTotalElements());
        model.addAttribute("contacts", contacts);
        model.addAttribute("models", "contact");
        model.addAttribute("title", "Contact Management");
    }

    @GetMapping("create")
    public String create(Model model, ContactDto contactDto) {
        model.addAttribute("title", "Thêm liên hệ");
        contactDto.setEmail(CommonUtils.getCurrentUser().getEmail());
        model.addAttribute("contactDto", contactDto);
        return "contact/create";
    }

    @PostMapping("create")
    public String save(@Valid @ModelAttribute("contactDto") ContactDto contactDto,
                       BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "contact/create";
        }
        Contact contact = new Contact();
        contact.setUser(CommonUtils.getCurrentUser());
        contact.setEmail(contactDto.getEmail());
        contact.setSubject(contactDto.getSubject());
        contact.setMessage(contactDto.getMessage());
        contact.setIs_reply(0);

        contactService.save(contact);
        ra.addFlashAttribute("message", messageSource.getMessage("create_contact_success", new Object[0], LocaleContextHolder.getLocale()));
        return "redirect:/contact";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes ra, ContactDto contactDto) {
        try {
            Contact contact = contactService.show(id);
            contactDto.setId(contact.getId());
            contactDto.setEmail(contact.getEmail());
            contactDto.setSubject(contact.getSubject());
            contactDto.setMessage(contact.getMessage());
            model.addAttribute("title", "Sửa liên hệ");
            model.addAttribute("contactDto", contactDto);
            return "contact/edit";
        } catch (ClassNotFoundException exception) {
            ra.addFlashAttribute("message", exception.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("edit/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("contactDto") ContactDto contactDto,
                         BindingResult result,
                         RedirectAttributes ra) throws ClassNotFoundException {
        if (result.hasErrors()) {
            return "contact/edit";
        }
        Contact contact = contactService.show(id);

        contact.setUser(CommonUtils.getCurrentUser());
        contact.setEmail(contactDto.getEmail());
        contact.setSubject(contactDto.getSubject());
        contact.setMessage(contactDto.getMessage());

        contactService.save(contact);
        ra.addFlashAttribute("message", messageSource.getMessage("update_contact_success", new Object[0], LocaleContextHolder.getLocale()));
        return "redirect:/contact";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            contactService.show(id);
            ra.addFlashAttribute("message", messageSource.getMessage("delete_contact_success", new Object[0], LocaleContextHolder.getLocale()));
            contactService.delete(id);
        } catch (ClassNotFoundException exception) {
            ra.addFlashAttribute("message", exception.getMessage());
        }
        return "redirect:/contact";
    }
    @GetMapping("reply/{id}")
    public String active(@PathVariable("id") Integer id) throws ClassNotFoundException {
        Contact contact = contactService.show(id);
        contact.setIs_reply(1);
        contactService.save(contact);
        return "redirect:/contact";
    }

}
