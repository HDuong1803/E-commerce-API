package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.entity.Category;
import com.bezkoder.springjwt.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category getById(Integer id) {

        return categoryRepository.findById(id).get();

    }

}
