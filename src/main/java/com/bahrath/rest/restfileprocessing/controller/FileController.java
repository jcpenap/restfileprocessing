package com.bahrath.rest.restfileprocessing.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class FileController {

    @Value("${uploadDir}")
    private String UPLOAD_DIR;

    @PostMapping("/upload")
    public boolean upload(@RequestParam("file") MultipartFile file) throws IOException {
        file.transferTo(new File(UPLOAD_DIR + file.getOriginalFilename()));
        return true;
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable("fileName") String fileName) throws IOException {
        byte[] fileData = Files.readAllBytes(new File(UPLOAD_DIR + fileName).toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(fileData, headers, HttpStatus.OK);
    }

}
