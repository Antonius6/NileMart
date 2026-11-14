package org.route.controller;

import jakarta.validation.Valid;
import org.route.model.Category;
import org.route.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/categories")
    public String getSortedCategories(Model model) {
        List<Category>categories=categoryService.getSortedCategoriesHierarchy();
        model.addAttribute("categories",categories);
        return "categories";
    }

    @GetMapping("/categories/new")
    public String showForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("toplevel",categoryService.getTopLevelCategories());
        return "categoryForm";
    }


    @PostMapping("/categories")
    public String save(@ModelAttribute @Valid Category category
            , BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
             model.addAttribute("toplevel",categoryService.getTopLevelCategories());
            return "categoryForm";
        }
        categoryService.saveCategory(category,bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("toplevel",categoryService.getTopLevelCategories());
            return "categoryForm";
        }
        return "redirect:/categories";
    }



















}
