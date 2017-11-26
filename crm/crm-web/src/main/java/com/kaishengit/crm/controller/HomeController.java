package com.kaishengit.crm.controller;


import com.kaishengit.crm.auth.ShiroUtil;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.service.AccountService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private AccountService accountService;

    /**
     * 来到登录页面
     * @return
     */
    @GetMapping("/")
    public String login() {
        Subject subject = ShiroUtil.getSubject();
        System.out.println("isAuthenticated ? " + subject.isAuthenticated());
        System.out.println("isRemembered ? " + subject.isRemembered());

       if(subject.isAuthenticated()) {
           //认为用户是要切换账号
           subject.logout();//消除之前账户
       }
       if(!subject.isAuthenticated() && subject.isRemembered()) {
           //被记住的用户直接去登陆成功页面
           return "redirect:/home";
       }

        return "index";
    }

    /**
     * 表单登录方法
     * @param mobile
     * @param password
     * @return
     */
    @PostMapping("/")
    public String login(String mobile, String password, boolean rememberMe,
                        RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
           /* Account account = accountService.login(mobile, password);
            session.setAttribute("curr_account",account);*/

            Subject subject = SecurityUtils.getSubject();
            /*可以直接加盐值 还可以设置加密次数（拿加后的结果再加密连续三次）*/
            UsernamePasswordToken usernamePasswordToken =
                    new UsernamePasswordToken(mobile,new Md5Hash(password).toString(),rememberMe);
            subject.login(usernamePasswordToken);

      /* 头像取值 方法一  后端  方法二  前端 以下步骤可以在jsp页面写*/
            //获取当前登录对象 从验证登陆的方法中取（因为已经返回了Account）account(主体)
            //Account account = (Account) subject.getPrincipal();
            //蒋登录成功的对象放入Session（来自shiro）获取 subject.getSession()
           // Session session = subject.getSession();//在此会认为是HTTPSession
            //放入session
            //session.setAttribute("curr_account",account);


            /*跳转到登录前访问的URL （存于session中，所以需要HttpServletRequest）*/
           String url = "/home";
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            if(savedRequest != null) {
                //获取登陆前访问的URL
                url = savedRequest.getRequestUrl();
            }
            return "redirect:" + url;

        } catch (AuthenticationException ex) {
            redirectAttributes.addFlashAttribute("message","账号或密码错误");
            return "redirect:/";
        }
    }

    /**
     * 去登陆后的页面
     * @return
     */
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    /**
     * 安全退出
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session,RedirectAttributes redirectAttributes) {
        /*清空session*/
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message","安全退出系统");

        return "redirect:/";
    }
}
