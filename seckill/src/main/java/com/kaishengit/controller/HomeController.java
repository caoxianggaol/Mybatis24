package com.kaishengit.controller;
import com.kaishengit.entity.Seckill;

import com.kaishengit.service.SeckillService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.List;


/**
 * Created by xiaogao on 2017/12/5.
 */
@Controller
public class HomeController {

    @Autowired
    private SeckillService seckillService;

    @GetMapping("/")
    public String home(Model model) {
        List<Seckill> seckillList = seckillService.findAll();
        model.addAttribute("seckillList",seckillList);
        return "home";
    }

    /**
     * 添加抢购商品
     * @return
     */
    @GetMapping("/seckill/new")
    public String newSeckill() {
        return "new";
    }

    @PostMapping("/seckill/new")
    public String newseckill(Seckill seckill, MultipartFile image,String sTime,String eTime) {

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        DateTime startDateTime = DateTime.parse(sTime,formatter);
        DateTime endDateTime = DateTime.parse(eTime,formatter);

        seckill.setStartTime(startDateTime.toDate());
        seckill.setEndTime(endDateTime.toDate());

        if(image.isEmpty()) {
            seckillService.saveSeckill(seckill,null);
        } else {
            try {
                seckillService.saveSeckill(seckill,image.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/";
    }


    @GetMapping("/seckill/{id:\\d+}")
    public String showSeckill(@PathVariable Integer id,Model model) {
        Seckill seckill = seckillService.findById(id);
        model.addAttribute("seckill",seckill);
        return "product";
    }
}
