package com.zxh.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path = "/file")
public class FileloadController {

    /*
    * 文件上传
    * */
    @RequestMapping(value = "/one")
    public  String fileuploadone(HttpServletRequest req) throws Exception {
        //获取当前日期，平且转换为yyymmdd格式
        String timeStr1= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(timeStr1);

        //指定文件上传位置
       String path = req.getSession().getServletContext().getRealPath("/"+timeStr1+"/");
       //判断该路径是否存在
        File file = new File(path);
        if(!file.exists())
        {
                //如果不存在则创建该文件夹
                file.mkdir();
        }

        //解析request对象，获取上传文件项
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        //解析request
        List<FileItem> fileItems= upload.parseRequest(req);
        //遍历
        for (FileItem fileItem :fileItems)
        {
                //判断当前fileItem对象是不是上传文件项
                if(fileItem.isFormField())//如果是true就是普通表单，false就是文件项
                {

                }
                else {
                        //获取上传文件的名称
                    String filename = fileItem.getName();

                    //只获取文件名最后的后缀.
                    filename = filename.substring(filename.lastIndexOf("."));



                       //把文件名称设置成唯一值防止覆盖，uuid
                    String uuid = UUID.randomUUID().toString().replace("-","");
                    //把uuid和后缀名拼接起来。
                    filename =uuid+filename;


                        //完成文件上传，new File(path,filename)表示把名为filename的文件上传到path路径下。
                    fileItem.write(new File(path,filename));

                        //删除临时文件(如果文件小于10KB只会在内存中，大于10kb会产生临时文件)
                    fileItem.delete();

                }
        }


        System.out.println("文件上传。。。。。");
        return "pages/success.jsp";
    }
}
