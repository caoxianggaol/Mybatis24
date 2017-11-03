package com.kaishengit.controller;

import com.kaishengit.entity.User;



import org.apache.commons.io.IOUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * Created by xiaogao on 2017/11/2.
 */
/*@RestController（4.0+）在类上加此注解表示返回的所有值都为结果，而不是往视图跳，此时可以省略每个方法中的@ResponseBody注解*/
@Controller
public class HelloController {
    /*@RequestMapping(value = "/hello",method = {RequestMethod.GET,RequestMethod.POST})*/
  /*  @RequestMapping(value = "/hello",method = RequestMethod.GET)*/
   /* @GetMapping("/hello")
    public String sayHello(){
        System.out.println("hello,SpringMvc");
         return "hello";
    }*/

    /*方法二*/
    /*@GetMapping("/send")
    public ModelAndView save() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("send");//设置视图的名字
        modelAndView.addObject("message","你好视图");

        return modelAndView;
    }*/

    /*业务控制器获取方法返回值 方法一*/
   /* @GetMapping("/send")
    public String send(Model model) {
        model.addAttribute("message","你好springMVC");

        return "send";
    }*/


   /*接收客户端的值,传值时将tId写为其他名字时，可将其他名字配置在name中则可疑获得tId
   * required = false,defaultValue = "1"表示tId可以不传值，则默认值为1*/
   @GetMapping("/hello")
   public String book(Integer id,
                      @RequestParam(name="T", required = false,defaultValue = "1")Integer tId, Model model) {

       System.out.println("id" + id + "tId" + tId);
       model.addAttribute("id",id);
       model.addAttribute("tId",tId);

       return "hello";
   }

   /*{id:\d+} 表示为变量对变量的控制是正则表达式
   * 此时要绑定变量并且是不可选的变量必须有*/
    @GetMapping("/movie/{id:\\d+}")
    public String movie(@PathVariable Integer id) {
        System.out.println("Movie Id :" + id);
        return "hello";
    }
/*输入localhost/class/java24/user/12
* 输出ClassName=java24 UserId=12*/
    @GetMapping("/class/{className}/user/{userId:\\d+}")
    public String findUser(@PathVariable String className,
                           @PathVariable Integer userId) {
        System.out.println("ClassName : " + className + "  UserId:" + userId);
        return "hello";
    }

    @GetMapping("/save")
    public String save(){

        return "save";
    }

    @PostMapping("/save")
    public String save(User user, String email,
                       MultipartFile photo,
                       RedirectAttributes redirectAttributes) {
        System.out.println("userName -> " + user.getUserName() + " password-> " + user.getPassword() + " email ->" + email);

        //判断是否上传了图片
        if(!photo.isEmpty()) {
            System.out.println("文件名称 -> " + photo.getOriginalFilename());
            System.out.println("文件大小- > " + photo.getSize());
            System.out.println("MIMEType -> " + photo.getContentType());

            try {
                IOUtils.copy(photo.getInputStream(),new FileOutputStream("I:/a/"+photo.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        redirectAttributes.addFlashAttribute("message","资料审核中");
        return "redirect:/save";
    }

    /* @ResponseBody不加此注解回去找/WEB-INF/views/user.jsp路径 会抛404错误
    /**加了此注解后抛500 No converter found for return value of type没有转换器对
    返回值进行转换 所以需要在pom.xml中导入jackson库*/
    @GetMapping("/user")
    @ResponseBody
    public User findById() {

        User user = new User();
        user.setId(30);
        user.setUserName("小明");
        user.setPassword("123123");
        return user;

    }

    @GetMapping("/users")
    @ResponseBody
    public List<User> findAll() {
        User user = new User();
        user.setId(30);
        user.setUserName("小明");
        user.setPassword("123123");

        User user2 = new User();
        user2.setId(30);
        user2.setUserName("tom");
        user2.setPassword("123123");
        return Arrays.asList(user,user2);
    }

    /*@CookieValue("JSESSIONID")取JSESSIONID的cook值给 sessionId*/
    @GetMapping("/session")
    public String show(HttpServletRequest request,
                       HttpServletResponse response,
                       HttpSession session,
                      @CookieValue("JSESSIONID") String sessionId) {
        session.setAttribute("aa","springMVC");
        return "hello";
    }
}
