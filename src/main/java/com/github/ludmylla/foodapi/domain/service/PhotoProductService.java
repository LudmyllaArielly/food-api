package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.model.PhotoProduct;
import com.github.ludmylla.foodapi.domain.repository.ProductRepository;
import com.github.ludmylla.foodapi.domain.service.exceptions.PhotoProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class PhotoProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PhotoStorageService photoStorageService;

    @Transactional
    public PhotoProduct save (PhotoProduct photoProduct, InputStream dataFile){

        Long restaurantId = photoProduct.getRestaurantId();
        Long productId = photoProduct.getProduct().getId();

        generateNewFileName(photoProduct);
        String fileNameExisting = null;

        Optional<PhotoProduct> existingPhoto =
                productRepository.findPhotoById(restaurantId, productId);

        if(existingPhoto.isPresent()){
            fileNameExisting = existingPhoto.get().getFileName();
            productRepository.delete(existingPhoto.get());
        }

        photoProduct = productRepository.save(photoProduct);
        productRepository.flush();

        storage(photoProduct, dataFile, fileNameExisting);

        return photoProduct;
    }

    public PhotoProduct findById(Long restaurantId, Long productId){
        return productRepository.findPhotoById(restaurantId, productId)
                .orElseThrow(() -> new PhotoProductNotFoundException(restaurantId, productId));
    }

    @Transactional
    public void delete(Long restaurantId, Long productId) {
        PhotoProduct photo = findById(restaurantId, productId);

        productRepository.delete(photo);
        productRepository.flush();

        photoStorageService.remove(photo.getFileName());
    }

    private PhotoProduct storage (PhotoProduct photoProduct, InputStream dataFile, String fileNameExisting ){

        PhotoStorageService.NewPhoto newPhoto = PhotoStorageService.NewPhoto
                .builder()
                .fileName(photoProduct.getFileName())
                .contentType(photoProduct.getContentType())
                .inputStream(dataFile)
                .build();

        photoStorageService.replace(fileNameExisting, newPhoto);

        return photoProduct;
    }

    private PhotoProduct generateNewFileName(PhotoProduct photoProduct){
        String newFileName = photoStorageService.generateFileName(photoProduct.getFileName());
        photoProduct.setFileName(newFileName);
        return photoProduct;
    }

}
