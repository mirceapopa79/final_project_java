package com.sda.javaremote18.spring_boot.controllers.sub_categories;

import com.sda.javaremote18.spring_boot.controllers.items.ItemsRepository;
import com.sda.javaremote18.spring_boot.models.ServerResponse;
import com.sda.javaremote18.spring_boot.models.sub_category.SubCategoryDto;
import com.sda.javaremote18.spring_boot.models.sub_category.SubCategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubCategoriesController {
    private SubCategoriesRepository subCategoriesRepository;
    private ItemsRepository itemsRepository;

    @Autowired
    public SubCategoriesController(SubCategoriesRepository subCategoriesRepository, ItemsRepository itemsRepository){
        this.itemsRepository = itemsRepository;
        this.subCategoriesRepository = subCategoriesRepository;
    }

    @PostMapping("/sub-categories/create")
    public ServerResponse create(@RequestBody SubCategoryDto subCategoryDto) {
        if(!subCategoryDto.isValid()) {
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "Campurile sunt invalide!");
        }

        SubCategoryModel subCategory = new SubCategoryModel();
        subCategory.setTitle(subCategoryDto.getTitle());

        this.subCategoriesRepository.save(subCategory);

        return new ServerResponse(HttpStatus.OK.value(), "sub categorie creata cu succes", "", subCategory);
    }

    @PutMapping("/sub-categories/{subCategoryId}")
    public ServerResponse update(@PathVariable int subCategoryId, @RequestBody SubCategoryDto subCategoryDto) {
        if(!subCategoryDto.isValid()) {
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "Campurile sunt invalide!");
        }

        SubCategoryModel categoryFromDb = this.subCategoriesRepository.findById(subCategoryId).orElse(null);

        if(categoryFromDb == null) {
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "Item-ul nu exista in baza de date");
        }

        categoryFromDb.setTitle(subCategoryDto.getTitle());


        this.subCategoriesRepository.save(categoryFromDb);

        return new ServerResponse(HttpStatus.OK.value(), "sub categorie actualizata cu succes", "", categoryFromDb);
    }

    @DeleteMapping("/sub-categories/{subCategoryId}")
    public ServerResponse delete(@PathVariable int subCategoryId) {
        if(subCategoryId <= 0){
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "sub categoria nu exista in baza de date");
        }

        SubCategoryModel subCategoryFromDb = this.subCategoriesRepository.findById(subCategoryId).orElse(null);

        if(subCategoryFromDb == null){
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "sub categoria nu exista in baza de date");
        }

        this.subCategoriesRepository.delete(subCategoryFromDb);

        return new ServerResponse(HttpStatus.OK.value(), "sub categoria a fost stearsa cu succes", null);
    }

    @GetMapping("/sub-categories")
    public ServerResponse findAll() {
        List<SubCategoryModel> subCategoryModels = this.subCategoriesRepository.findAll();

        return new ServerResponse(HttpStatus.OK.value(), "Lista sub categorii", subCategoryModels);
    }
}