package org.route.repository;

import org.route.model.Brand;

import java.util.List;

public interface BrandRepository {
    List<Brand> findAll();
    void save(Brand brand);
    boolean isExist(String name);
    boolean isExistSlug(String slug);
}
