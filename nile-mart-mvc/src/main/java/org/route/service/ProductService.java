package org.route.service;


import org.route.model.Category;
import org.route.model.Product;
import org.route.repository.BrandRepository;
import org.route.repository.CategoryRepository;
import org.route.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService ;
    private final BrandService brandService;


    public ProductService(ProductRepository productRepository, CategoryService categoryService, BrandService brandService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    public List<Product> getAvailableProducts() {
        List<Product> availableProducts = new ArrayList<>();
        for (Product p : productRepository.findAll()) {
            if (p.getInStock() > 0)
                availableProducts.add(p);
        }
        return availableProducts;
    }

    public void saveProduct(Product product, BindingResult result) {

        boolean isSubCategory =categoryService.getSubCategories().stream()
                .anyMatch(c->c.getSlug().equals(product.getCategory()));
        if (!isSubCategory) {
            result.rejectValue("category", "400", "Category must be a sub-category");
        }

        if (product.getBrandSlug() != null && !product.getBrandSlug().isEmpty()) {
            boolean isValidBrand = brandService.getActiveBrands().stream()
                    .anyMatch(b -> b.getSlug().equals(product.getBrandSlug()));

            if (!isValidBrand) {
                result.rejectValue("brandSlug", "400", "Brand does not exist or is inactive");
            }

        }
        if (result.hasErrors()) {
            return;
        }

        productRepository.save(product);
    }

    public boolean isProductExist(String sku) {
        return productRepository.findBySku(sku) != null;
    }
}
