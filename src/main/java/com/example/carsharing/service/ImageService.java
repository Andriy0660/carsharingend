package com.example.carsharing.service;


import com.example.carsharing.entity.ImageData;
import com.example.carsharing.repository.ImageRepository;
import com.example.carsharing.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository repository;

    public void uploadImage(MultipartFile file) throws IOException {
        byte[] imagedt= ImageUtils.resizeImage(file.getBytes(),150,150);
        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(imagedt)).build());

    }

    public byte[] getImage(String fileName){
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }
    public ImageData getImageData(String fileName){
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        return dbImageData.orElseThrow();
    }

}
