package com.setec.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUpdate {
    @Value("${file.upload-dir}")
    private static String uploadFolder;
    public static  String updateFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadFolder);
        if (Files.notExists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        if (file.isEmpty()) {
           return "";
        } else {
            String fileName = file.getOriginalFilename();
            Path targetPath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        }
        return  file.getOriginalFilename();
    }
}
