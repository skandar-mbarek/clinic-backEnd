package com.clinic.userservice.controllers;


import com.clinic.userservice.constants.Constants;
import com.clinic.userservice.utils.FileStorage;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.util.Collections;

@RestController
@RequestMapping(Constants.APP_ROOT+ "/file")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FileController {


    private final FileStorage fileStorage;

    @PostMapping(value = "uploadImage")
    public ResponseEntity<Object> uploadImage(@PathParam("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap("file", fileStorage.storeFile(file)));
    }
    @GetMapping("img/{fileName:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String fileName) {
        Resource resource = fileStorage.loadFileAsResource(fileName);
        return   ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(resource);
    }

}
