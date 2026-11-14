package org.route.service;

import org.route.model.Category;
import org.route.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<Category> getSortedCategoriesHierarchy() {

        List<Category> res = new ArrayList<>();
        Map<String, List<Category>> grouped = categoryRepository.findAll().stream()
                .collect(Collectors.groupingBy(c ->
                        c.getParentSlug() == null ? "" : c.getParentSlug()));


        List<Category> topLevels = grouped.getOrDefault("", Collections.emptyList()).stream()
                .sorted(Comparator.comparingInt(Category::getDisplayOrder))
                .toList();

        topLevels.forEach(top -> {
                    res.add(top);

                    List<Category> subs = grouped.getOrDefault(top.getSlug(), Collections.emptyList()).stream()
                            .sorted(Comparator.comparingInt(Category::getDisplayOrder))
                            .toList();
                    res.addAll(subs);

                }
        );
        return res;
    }

    public List<Category> getTopLevelCategories() {
        return categoryRepository.findAll().stream()
                .filter(c -> c.getParentSlug() == null || c.getParentSlug().isEmpty())
                .sorted(Comparator.comparingInt(Category::getDisplayOrder))
                .toList();
    }

    public List<Category> getSubCategories() {
        return categoryRepository.findAll().stream()
                .filter(c -> c.getParentSlug() != null && !c.getParentSlug().isEmpty())
                .toList();
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public void saveCategory(Category category, BindingResult result) {
        if (categoryRepository.findBySlug(category.getSlug()) != null)
            result.rejectValue("slug", "400", "slug already exists");


        boolean nameExistsUnderParent = categoryRepository.findAll().stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(category.getName()) &&
                        ((c.getParentSlug() == null ? "" : c.getParentSlug())
                                .equals(category.getParentSlug() == null ? "" : category.getParentSlug())));


        if (nameExistsUnderParent)
            result.rejectValue("name", "400", "\"A category with this name already exists under this parent")
                    ;

        if (result.hasErrors())
            return;

        categoryRepository.save(category);
    }


}
