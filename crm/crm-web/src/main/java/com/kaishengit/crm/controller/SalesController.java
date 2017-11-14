package com.kaishengit.crm.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundEXception;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.entity.SaleChanceRecord;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.SaleChanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
/**
 * 销售层控制器
 * Created by xiaogao on 2017/11/13.
 */
@Controller
@RequestMapping("/sales")
public class SalesController extends BaseController{

    @Autowired
    private SaleChanceService saleChanceService;
    @Autowired
    private CustomerService customerService;

    /**
     * 登录 我的销售机会列表
     * @return
     */
    @GetMapping("/smy")
    public String smySalesList(@RequestParam(required = false,defaultValue = "1",name = "p") Integer pageNo,
                               Model model, HttpSession httpSession) {
        //验证当前登录对象
        Account account = getCurrentAccount(httpSession);
        PageInfo<SaleChance> pageInfo = saleChanceService.pageForAccountSale(account,pageNo);

        model.addAttribute("page",pageInfo);
        return "/sales/smy";
    }

    /**
     * 新增销售机会
     * @return
     */
    @GetMapping("/smy/snew")
    public String snewSalesChance(Model model,HttpSession httpSession) {

        Account account = getCurrentAccount(httpSession);
        //当前登录对象的客户列表
        List<Customer> customerList = customerService.findAllCustomerByAccountId(account);
        //进度列表
        List<String> progressList = saleChanceService.findAllSalesProgress();

        model.addAttribute("customerList",customerList);
        model.addAttribute("progressList",progressList);

        return "sales/snew";
    }
    @PostMapping("/smy/snew")
    public String newSalesChance(SaleChance saleChance,
                                 RedirectAttributes redirectAttributes) {
        saleChanceService.saveNewSalesChance(saleChance);
        redirectAttributes.addFlashAttribute("message","保存成功");
        return "redirect:/sales/smy";
    }
    /**
     * 我的销售机会详情
     * @param id
     * @return
     */
    @GetMapping("/smy/{id:\\d+}")
    public String mySalesInfo(@PathVariable Integer id,
                              HttpSession session,
                              Model model) {
        SaleChance saleChance = checkRole(id, session);

        //查询该销售机会对应的跟进记录列表
        List<SaleChanceRecord> recordList = saleChanceService.findSalesChanceRecodeListBySalesId(id);

        model.addAttribute("recordList",recordList);
        model.addAttribute("saleChance",saleChance);
        model.addAttribute("processList",saleChanceService.findAllSalesProgress());
        return "sales/chance";
    }
    private SaleChance checkRole(Integer id, HttpSession session) {
        Account account = getCurrentAccount(session);
        SaleChance saleChance = saleChanceService.findSalesChanceWithCustomerById(id);
        if(saleChance == null) {
            throw new NotFoundEXception();
        }
        if(!saleChance.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }
        return saleChance;
    }
    /**
     * 删除销售机会
     */
    @GetMapping("/smy/{id:\\d+}/delete")
    public String deleteSalesChance(@PathVariable Integer id,
                                    RedirectAttributes redirectAttributes,
                                    HttpSession session) {
        checkRole(id, session);
        saleChanceService.deleteSalesChanceById(id);
        return "redirect:/sales/smy";
    }
    /**
     * 添加新的跟进记录
     * @param record
     * @return
     */
    @PostMapping("/smy/snew/record")
    public String saveNewSaleChanceRecode(SaleChanceRecord record,HttpSession session) {
        checkRole(record.getSaleId(), session);
        saleChanceService.saveNewSalesChanceRecode(record);
        return "redirect:/sales/smy/"+record.getSaleId();
    }
    /**
     * 改变销售机会的状态
     * @return
     */
    @PostMapping("/smy/{id:\\d+}/progress/update")
    public String updateSaleChanceState(@PathVariable Integer id,String progress,HttpSession session) {
        checkRole(id, session);
        saleChanceService.updateSalesChanceState(id,progress);
        return "redirect:/sales/smy/"+id;
    }

}
