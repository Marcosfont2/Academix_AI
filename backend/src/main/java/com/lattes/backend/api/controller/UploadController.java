package com.lattes.backend.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // This allows Next.js to talk to Java
public class UploadController {

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("lattes") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            // For now, we just save it to a temporary directory to prove it works
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File directory = new File(uploadDir);
            if (!directory.exists()) directory.mkdir();

            file.transferTo(new File(uploadDir + file.getOriginalFilename()));

            return ResponseEntity.ok("File uploaded successfully to backend!");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error saving file: " + e.getMessage());
        }
    }
}