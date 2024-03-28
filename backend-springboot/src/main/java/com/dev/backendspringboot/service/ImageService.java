package com.dev.backendspringboot.service;


import com.dev.backendspringboot.api.dto.response.ImageApi;
import com.dev.backendspringboot.api.dto.response.ImageDataDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface ImageService {

    ImageDataDto uploadImageToMongoDB(MultipartFile file) throws IOException;

    byte[] downloadImage(String fileName);
     void deleteImageById(String id);
}
