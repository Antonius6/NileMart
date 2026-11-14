package org.route.service;

import org.route.model.Brand;
import org.route.repository.BrandRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Set;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    public void addBrand(Brand brand, BindingResult result){

        if(brand.getSlug() != null && !brand.getSlug().matches("^[a-z0-9-]+$"))
            result.rejectValue("slug", "400", "slug must match [a-z0-9]");

        if(brandRepository.isExist(brand.getName()))
            result.rejectValue("name", "400", "Brand with name already exists");
        if(brandRepository.isExistSlug(brand.getSlug()))
            result.rejectValue("slug", "400", "Brand with slug already exists");
        if (result.hasErrors()) {
            return;
        }

        brandRepository.save(brand);
    }


    public List<Brand> getActiveBrands() {
        return brandRepository.findAll().stream()
                .filter(Brand::isActive)
                .toList();
    }
  public List<Brand> getBrands() {
        return brandRepository.findAll();
  }




}
