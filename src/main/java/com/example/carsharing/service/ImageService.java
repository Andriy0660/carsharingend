package com.example.carsharing.service;


import com.example.carsharing.entity.ImageData;
import com.example.carsharing.repository.ImageRepository;
import com.example.carsharing.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository repository;
    public void uploadImage(MultipartFile file) throws IOException {
        repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(
                        ImageUtils.resizeImage(file.getBytes(),150,150)))
                .build());
    }

    public byte[] getImage(String fileName){
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }
    public ImageData getImageData(String fileName){
        return repository.findByName(fileName).orElseThrow(
                ()->new NoSuchElementException("No images with this file name"));
    }

}
