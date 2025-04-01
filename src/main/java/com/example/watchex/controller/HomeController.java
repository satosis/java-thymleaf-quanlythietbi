package com.example.watchex.controller;

import com.example.watchex.entity.Devices;
import com.example.watchex.service.CategoryService;
import com.example.watchex.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;
    @GetMapping("")
    public String index(Model model) {
        List<Category> categories = categoryService.getAll();
        List<Devices> devices = productService.getAll();
        List<Devices> productsAccessoriesses = productService.getProductsAccessoriess();
        List<Devices> productsGlasses = productService.getProductsGlass();
        List<Devices> productsWatches = productService.getProductsWatch();
        List<Devices> listDevices1 = productService.getProductsByCategory(1, 15);
        List<Devices> listDevices2 = productService.getProductsByCategory(2, 15);
        List<Devices> listDevices3 = productService.getProductsByCategory(3, 15);
        List<Devices> listDevices4 = productService.getProductsByCategory(4, 15);
        List<Devices> listDevices5 = productService.getProductsByCategory(5, 15);
        List<Devices> listDevices6 = productService.getProductsByCategory(6, 15);
        List<Devices> listDevices7 = productService.getProductsByCategory(7, 15);

        model.addAttribute("cartCount", Cart.cart.size());
        model.addAttribute("categories", categories);
        model.addAttribute("products", devices);
        model.addAttribute("productsAccessoriess", productsAccessoriesses);
        model.addAttribute("productsGlass", productsGlasses);
        model.addAttribute("productsWatch", productsWatches);
        model.addAttribute("listProduct1", listDevices1);
        model.addAttribute("listProduct2", listDevices2);
        model.addAttribute("listProduct3", listDevices3);
        model.addAttribute("listProduct4", listDevices4);
        model.addAttribute("listProduct5", listDevices5);
        model.addAttribute("listProduct6", listDevices6);
        model.addAttribute("listProduct7", listDevices7);

        return "index";
    }
}
