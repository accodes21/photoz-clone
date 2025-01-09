package com.jetbrains.marco.photoz.clone;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class DownloadController {

    private PhotozService photozService;

    public DownloadController(PhotozService photozService) {
        this.photozService = photozService;
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable String id){
        byte[] data = new byte[0];
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
    }
}
