package com.dev.backendspringboot.drive;

import java.util.Collections;
import com.google.api.services.drive.model.File;
import org.springframework.stereotype.Component;

@Component
public class GoogleDriveUtil {

    File getFile(java.io.File file, String folderId) {
        File fileMetaData = new File();
        fileMetaData.setName(file.getName());
        fileMetaData.setParents(Collections.singletonList(folderId));
        return fileMetaData;
    }
}
