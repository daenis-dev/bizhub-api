package com.greenpalmsolutions.security.files.internal;

import com.greenpalmsolutions.security.files.api.behavior.DownloadFile;
import com.greenpalmsolutions.security.files.api.behavior.DownloadFilesAsZip;
import com.greenpalmsolutions.security.files.api.behavior.UploadFile;
import com.greenpalmsolutions.security.files.api.model.UploadFileRequest;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
class FileService implements DownloadFile, DownloadFilesAsZip, UploadFile {

    @Override
    public void uploadFileForRequest(UploadFileRequest request) {
        final Path STORAGE_PATH = Paths.get(request.getFilePath());

        try {
            Files.createDirectories(STORAGE_PATH.getParent());

            try (FileOutputStream fos = new FileOutputStream(STORAGE_PATH.toFile());
                 ZipOutputStream zos = new ZipOutputStream(fos)) {

                String fileName = STORAGE_PATH.getFileName().toString();
                ZipEntry zipEntry = new ZipEntry(fileName);
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
            throw new RuntimeException("Error while downloading file", ex);
        }
    }

    // TODO: IT
    @Override
    public byte[] downloadFilesForFilePaths(List<String> filePaths) {
        try {
            // Create a temporary directory for extracted files
            Path tempDir = Files.createTempDirectory("unzipped_files");

            for (String filePath : filePaths) {
                unzipFile(filePath, tempDir);
            }

            return zipDirectory(tempDir);
        } catch (IOException e) {
            throw new RuntimeException("Error while processing backup files", e);
        }
    }


    private void unzipFile(String zipFilePath, Path outputDir) throws IOException {
        try (FileInputStream fis = new FileInputStream(zipFilePath);
             ZipInputStream zis = new ZipInputStream(fis)) {

            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                Path extractedFilePath = outputDir.resolve(zipEntry.getName());
                Files.createDirectories(extractedFilePath.getParent());

                try (FileOutputStream fos = new FileOutputStream(extractedFilePath.toFile());
                     BufferedOutputStream bos = new BufferedOutputStream(fos)) {

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = zis.read(buffer)) != -1) {
                        bos.write(buffer, 0, length);
                    }
                }
            }
        }
    }

    private byte[] zipDirectory(Path dir) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream);
             Stream<Path> walk = Files.walk(dir)) {

            walk.filter(Files::isRegularFile).forEach(file -> {
                try {
                    ZipEntry zipEntry = new ZipEntry(dir.relativize(file).toString().replace("\\", "/"));
                    zipOut.putNextEntry(zipEntry);
                    Files.copy(file, zipOut);
                    zipOut.closeEntry();
                } catch (IOException e) {
                    throw new RuntimeException("Error while zipping file: " + file, e);
                }
            });
        }

        return byteArrayOutputStream.toByteArray();
    }


}
