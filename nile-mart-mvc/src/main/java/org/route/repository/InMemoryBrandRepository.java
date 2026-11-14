package org.route.repository;

import jakarta.annotation.PostConstruct;
import org.route.model.Brand;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryBrandRepository implements BrandRepository {

   private final  List<Brand> brands = new ArrayList<>();

    @PostConstruct
    void init() {
        brands.add(Brand.builder()
                .name("Samsung")
                .slug("samsung")
                .active(true)
                .build());
        brands.add(Brand.builder()
                .name("Asus")
                .slug("asus")
                .active(true)
                .build());
        brands.add(Brand.builder()
                .name("Anker")
                .slug("anker")
                .active(true)
                .build());
        brands.add(Brand.builder()
                .name("Tefal")
                .slug("tefal")
                .active(true)
                .build());
        brands.add(Brand.builder()
                .name("Old Brand")
                .slug("old-brand")
                .active(false)
                .build());

    }


    @Override
    public List<Brand> findAll() {
       return brands;
    }

    @Override
    public void save(Brand brand) {
      brands.add(brand);
    }

    @Override
    public boolean isExist(String name) {
      return  brands.stream().anyMatch(b -> b.getName().equalsIgnoreCase(name));
    }

    @Override
    public boolean isExistSlug(String slug) {
        return brands.stream().anyMatch(b -> b.getSlug().equalsIgnoreCase(slug));
    }
}
