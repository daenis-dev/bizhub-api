package com.greenpalmsolutions.security.files.internal;

import com.greenpalmsolutions.security.files.api.behavior.DownloadFile;
import com.greenpalmsolutions.security.files.api.behavior.UploadFile;
import com.greenpalmsolutions.security.files.api.model.UploadFileRequest;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

// TODO: IT
@Service
class FileService implements DownloadFile, UploadFile {

    @Override
    public void uploadFileForRequest(UploadFileRequest request) {
        final Path STORAGE_PATH = Paths.get(request.getFilePath());

        try {
            Files.createDirectories(STORAGE_PATH.getParent());

            try (FileOutputStream fos = new FileOutputStream(STORAGE_PATH.toFile());
                 ZipOutputStream zos = new ZipOutputStream(fos)) {

                ZipEntry zipEntry = new ZipEntry(request.getFilePath());
                zos.putNextEntry(zipEntry);

                zos.write(request.getFileContents());
                zos.closeEntry();
            }
        } catch (IOException ex) {
            throw new RuntimeException("An error occurred while backing up the file", ex);
        }
    }

    @Override
    public byte[] downloadFileWithFilePath(String filePath) {
        try {
            Path zipFilePath = Paths.get(filePath);
            try (FileInputStream fis = new FileInputStream(zipFilePath.toFile());
                 ZipInputStream zis = new ZipInputStream(fis)) {

                ZipEntry zipEntry = zis.getNextEntry();
                if (zipEntry != null) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = zis.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, length);
                    }
                    return byteArrayOutputStream.toByteArray();
                } else {
                    throw new FileNotFoundException("No entries found in zip file.");
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error while retrieving backup", ex);
        }
    }
}
