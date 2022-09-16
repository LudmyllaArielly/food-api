package com.github.ludmylla.foodapi.infrastructure.repository;

import com.github.ludmylla.foodapi.domain.model.PhotoProduct;
import com.github.ludmylla.foodapi.domain.repository.ProductRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {

    @Autowired
    private EntityManager manager;

    @Transactional
    @Override
    public PhotoProduct save(PhotoProduct photoProduct) {
        return manager.merge(photoProduct);
    }

    @Transactional
    @Override
    public void delete(PhotoProduct photoProduct) {
         manager.remove(photoProduct);
    }
}
