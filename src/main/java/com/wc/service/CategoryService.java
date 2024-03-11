package com.wc.service;

import com.wc.pojo.Category;

import java.util.List;

public interface CategoryService {
    public void add(Category category);
    public List<Category> list();
    public Category findById(Integer id);
}
