package com.communication.app.services;
import jakarta.servlet.http.HttpServletRequest;
import net.coobird.thumbnailator.Thumbnails;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StorageService {
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
    public List<String> getFilesLinks(File[] files) {
        List<String> links = new ArrayList<>();
        for(File file: files) {
            links.add(getServerLink(file.getName()));
        }
        return links;
    }
    public String getServerLink(String fileName) {
        return  String.format("%s://%s:%s/%s",request.getScheme(), request.getServerName(), request.getServerPort(), fileName);
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
    public List<String> getAllMedias() {
        String uploadDirPath = UPLOAD_DIR.replace("file:/", "");
        File uploadDir = new File(uploadDirPath);
        File[] files = uploadDir.listFiles();
        return getFilesLinks(files);
    }

}
