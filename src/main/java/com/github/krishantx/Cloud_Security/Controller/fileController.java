package com.github.krishantx.Cloud_Security.Controller;

import com.github.krishantx.Cloud_Security.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
public class fileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("multipartFile") MultipartFile multipartFile, @RequestParam("fileName") String fileName) throws Exception{
        return fileService.uploadFile(multipartFile, fileName);
    }

    @GetMapping("/download/{id}")
    public  ResponseEntity<Resource> downloadFile(@PathVariable int id) throws IOException {
        return fileService.downloadFile(id);
    }
}
