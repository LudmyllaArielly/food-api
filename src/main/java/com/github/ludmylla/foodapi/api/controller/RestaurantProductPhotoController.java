package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.api.assembler.PhotoProductModelAssembler;
import com.github.ludmylla.foodapi.domain.dtos.PhotoProductModel;
import com.github.ludmylla.foodapi.domain.dtos.input.PhotoProductInput;
import com.github.ludmylla.foodapi.domain.model.PhotoProduct;
import com.github.ludmylla.foodapi.domain.model.Product;
import com.github.ludmylla.foodapi.domain.service.PhotoProductService;
import com.github.ludmylla.foodapi.domain.service.PhotoStorageService;
import com.github.ludmylla.foodapi.domain.service.ProductService;
import com.github.ludmylla.foodapi.domain.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {

    @Autowired
    private PhotoProductService photoProductService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PhotoProductModelAssembler photoProductModelAssembler;

    @Autowired
    private PhotoStorageService photoStorageService;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PhotoProductModel updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId,
                                         @Valid PhotoProductInput photoProductInput) throws IOException {

        Product product = productService.findByIdRestaurant(restaurantId, productId);
        MultipartFile multipartFile = photoProductInput.getFile();

        PhotoProduct photo = new PhotoProduct();
        photo.setProduct(product);
        photo.setDescription(photoProductInput.getDescription());
        photo.setContentType(multipartFile.getContentType());
        photo.setSize(multipartFile.getSize());
        photo.setFileName(multipartFile.getOriginalFilename());

        PhotoProduct photoSave = photoProductService.save(photo, multipartFile.getInputStream());

        return photoProductModelAssembler.toModel(photoSave);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PhotoProductModel> findPhoto(@PathVariable Long restaurantId, @PathVariable Long productId) {
        PhotoProduct photoProduct = photoProductService.findById(restaurantId, productId);
        return ResponseEntity.ok(photoProductModelAssembler.toModel(photoProduct));
    }

    @GetMapping
    public ResponseEntity<?> showPhoto(@PathVariable Long restaurantId, @PathVariable Long productId,
                                       @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {
            PhotoProduct photoProduct = photoProductService.findById(restaurantId, productId);

            MediaType mediaTypePhoto = MediaType.parseMediaType(photoProduct.getContentType());
            List<MediaType> mediaTypesAccepted = MediaType.parseMediaTypes(acceptHeader);
            checkCompatibilityMediaType(mediaTypePhoto, mediaTypesAccepted);

            PhotoStorageService.PhotoRestore photoRestore = photoStorageService.toRestore(photoProduct.getFileName());

            if (photoRestore.haveUrl()) {

                return ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.CONTENT_LOCATION, photoRestore.getUrl()).build();
            } else {
                return ResponseEntity.ok()
                        .contentType(mediaTypePhoto)
                        .body(new InputStreamResource(photoRestore.getInputStream()));
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long restaurantId, @PathVariable Long productId) {
        photoProductService.delete(restaurantId, productId);
        return ResponseEntity.noContent().build();
    }

    private void checkCompatibilityMediaType(MediaType mediaTypePhoto, List<MediaType> mediaTypesAccepted) throws HttpMediaTypeNotAcceptableException {
        boolean compatible = mediaTypesAccepted.stream()
                .anyMatch(mediaTypeAccepted -> mediaTypeAccepted.isCompatibleWith(mediaTypePhoto));

        if (!compatible) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAccepted);
        }
    }

}
