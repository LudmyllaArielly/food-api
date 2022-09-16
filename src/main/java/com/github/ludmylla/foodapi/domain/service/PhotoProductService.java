package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.model.PhotoProduct;
import com.github.ludmylla.foodapi.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PhotoProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public PhotoProduct save (PhotoProduct photoProduct){
        existingPhoto(photoProduct);
        return productRepository.save(photoProduct);
    }

    private PhotoProduct existingPhoto(PhotoProduct photoProduct){
        Long restaurantId = photoProduct.getRestaurantId();
        Long productId = photoProduct.getProduct().getId();

        Optional<PhotoProduct> existingPhoto =
                productRepository.findPhotoById(restaurantId, productId);

        if(existingPhoto.isPresent()){
            productRepository.delete(existingPhoto.get());
        }

        return photoProduct;
    }
}
