package com.kaishengit.controller;

import com.kaishengit.pojo.Product;
import com.kaishengit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xiaogao on 2017/11/29.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("productList",productService.findAll());
        return "/list";
    }

    @GetMapping("/new")
    public String newProduct() {
        return "new";
    }

    @PostMapping("/new")
    public String saveProduct(Product product) {
        productService.save(product);
        return "redirect:/product";
    }

    @GetMapping("/{id:\\d+}/delete")
    public String delete(@PathVariable("id") Integer id) {
        System.out.println(id);
        productService.deleteById(id);
        return "redirect:/product";
    }

}
