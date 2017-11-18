package com.kaishengit.crm.controller.interceptor;

import com.kaishengit.crm.controller.exception.NotFoundEXception;
import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.service.DiskService;
import com.kaishengit.web.result.AjaxResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 网盘控制器
 * Created by xiaogao on 2017/11/17.
 */
@Controller
@RequestMapping("/disk")
public class Diskcontroller {

    @Autowired
    private DiskService diskService;

    @GetMapping
    public String disk(Model model,
                       @RequestParam(required = false,defaultValue = "0",name = "_") Integer pid) {
        List<Disk> diskList = diskService.findDiskBypId(pid);
        /*判断是否为子文件夹 pid != 0说明为子文件夹（因为=0为文件夹）*/
        if(pid != 0) {
            Disk disk = diskService.findById(pid);//找到当前目录
            model.addAttribute("disk",disk);//到前端做判断
        }
        /*传到前端*/
        model.addAttribute("diskList",diskList);
        return "disk/home";
    }

    /**
     * 新建文件夹
     * @param disk
     * @return
     */
    @PostMapping("/new/folder")
    @ResponseBody//返回一个json对象
    public AjaxResult saveNewFolder(Disk disk) {
        //保存文件夹
        diskService.saveNewFolder(disk);
        //获取当前最新的集合（Ajax刷新页面）
        List<Disk> diskList = diskService.findDiskBypId(disk.getpId());//当前diak.getpId()

        //return AjaxResult.success();//不刷新的做法
        return AjaxResult.successWithData(diskList);
    }


    /**
     * 文件上传
     * 添加commons-fileupload依赖
     */
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult upload(Integer pId, Integer accountId, MultipartFile file) throws IOException {
        if(file.isEmpty()) {//没有选择上传文件（如Tools/TsetRes...模仿客户端发出请求）
            return AjaxResult.error("");
        }
        //选择了文件  获取文件输入流
        InputStream inputStream = file.getInputStream();
        //获取文件大小
        long fileSize = file.getSize();
        //获取文件真正的名字
        String fileName = file.getOriginalFilename();

        diskService.saveNewFile(inputStream,fileSize,fileName,pId,accountId);

        //获取当前最新的集合（Ajax刷新页面）
        List<Disk> diskList = diskService.findDiskBypId(pId);
        return AjaxResult.successWithData(diskList);

    }
/*================================================================*/
    /**
     * 文件下载
     * @param id
     * @param fileName
     */
    @GetMapping("/download")
    public void downloadFile(@RequestParam(name="_") Integer id,
                             @RequestParam(required = false,defaultValue = "") String fileName,
                             HttpServletResponse response) {
          try {
              OutputStream outputStream = response.getOutputStream();//相应输出流
              InputStream inputStream = diskService.downloadFile(id);

              //判断是下载环视预览
              if(StringUtils.isNotEmpty(fileName)) {
                  //下载
                  //设置mimetype 下载时为二进制流 所以设置为http mimetype stream-->application/octet-stream
                  response.setContentType("application/octet-stream");
                  //设置下载对话框 中文
                  fileName = new String(fileName.getBytes("UTF-8"),"ISO8859-1");
                  response.addHeader("Content-Disposition","attachment;filename=\""+fileName+"\"");
              } //预览————> 不设置mimetype 和 下载框 即可

              IOUtils.copy(inputStream,outputStream);
              outputStream.flush();
              outputStream.close();
              inputStream.close();

          } catch (IOException | ServiceException ex) {
                ex.printStackTrace();
                throw new NotFoundEXception();
          }
    }
}
