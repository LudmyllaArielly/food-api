package com.github.ludmylla.foodapi.domain.repository;

import com.github.ludmylla.foodapi.domain.model.PhotoProduct;

public interface ProductRepositoryQueries {

    PhotoProduct save(PhotoProduct product);

    void delete(PhotoProduct photoProduct);
}
