package com.communication.app.services;
import jakarta.servlet.http.HttpServletRequest;
import net.coobird.thumbnailator.Thumbnails;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class StorageService {
    @Value("${server.address}")
    private String SERVER_DOMAIN;
    @Value("${server.port}")
    private String SERVER_PORT;
    @Autowired
    private  HttpServletRequest request;
    private static String userHome = System.getProperty("user.home");
    @Value("${spring.web.resources.static-locations}")
    private String UPLOAD_DIR;
    @SneakyThrows
    private byte[] compressImage(MultipartFile image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(image.getInputStream())
                .size(500, 500)
                .outputQuality(0.5)
                .outputFormat("jpg")
                .toOutputStream(outputStream);
        return outputStream.toByteArray();
    }
    public  String getServerLink(String fileName) {
       return  String.format("%s://%s:%s/%s",request.getScheme(), SERVER_DOMAIN, SERVER_PORT, fileName);
    }
    @SneakyThrows
    public String uploadFile(MultipartFile file) {
        byte[] compressedImage = compressImage(file);
        String uploadDirPath = UPLOAD_DIR.replace("file:/", "");
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String fileName = UUID.randomUUID() + ".jpg";
        Path path = Paths.get(uploadDirPath +File.separator + fileName);
        Files.write(path, compressedImage);
        String fileLink = getServerLink(fileName);
        return fileLink;
    }
}
