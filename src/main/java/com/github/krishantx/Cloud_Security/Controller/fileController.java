package com.github.krishantx.Cloud_Security.Controller;

import com.github.krishantx.Cloud_Security.model.FileModel;
import com.github.krishantx.Cloud_Security.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class fileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public String uploadFile(HttpServletRequest request, @RequestParam("multipartFile") MultipartFile multipartFile, @RequestParam("fileName") String fileName) throws Exception{
        return fileService.uploadFile(request, multipartFile, fileName);
    }

    @GetMapping("/download/{id}")
    public  ResponseEntity<?> downloadFile(HttpServletRequest req, @PathVariable int id) throws IOException {
        return fileService.downloadFile(req, id);
    }

    @GetMapping("/my-uploads")
    public List<FileModel> myUploads(HttpServletRequest req) {
        return fileService.myUploads(req);
    }
}
