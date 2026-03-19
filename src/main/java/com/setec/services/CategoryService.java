package com.setec.services;

import com.setec.models.Category;
import com.setec.models.Product;
import com.setec.repositories.CategoryRepository;
import com.setec.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public List<Category> getCategoryAll() {
        return categoryRepository.findAll();
    }
}
