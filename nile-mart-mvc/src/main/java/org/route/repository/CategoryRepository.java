package org.route.repository;


import org.route.model.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
    void save(Category category);
    Category findBySlug(String slug);
}







