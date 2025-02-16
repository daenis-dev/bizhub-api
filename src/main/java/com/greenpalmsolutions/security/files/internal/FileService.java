package com.greenpalmsolutions.security.files.internal;

import com.greenpalmsolutions.security.files.api.behavior.DownloadFiles;
import com.greenpalmsolutions.security.files.api.behavior.UploadFile;
import com.greenpalmsolutions.security.files.api.model.UploadFileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

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
@RequiredArgsConstructor
class FileService implements DownloadFiles, UploadFile {

    @Value("${user-file-bucket-name}")
    private String BUCKET_NAME;

    private final S3Client s3Client;

    @Override
    public void uploadFileForRequest(UploadFileRequest request) {
        final String s3Key = request.getFilePath();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(s3Key)
                    .build();

            try (ByteArrayInputStream fileContentsStream = new ByteArrayInputStream(request.getFileContents())) {
                s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(fileContentsStream, request.getFileContents().length));
            }

        } catch (IOException ex) {
            throw new RuntimeException("An error occurred while uploading the file to S3", ex);
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

                ResponseInputStream<GetObjectResponse> s3ObjectInputStream = downloadFileFromS3(filePath);

                unzipFile(s3ObjectInputStream, bizhubBackupsDir, originalFileName);
            }

            return zipDirectory(bizhubBackupsDir);

        } catch (IOException e) {
            throw new RuntimeException("Error while processing backup files", e);
        }
    }

    private ResponseInputStream<GetObjectResponse> downloadFileFromS3(String filePath) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(filePath.replaceAll("\\.[^.]*$", ".zip"))
                .build();

        try {
            return s3Client.getObject(getObjectRequest);
        } catch (S3Exception e) {
            throw new RuntimeException("Failed to download file from S3: " + filePath, e);
        }
    }

    private void unzipFile(ResponseInputStream<GetObjectResponse> s3ObjectInputStream, Path outputDir, String originalFileName) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(s3ObjectInputStream)) {

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
