package com.jetbrains.marco.photoz.clone.repository;

import org.springframework.data.repository.CrudRepository;

import com.jetbrains.marco.photoz.clone.model.Photo;

public interface PhotozRepository extends CrudRepository<Photo, Integer> {
    
}
