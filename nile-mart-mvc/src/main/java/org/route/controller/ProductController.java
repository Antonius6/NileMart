package org.route.controller;


import jakarta.validation.Valid;
import org.route.model.Brand;
import org.route.model.Category;
import org.route.model.Product;
import org.route.service.BrandService;
import org.route.service.CategoryService;
import org.route.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    public ProductController(ProductService productService, CategoryService categoryService, BrandService brandService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping("/products")
    public String getProducts(Model model) {
        List<Product> products =  productService.getAvailableProducts();
        model.addAttribute("availableProducts", products);
        Map<String, String> categoryNames = categoryService.findAllCategories().stream()
                .collect(Collectors.toMap(Category::getSlug, Category::getName, (existing, replacement) -> existing));

        Map<String, String> brandNames = brandService.getBrands().stream()
                .collect(Collectors.toMap(Brand::getSlug, Brand::getName, (existing, replacement) -> existing));

        model.addAttribute("categoryNames", categoryNames);
        model.addAttribute("brandNames", brandNames);
        return "products";
    }

    @GetMapping("/products/new")
    public String showForm(Model model) {
        model.addAttribute("product", new Product());

            model.addAttribute("subCategories", categoryService.getSubCategories());
            model.addAttribute("activeBrands", brandService.getActiveBrands());

        return "productForm";
    }

    @PostMapping("/products")
    public String addProduct(@ModelAttribute("product") @Valid Product product,
                             BindingResult result, Model model) {
        if (productService.isProductExist(product.getSku())) {
            result.rejectValue("sku", "400", "sku is already exist");
        }
        if (result.hasErrors()) {
            model.addAttribute("subCategories", categoryService.getSubCategories());
            model.addAttribute("activeBrands", brandService.getActiveBrands());
            return "productForm";
        }
        productService.saveProduct(product,result);
        return "redirect:/products";
    }
}
