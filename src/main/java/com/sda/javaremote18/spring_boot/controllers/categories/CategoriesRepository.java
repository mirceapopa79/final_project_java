package com.sda.javaremote18.spring_boot.controllers.categories;

import com.sda.javaremote18.spring_boot.models.category.CategoryModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoriesRepository extends CrudRepository<CategoryModel, Integer> {
    List<CategoryModel> findAll();
}
