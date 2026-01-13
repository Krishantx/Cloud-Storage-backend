package com.github.krishantx.Cloud_Security.service;


import com.github.krishantx.Cloud_Security.model.FileModel;
import com.github.krishantx.Cloud_Security.repo.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileRepo fileRepo;

    public ResponseEntity<Resource> downloadFile(int fileId) throws IOException {
        Optional<FileModel> fileModel = fileRepo.findById(fileId);

        if (fileModel.isEmpty()) {
            return ResponseEntity.notFound().build();
        }


        Path storage = Paths.get("storage").toAbsolutePath().normalize();
        String file = Integer.toString(fileId);
        Path filePath = storage.resolve(file).normalize();

        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(filePath);

        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        System.out.println(contentType);

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileModel.get().getFileName() + "\"")
                .body(resource);
    }

    public String uploadFile(MultipartFile multipartFile, String fileName) throws Exception {
        //Create an entry in the database for the file and fetch that data
        FileModel fileModel = new FileModel(fileName);
        FileModel model = fileRepo.save(fileModel);

        // Creates a directory called storage if it does  not exist
        Path storageRoot = Path.of("storage");
        Files.createDirectories(storageRoot);
        String file = Integer.toString(model.getFileId());
        Path targetPath = storageRoot.resolve(file);

        try (InputStream in = multipartFile.getInputStream()) {
            Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println(e);
            return "Failed";
        }
        return "Success";
    }

}
