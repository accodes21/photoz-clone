package com.jetbrains.marco.photoz.clone;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotozService {
    private Map<String, Photo> db = new HashMap<>() {
        {
            put("1", new Photo("1", "hello.jpeg"));
        }
    };

    public Collection<Photo> get() {
        return db.values();

    }

    public Photo get(String id) {
        return db.get(id);
    }

    public Photo remove(String id) {
        return db.remove(id);
    }

    @PostMapping("/photoz")
    public Photo save(String fileName, byte[] data) throws Throwable {
        Photo photo = new Photo();
        photo.setId(UUID.randomUUID().toString());
        photo.setFileName(fileName);
        photo.setData(data);
        db.put(photo.getId(), photo);
        return photo;
    }

}
