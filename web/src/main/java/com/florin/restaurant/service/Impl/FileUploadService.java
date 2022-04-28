package com.florin.restaurant.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileUploadService {

    private String filePath="C:\\Users\\ffilip\\OneDrive - ENDAVA\\Desktop\\restaurant\\restaurant\\web\\src\\main\\resources\\static";

    public void uploadFile(MultipartFile file) throws IOException {
        file.transferTo(new File(filePath+file.getOriginalFilename()));
    }
}
