package com.sda.javaremote18.spring_boot.controllers.sub_categories;

import com.sda.javaremote18.spring_boot.models.sub_category.SubCategoryModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubCategoriesRepository extends CrudRepository<SubCategoryModel, Integer> {
    List<SubCategoryModel> findAll();
}
