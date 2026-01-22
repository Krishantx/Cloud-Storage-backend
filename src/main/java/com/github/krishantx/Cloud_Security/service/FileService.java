package com.github.krishantx.Cloud_Security.service;


import com.github.krishantx.Cloud_Security.model.FileModel;
import com.github.krishantx.Cloud_Security.model.UserModel;
import com.github.krishantx.Cloud_Security.repo.FileRepo;
import com.github.krishantx.Cloud_Security.repo.UserRepo;
import com.github.krishantx.Cloud_Security.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileRepo fileRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    JwtUtil jwtUtil;

    public ResponseEntity<?> downloadFile(HttpServletRequest req, int fileId) throws IOException {
        Optional<FileModel> fileModel = fileRepo.findById(fileId);

        String token = req.getHeader("Authentication").substring(7);
        String username = jwtUtil.extractUsername(token);
        Optional<UserModel> userModel = userRepo.findByUsername(username);


        if (fileModel.isEmpty())
            return ResponseEntity
                    .notFound()
                    .build();

        if (!fileModel.get().getOwner().getUsername().equals(userModel.get().getUsername()))
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("The resource does not belong to the user");



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

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileModel.get().getFileName() + "\"")
                .body(resource);
    }

    public String uploadFile(HttpServletRequest req, MultipartFile multipartFile, String fileName) throws Exception {

        //Figure out who uploaded the file
        String token = req.getHeader("Authentication").substring(7);
        String username = jwtUtil.extractUsername(token);

        //Create an entry in the database for the file and fetch that data
        Optional<UserModel> owner = userRepo.findByUsername(username);
        if (owner.isEmpty()) return "User not found";
        FileModel fileModel = new FileModel(fileName, owner.get());
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

    public List<FileModel> myUploads(HttpServletRequest req) {
        String token = req.getHeader("Authentication").substring(7);
        String username = jwtUtil.extractUsername(token);
        return fileRepo.findByOwnerUsername(username);
    }
}
