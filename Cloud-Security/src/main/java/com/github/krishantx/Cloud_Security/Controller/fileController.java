package com.github.krishantx.Cloud_Security.Controller;

import com.github.krishantx.Cloud_Security.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
