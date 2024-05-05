package com.clinic.userservice.utils;

import com.clinic.userservice.exceptions.BadRequestException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Service
@Data
public class FileStorage {


    @Value("${upload.path}")
    private String fileStorageLocation;


    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("mahoch mawjoud ************"+fileName);
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String timestamp = dateFormat.format(new Date());

            String newFileName = timestamp + "_" + fileName;

            Path targetLocation = Paths.get(fileStorageLocation).resolve(newFileName).normalize();

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return newFileName;
        } catch (IOException ex) {

            throw new RuntimeException("mahoch mawjoud ************"+fileName);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path imagePath = Paths.get(fileStorageLocation).resolve(fileName);
            System.out.println(imagePath);
            Resource resource = new FileSystemResource(imagePath.toFile());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new BadRequestException("file is not existe");
            }
        } catch (Exception e) {
            throw new RuntimeException("mahoch mawjoud ************");
        }
    }
    public String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

}
