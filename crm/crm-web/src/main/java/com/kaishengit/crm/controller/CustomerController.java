package com.kaishengit.crm.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundEXception;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.service.AccountService;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.SaleChanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 客户管理控制器
 * Created by xiaogao on 2017/11/9.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController{

    @Autowired
    private CustomerService customerService;

    /*显示所有用户使用（用于转交他人列表）*/
    @Autowired
    private AccountService accountService;

    @Autowired
    private SaleChanceService saleChanceService;

    /**
     * 我的客户 页面
     * @param pageNo  分页 页号
     * @param model 返回json
     * @param httpSession 获取session验证当前登录对象
     * @return
     */
    @GetMapping("/my")
    public String customerMy(@RequestParam(required = false,defaultValue = "1") Integer pageNo,
                             Model model, HttpSession httpSession) {
        //获取当前登录账号对象
        Account account = getCurrentAccount(httpSession);
        //我的客户 分页
        PageInfo<Customer> pageInfo = customerService.pageForCustomer(account,pageNo);
       //service返回return new PageInfo<>(customerList);此处接收并返回前端
        model.addAttribute("page",pageInfo);
        return "customer/my";
    }


    /**
     * 跳转到公海客户页面 公海客户列表
     * @return
     */
    @GetMapping("/public")
    public String customerPublic() {
        return "customer/public";
    }

    /**
     * 跳到 我的客户新增页面
     * 需要把客户来源列表和行业名称列表传到页面 使用Model
     * @return
     */
    @GetMapping("/my/new")
    public String customerNew(Model model) {

        model.addAttribute("trades",customerService.findAllCustomerTrade());
        model.addAttribute("sources",customerService.findAllCustomerSource());

        return "customer/new";
    }

    /**
     * 提交     新增表单
     * @param customer
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/my/new")
    public String newCustomer(Customer customer, RedirectAttributes redirectAttributes) {

        customerService.saveNewCustomer(customer);
        redirectAttributes.addFlashAttribute("message","新增成功");

        return "redirect:/customer/my";
    }

    /**
     * 展示我的客户详细信息
     * @param id
     * @param httpSession
     * @param model
     * @return
     */
    @GetMapping("/my/{id:\\d+}")
    public String showCustomer(@PathVariable Integer id,
                               HttpSession httpSession,
                               Model model) {
        Customer customer = checkCustomerRole(id,httpSession);

        //查询客户关联的销售机会列表
        List<SaleChance> saleChanceList = saleChanceService.findSaleChanceByCustId(id);

        model.addAttribute("saleChanceList",saleChanceList);
        model.addAttribute("customer",customer);
       /*用于显示转交他人列表，查询所有account，前端c:if  accountList*/
       model.addAttribute("accountList",accountService.findAllAccount());
        return "customer/show";
    }

    /**
     * 删除 根据主键删除我的客户
     * @param id
     * @return
     */
    @GetMapping("/my/{id:\\d+}/delete")
    public String deleteCustomerById(@PathVariable Integer id,
                                     HttpSession httpSession,
                                     RedirectAttributes redirectAttributes) {

        Customer customer = checkCustomerRole(id, httpSession);
        customerService.deleteCustomer(customer);
        redirectAttributes.addFlashAttribute("message","删除客户成功");

        return "redirect:/customer/my";
    }

    /**
     * 把客户放入公海
     * @return
     */
    @GetMapping("/my/{id:\\d+}/public")
    public String publicCustomer(@PathVariable Integer id,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        Customer customer = checkCustomerRole(id,session);
        customerService.publicCustomer(customer);
        redirectAttributes.addFlashAttribute("message","将客户放入公海成功");
        return "redirect:/customer/my";

    }


    /**
     * 编辑客户
     */
    @GetMapping("/my/{id:\\d+}/edit")
    public String editCustomer(@PathVariable Integer id,
                               HttpSession session,
                               Model model) {
        Customer customer = checkCustomerRole(id,session);

        model.addAttribute("customer",customer);
        model.addAttribute("trades",customerService.findAllCustomerTrade());
        model.addAttribute("sources",customerService.findAllCustomerSource());
        return "customer/edit";
    }

    @PostMapping("/my/{id:\\d+}/edit")
    public String editCustomer(Customer customer,HttpSession session,RedirectAttributes redirectAttributes) {
        checkCustomerRole(customer.getId(),session);
        customerService.editCustomer(customer);
        redirectAttributes.addFlashAttribute("message","编辑成功");
        return "redirect:/customer/my/"+customer.getId();
    }

    /**
     * 转交客户
     * @return
     */
    @GetMapping("/my/{customerId:\\d+}/tran/{toAccountId:\\d+}")
    public String tranCustomer(@PathVariable Integer customerId,
                               @PathVariable Integer toAccountId,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        Customer customer = checkCustomerRole(customerId,session);
        customerService.tranCustomer(customer,toAccountId);

        redirectAttributes.addFlashAttribute("message","客户转交成功");
        return "redirect:/customer/my";
    }

    /**
     * 将数据导出为csv文件
     */
    @GetMapping("/my/export.csv")
    public void exportCsvData(HttpServletResponse response,
                              HttpSession session) throws IOException {
        Account account = getCurrentAccount(session);
        /*mime头*/
        response.setContentType("text/csv;charset=GBK");
        String fileName = new String("我的客户.csv".getBytes("UTF-8"),"ISO8859-1");
       /*不设置"Content-Disposition浏览器会尝试打开图片，而不弹框*/
        response.addHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");

        OutputStream outputStream = response.getOutputStream();
        customerService.exportCsvFileToOutputStream(outputStream,account);
    }

    /**
     * 将数据导出为xls文件
     */
    @GetMapping("/my/export.xls")
    public void exportXlsData(HttpServletResponse response,
                              HttpSession session) throws IOException {
        Account account = getCurrentAccount(session);

        response.setContentType("application/vnd.ms-excel");
        String fileName = new String("我的客户.xls".getBytes("UTF-8"),"ISO8859-1");
        response.addHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");

        OutputStream outputStream = response.getOutputStream();
        customerService.exportXlsFileToOutputStream(outputStream,account);
    }



    /**
     * 验证客户是否为当前登录的对象（有权限)
     */
    private Customer checkCustomerRole(Integer id,HttpSession httpSession) {

        //根据id查找客户
        Customer customer = customerService.findCustomerById(id);
        if(customer == null) {
            //404
            throw new NotFoundEXception("找不到"+id+"对应的客户");
        }

        Account account = getCurrentAccount(httpSession);
        if(!customer.getAccountId().equals(account.getId())) {
            //403 Forbidden
            throw new ForbiddenException("权限不足");
        }
        return customer;
    }

}



