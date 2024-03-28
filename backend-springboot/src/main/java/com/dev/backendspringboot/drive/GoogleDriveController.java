package com.dev.backendspringboot.drive;

import com.dev.backendspringboot.api.dto.response.ImageApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.services.drive.model.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/api/v1/googleCloud")
public class GoogleDriveController {
    private GoogleDriveService googleDriveService;
    private GoogleDriveConfig googleDriveConfig;
    @Autowired
    public GoogleDriveController(GoogleDriveService googleDriveService, GoogleDriveConfig googleDriveConfig) {
        this.googleDriveService = googleDriveService;
        this.googleDriveConfig = googleDriveConfig;
    }
    @PostMapping("/upload")
    public ResponseEntity<ImageApi> handleFileUpload(@RequestParam("image") MultipartFile file) throws IOException, GeneralSecurityException {
        if (file.isEmpty()) {
            ImageApi messageApi = ImageApi.builder().message("Hình ảnh rỗng").build();
            return new ResponseEntity<>(messageApi, HttpStatus.NOT_FOUND);
        }
        java.io.File tempFile = java.io.File.createTempFile("image", null);
        file.transferTo(tempFile);
        return ResponseEntity.ok(googleDriveService.saveNewImage(tempFile));
    }
    //TODO:down load image from google drive
    @GetMapping("/upload/{fileName}")
    public ResponseEntity<byte[]> downloadImageByGoogleDrive(@PathVariable String fileName) throws IOException, GeneralSecurityException {
        File file = googleDriveConfig.googleDrive().files().get(fileName).execute();
        return ResponseEntity.ok(googleDriveService.downloadFile(googleDriveConfig, file));
    }
}
