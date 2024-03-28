package com.dev.backendspringboot.drive;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

@Configuration
public class GoogleDriveConfig{
    private static final String APPLICATION_NAME = "Web-based Freight Management Application";
    private static final String CREDENTIALS_FILE_PATH = "cloud.json";
    public Drive googleDrive() throws GeneralSecurityException, IOException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        InputStream credentialsStream = getClass().getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);

        return new Drive.Builder(httpTransport, jsonFactory, new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

}
