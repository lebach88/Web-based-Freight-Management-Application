package com.dev.backendspringboot.api.controller;

import com.dev.backendspringboot.api.dto.response.ImageDataDto;
import com.dev.backendspringboot.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {
    private ImageService imageService;
    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/uploadToMongoDB")
    public ResponseEntity<ImageDataDto> uploadImageToMongoDB(@RequestParam("image") MultipartFile file) throws IOException {
        return ResponseEntity.ok(imageService.uploadImageToMongoDB(file));
    }

    @GetMapping("/uploadToMongoDB/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imageData=imageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}
