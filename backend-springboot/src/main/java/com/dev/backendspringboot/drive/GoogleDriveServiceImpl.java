package com.dev.backendspringboot.drive;

import com.dev.backendspringboot.api.dto.response.ImageApi;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleDriveServiceImpl implements GoogleDriveService{
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_KEY_PATH = getPathToGoogleCredentials();

    private static String getPathToGoogleCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "cloud.json");
        return filePath.toString();
    }
    public ImageApi saveNewImage(java.io.File file) throws GeneralSecurityException, IOException {
        try{
            String folderId = "1yJc5xLsChEdvEp-8-HTMKazbhhflS3Y_";
            Drive drive = createDriveService();
            File fileMetaData = getFile(file, folderId);
            FileContent mediaContent = new FileContent("image/jpeg", file);

            File uploadedFile = drive.files().create(fileMetaData, mediaContent).setFields("id").execute();

            String image = "https://drive.google.com/uc?export=view&id="+uploadedFile.getId();
            System.out.println("IMAGE URL: " + image);
            file.delete();
            ImageApi imageApi = ImageApi.builder().status(200).message("Tải hình ảnh lên google drive thành công").image(URI.create(image)).build();
            return imageApi;
        }catch (Exception e){
            System.out.println(e.getMessage());
            ImageApi imageApi = ImageApi.builder().message(e.getMessage()).build();
            return imageApi;
        }
    }

    @Override
    public byte[] downloadFile(GoogleDriveConfig googleDriveConfig, File file) throws IOException, GeneralSecurityException {
        if (file.getWebContentLink() != null && !file.getWebContentLink().isEmpty()) {
            HttpResponse resp = googleDriveConfig.googleDrive().getRequestFactory().buildGetRequest(new GenericUrl(file.getWebContentLink())).execute();
            InputStream is = resp.getContent();
            byte[] fileBytes = IOUtils.toByteArray(is);
            return fileBytes;
        }
        return null;
    }

    private Drive createDriveService() throws GeneralSecurityException, IOException {
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .build();

    }
    private File getFile(java.io.File file, String folderId) {
        File fileMetaData = new File();
        fileMetaData.setName(file.getName());
        fileMetaData.setParents(Collections.singletonList(folderId));
        return fileMetaData;
    }
}
