package com.dev.backendspringboot.drive;

import com.dev.backendspringboot.api.dto.response.ImageApi;
import com.google.api.services.drive.model.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GoogleDriveService {
    ImageApi saveNewImage(java.io.File tempFile) throws GeneralSecurityException, IOException;

    byte[] downloadFile(GoogleDriveConfig googleDriveConfig, File file) throws IOException, GeneralSecurityException;
}
