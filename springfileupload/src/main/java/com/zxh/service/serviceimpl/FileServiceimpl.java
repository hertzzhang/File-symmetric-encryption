package com.zxh.service.serviceimpl;


import com.zxh.dao.daoimpl.FileDaoimpl;
import com.zxh.pojo.IFile;
import com.zxh.service.FileService;
import com.zxh.utils.Secret;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service(value = "fileservice")
public class FileServiceimpl implements FileService {


    SecretKey secretKey = Secret.secretKey();
    String lastname;

    @Resource(name = "ifile")
    private IFile ifile;

    @Resource(name = "filedao")
    private FileDaoimpl fileDaoimpl;

    public FileServiceimpl() throws NoSuchAlgorithmException {
    }

    @Override
    public IFile SelectFile(String path, MultipartFile fileload) throws Exception {

        //判断该路径是否存在
       File file = new File(path);
        if(!file.exists())
        {
            //如果不存在则创建该文件夹
            file.mkdir();
        }

        //获取上传文件的名称
        String filename = fileload.getOriginalFilename();


        //获取上传的文件大小
        double dsize = Double.valueOf(fileload.getSize())/1024/1024;
        String ssize = String.valueOf(dsize);



         //获取文件类型
        String typename = filename.substring(filename.lastIndexOf("."));


        //把文件名称设置成唯一值防止覆盖，uuid
        String uuid = UUID.randomUUID().toString().replace("-","");
        //把uuid和后缀名拼接起来。
         lastname=uuid+typename;

        //获取文件上传的日期
        String time = path.substring(path.lastIndexOf("\\")+1);


        //获取加密过后的文件

        MultipartFile multipartFile = Secret.encrypt(fileload,secretKey);
        System.out.println("这里是kkkk对象"+secretKey);

        //完成加密文件上传
        multipartFile.transferTo(new File(path,lastname));

        //封装文件信息传递到IFile对象中
        ifile.setUuid(uuid);
        ifile.setSize(ssize);
        ifile.setType(typename);
        ifile.setFilename(filename);
        ifile.setTime(time);
        ifile.setAddress(time+"\\"+lastname);


        fileDaoimpl.SelectfileDao(ifile);

        return ifile;

    }
    public  void Uploadfile(HttpServletResponse resp,String name,String path) throws Exception {

        //获取文件的类型
        String filetype = name.substring(name.lastIndexOf(".")+1);
        //获取文件的位置。
        String path1 = path+"\\"+name.substring(0,name.indexOf("\\"));



        //把下载文件的地址给与file
        File file  = new File(path+"\\"+name);

        //把file转换成multipartfile
        InputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(name, inputStream);


        //文件解密下载

        MultipartFile multipartFile1=Secret.decrypt(multipartFile,secretKey);

        //把multiparfile1转换成file文件。
        FileUtils.copyInputStreamToFile(multipartFile1.getInputStream(), file);
        //打开输入流
        InputStream is  = new FileInputStream(file);
        //设置需要下载的文件大小，文件类型，请求头和文件名
        resp.setContentLength((int)file.length());
        resp.setContentType(filetype);
        resp.setHeader("Content-Disposition","attachment;filename="+name);

        //打开输出流，下载到本地硬盘中
        OutputStream os  = resp.getOutputStream();
        IOUtils.copy(is,os);

        //再次加密文件
        MultipartFile multipartFile2 = Secret.encrypt(multipartFile1,secretKey);
        multipartFile2.transferTo(new File(path1,lastname));

        os.close();
        is.close();

    }
}