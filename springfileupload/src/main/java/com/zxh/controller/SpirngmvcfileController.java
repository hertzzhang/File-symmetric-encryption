package com.zxh.controller;

import com.zxh.pojo.IFile;
import com.zxh.service.FileService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
@RequestMapping(path = "/springmvcfile")
public class SpirngmvcfileController {


    ApplicationContext ac;
    FileService fileService;
    /*
    *
    * spingmvc方式上传
    * */
    @RequestMapping(value = "/one")

    public @ResponseBody IFile TestSpringmvcfile(HttpServletRequest req, MultipartFile fileload) throws Exception {


             ac = new ClassPathXmlApplicationContext("springmvc.xml");
             fileService = (FileService) ac.getBean("fileservice");
            //获取当前日期，平且转换为yyymmdd格式
            String timeStr1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            //指定文件上传位置
            String path = req.getSession().getServletContext().getRealPath("/" + timeStr1 + "/");

            //调用业务层
           IFile iFile= fileService.SelectFile(path, fileload);



            return iFile;


    }
    @RequestMapping(value = "/two")
    public void TestFiledown(HttpServletRequest req, HttpServletResponse resp,String name) throws Exception {


        //读取服务器中的文件地址
        String path  = req.getSession().getServletContext().getRealPath("");


        fileService.Uploadfile(resp,name,path);

    }
}
