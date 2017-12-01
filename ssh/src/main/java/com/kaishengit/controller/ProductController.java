package com.kaishengit.controller;

import com.kaishengit.pojo.Product;
import com.kaishengit.service.ProductService;
import com.kaishengit.util.Page;
import com.kaishengit.util.RequestQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by xiaogao on 2017/11/29.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /*required = false 该参数可有可无*/
   /* @GetMapping
    public String home(Model model,
                         @RequestParam(required = false)String productName) {
        //model.addAttribute("productList",productService.findAll());
        model.addAttribute("productList",productService.findByProductName(productName));
        return "/list";
    }*/

    @GetMapping
    public String home(Model model,
                       HttpServletRequest request,
                       @RequestParam(required = false,defaultValue = "1",name = "p") Integer pageNo) {
        List<RequestQuery> requestQueryList = RequestQuery.builderRequestQuery(request);
        Page<Product> productList = productService.findByRequestQuery(requestQueryList,pageNo);
        model.addAttribute("page",productList);
        return "list";
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



    @GetMapping("/{id:\\d+}")
    public String showProduct(@PathVariable("id") Integer id,Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product",product);
        //System.out.println(product);
        return "product";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String editProduct(@PathVariable Integer id,Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product",product);
        return "edit";
    }

    @PostMapping("/{id:\\d+}/edit")
    public String editProduct(Product product) {
        productService.save(product);
        return "redirect:/product/"+product.getId();
    }

    @GetMapping("/{id:\\d+}/delete")
    public String delProudect(@PathVariable Integer id) {
        productService.deleteById(id);
        return "redirect:/product";
    }
}
