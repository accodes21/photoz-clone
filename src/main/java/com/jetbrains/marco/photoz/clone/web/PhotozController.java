package com.jetbrains.marco.photoz.clone.web;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.jetbrains.marco.photoz.clone.model.Photo;
import com.jetbrains.marco.photoz.clone.service.PhotozService;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.http.HttpStatus;

@RestController
public class PhotozController {

    private final PhotozService photozService;

    public PhotozController(PhotozService photozService) {
        this.photozService = photozService;
    }

    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/photoz")
    public String photozPage() throws IOException {
        // Serve the HTML page as a String
        var htmlFile = new ClassPathResource("static/photoz.html");
        return Files.readString(htmlFile.getFile().toPath());
    }

    @GetMapping("/photoz/api")
    public Iterable<Photo> get() {
        return photozService.get();
    }

    @GetMapping("/photoz/api/{id}")
    public Photo get(@PathVariable Integer id) {
        Photo photo = photozService.get(id);
        if (photo == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return photo;
    }

    @DeleteMapping("/photoz/api/{id}")
    public void delete(@PathVariable Integer id) {
        photozService.remove(id);
    }

    @PostMapping(value = "/photoz/api", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Photo create(@RequestPart("data") MultipartFile file) throws Throwable {
        return photozService.save(file.getOriginalFilename(), file.getContentType(), file.getBytes());
    }
}
