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
@RequestMapping("/photoz")
public class PhotozController {

    private final PhotozService photozService;

    public PhotozController(PhotozService photozService) {
        this.photozService = photozService;
    }

    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }

    @GetMapping
    public String photozPage() throws IOException {
        // Serve the HTML page as a String
        var htmlFile = new ClassPathResource("static/photoz.html");
        return Files.readString(htmlFile.getFile().toPath());
    }

    @GetMapping("/api")
    public Iterable<Photo> getAllPhotos() {
        return photozService.get();
    }

    @GetMapping("/api/{id}")
    public Photo getPhoto(@PathVariable Integer id) {
        Photo photo = photozService.get(id);
        if (photo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found");
        }
        return photo;
    }

    @DeleteMapping("/api/{id}")
    public void deletePhoto(@PathVariable Integer id) {
        photozService.remove(id);
    }

    @PostMapping(value = "/api", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Photo createPhoto(@RequestPart("data") MultipartFile file) throws IOException {
        return photozService.save(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes()
        );
    }

    @PutMapping(value = "/api/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Photo updatePhoto(
            @PathVariable Integer id,
            @RequestPart("data") MultipartFile file
    ) throws IOException {
        Photo photo = photozService.get(id);
        if (photo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found");
        }

        // Update photo details
        return photozService.save(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes(),
                id
        );
    }
}
