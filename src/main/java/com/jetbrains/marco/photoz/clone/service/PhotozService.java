package com.jetbrains.marco.photoz.clone.service;

import org.springframework.stereotype.Service;

import com.jetbrains.marco.photoz.clone.model.Photo;
import com.jetbrains.marco.photoz.clone.repository.PhotozRepository;

@Service
public class PhotozService {
    private final PhotozRepository photozRepository;

    public PhotozService(PhotozRepository photozRepository) {
        this.photozRepository = photozRepository;
    }

    // Retrieve all photos
    public Iterable<Photo> get() {
        return photozRepository.findAll();
    }

    // Retrieve a specific photo by ID
    public Photo get(Integer id) {
        return photozRepository.findById(id).orElse(null);
    }

    // Delete a photo by ID
    public void remove(Integer id) {
        photozRepository.deleteById(id);
    }

    // Save a new photo
    public Photo save(String fileName, String contentType, byte[] data) {
        Photo photo = new Photo();
        photo.setContentType(contentType);
        photo.setFileName(fileName);
        photo.setData(data);
        return photozRepository.save(photo);
    }

    // Update an existing photo
    public Photo save(String fileName, String contentType, byte[] data, Integer id) {
        Photo photo = get(id);
        if (photo != null) {
            photo.setFileName(fileName);
            photo.setContentType(contentType);
            photo.setData(data);
            return photozRepository.save(photo);
        }
        return null; // Return null if the photo doesn't exist
    }
}
