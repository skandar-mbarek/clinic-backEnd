package com.clinic.userservice.controllers;


import com.clinic.userservice.constants.Constants;
import com.clinic.userservice.utils.FileStorage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.util.Collections;

@RestController
@RequestMapping(Constants.APP_ROOT+ "/file")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "File endPoints")
public class FileController {


    private final FileStorage fileStorage;

    @Operation(summary = "upload files")
    @PostMapping(value = "uploadImage")
    public ResponseEntity<Object> uploadImage(@PathParam("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap("file", fileStorage.storeFile(file)));
    }

    @Operation(summary = "Load file")
    @GetMapping("{fileName:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
        Resource resource = fileStorage.loadFileAsResource(fileName);

        // Get the file extension
        String fileExtension = fileStorage.getFileExtension(fileName);

        // Check file type and return appropriate response
        if (fileExtension.equalsIgnoreCase("pdf")) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else if (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg") ||
                fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("gif")) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Adjust content type as per your needs
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(null);
        }
    }



}
