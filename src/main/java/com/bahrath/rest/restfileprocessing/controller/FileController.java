package com.bahrath.rest.restfileprocessing.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class FileController {

    @Value("${uploadDir}")
    private String UPLOAD_DIR;

    @PostMapping("/upload")
    public boolean upload(@RequestParam("file") MultipartFile file) throws IOException {
        file.transferTo(new File(UPLOAD_DIR + file.getOriginalFilename()));
        return true;
    }

}
