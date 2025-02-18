package com.greenpalmsolutions.security.files.internal;

import com.greenpalmsolutions.security.files.api.behavior.DownloadFiles;
import com.greenpalmsolutions.security.files.api.behavior.UploadFile;
import com.greenpalmsolutions.security.files.api.model.UploadFileRequest;
import org.springframework.context.annotation.Profile;
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

@Profile("!prod")
@Service
class LocalFileService implements DownloadFiles, UploadFile {

    @Override
    public void uploadFileForRequest(UploadFileRequest request) {
        final Path STORAGE_PATH = Paths.get(request.getFilePath().replaceAll("\\.[^.]*$", ".zip"));

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
    public byte[] downloadZipForFilePaths(List<String> filePaths) {
        try {
            Path tempDir = Files.createTempDirectory("temp_unzip");
            Path bizhubBackupsDir = tempDir.resolve("bizhub-backups");
            Files.createDirectories(bizhubBackupsDir);

            for (String filePath : filePaths) {
                String originalFileName = Paths.get(filePath).getFileName().toString();

                Path zipFilePath = Paths.get(filePath.replaceAll("\\.[^.]+$", ".zip"));

                unzipFile(zipFilePath, bizhubBackupsDir, originalFileName);
            }

            return zipDirectory(bizhubBackupsDir);
        } catch (IOException e) {
            throw new RuntimeException("Error while processing backup files", e);
        }
    }

    private void unzipFile(Path zipFilePath, Path outputDir, String originalFileName) throws IOException {
        try (FileInputStream fis = new FileInputStream(zipFilePath.toFile());
             ZipInputStream zis = new ZipInputStream(fis)) {

            while (zis.getNextEntry() != null) {
                Path extractedFilePath = outputDir.resolve(originalFileName);
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
