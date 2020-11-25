package com.instagram.services;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.UrlFilenameViewController;

import javax.mail.Multipart;
import java.io.File;
import java.nio.file.Files;

@Service
public class FileUploadService {

    public String fileUpload(MultipartFile file, String fileName, String folderName)throws Exception{
        String originalFileName = file.getOriginalFilename();
        String extension = "";
        if(originalFileName.lastIndexOf(".") != -1 && originalFileName.lastIndexOf(".") != 0){
            extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        }
        String path = "S:\\instagram\\Instagram\\Instagram\\src\\main\\resources\\images\\" + fileName + "." + extension;
        file.transferTo(new File(path));
        return "image/" + fileName + "." + extension;
    }
}
