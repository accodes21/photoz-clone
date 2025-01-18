package com.jetbrains.marco.photoz.clone.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.jetbrains.marco.photoz.clone.model.Photo;
import com.jetbrains.marco.photoz.clone.repository.PhotozRepository;

@Service
public class PhotozService {
    private final PhotozRepository photozRepositroy;

    public PhotozService(PhotozRepository photozRepositroy) {
        this.photozRepositroy = photozRepositroy;
    }

    public Iterable<Photo> get() {
        return photozRepositroy.findAll();
    }

    public Photo get(Integer id) {
        return photozRepositroy.findById(id).orElse(null);
    }

    public void remove(Integer id) {
        photozRepositroy.deleteById(id);
    }

    @PostMapping("/photoz")
    public Photo save(String fileName, String contentType, byte[] data) throws Throwable {
        Photo photo = new Photo();
        photo.setContentType(contentType);
        photo.setFileName(fileName);
        photo.setData(data);
        photozRepositroy.save(photo);
        return photo;
    }

}
