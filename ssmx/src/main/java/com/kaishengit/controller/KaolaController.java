package com.kaishengit.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Kaola;
import com.kaishengit.entity.KaolaType;
import com.kaishengit.service.KaolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by xiaogao on 2017/11/4.
 */
@Controller
@RequestMapping("/product")
public class KaolaController {

    @Autowired
    private KaolaService kaolaService;
/*不写路径，则默认为类上面加的路劲*/
    @GetMapping
    public String list(@RequestParam(name = "p",required = false,defaultValue = "1") Integer pageNo,
                       Model model) {
        PageInfo<Kaola> pageInfo = kaolaService.findByPageNo(pageNo);
        model.addAttribute("pageInfo",pageInfo);
        return "product/list";
    }
    /* 1 */
    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("typeList",kaolaService.findAllType());
        return "product/new";
    }


    @PostMapping("/new")//访问路径
    public String newProduct(Kaola kaola,RedirectAttributes redirectAttributes) {
        kaolaService.save(kaola);
        redirectAttributes.addFlashAttribute("message", "保存成功");
        return "redirect:/product";//重定向到new
    }

    /*点击题目超链接时显示页面信息show*/
    @GetMapping("/{id:\\d+}")
    public String showProduct(@PathVariable Integer id, Model model) {
        Kaola kaola = kaolaService.findById(id);
        model.addAttribute("kaola",kaola);
        return "product/show";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String editProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("typeList",kaolaService.findAllType());
        model.addAttribute("product",kaolaService.findById(id));

        return "product/edit";
    }

    @PostMapping("/{id:\\d+}/edit")
    public String editProduct(Kaola kaola, RedirectAttributes redirectAttributes) {
        System.out.println(kaola.getProductName());
        kaolaService.editKaola(kaola);
        redirectAttributes.addFlashAttribute("message","修改成功");
        return "redirect:/product/"+kaola.getId();
    }

    @GetMapping("/{id:\\d+}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        kaolaService.deleteKaolaById(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/product";
    }
}
