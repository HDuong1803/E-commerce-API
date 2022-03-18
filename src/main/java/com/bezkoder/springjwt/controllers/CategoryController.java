package com.bezkoder.springjwt.controllers;



import com.bezkoder.springjwt.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
	CategoryService categoryService;
	
    
}
