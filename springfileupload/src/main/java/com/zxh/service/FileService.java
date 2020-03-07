package com.zxh.service;


import com.zxh.pojo.IFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Service;
import java.io.IOException;

public interface FileService {
    IFile SelectFile(String path, MultipartFile fileload) throws  Exception;
    void Uploadfile(HttpServletResponse resp, String name, String path)throws Exception;

}
