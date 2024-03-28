package com.dev.backendspringboot.service.Impl;

import com.dev.backendspringboot.api.dto.response.ImageApi;
import com.dev.backendspringboot.api.dto.response.ImageDataDto;
import com.dev.backendspringboot.document.ImageData;
import com.dev.backendspringboot.repository.ImageDataRepository;
import com.dev.backendspringboot.service.ImageService;
import com.dev.backendspringboot.service.util.ImageUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    private ImageDataRepository imageDataRepository;
    private ImageUtil imageUtil;
    @Autowired
    public ImageServiceImpl(ImageDataRepository imageDataRepository, ImageUtil imageUtil) {
        this.imageDataRepository = imageDataRepository;
        this.imageUtil = imageUtil;
    }

    public ImageDataDto uploadImageToMongoDB(MultipartFile file) throws IOException {

        ImageData imageData = imageDataRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(imageUtil.compressImage(file.getBytes())).build());
        if (imageData != null) {
//            return "file uploaded successfully : " + file.getOriginalFilename();
            return imageUtil.mapImageDataToImageDataDto(imageData);
        }
        return null;
    }

    public byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData = imageDataRepository.findByName(fileName);
        byte[] images=imageUtil.decompressImage(dbImageData.get().getImageData());
        return images;
    }
    public void deleteImageById(String imageId){
        imageDataRepository.deleteById(imageId);
    }
}
