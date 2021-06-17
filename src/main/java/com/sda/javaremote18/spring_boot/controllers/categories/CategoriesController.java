package com.sda.javaremote18.spring_boot.controllers.categories;

import com.sda.javaremote18.spring_boot.controllers.items.ItemsRepository;
import com.sda.javaremote18.spring_boot.models.ServerResponse;
import com.sda.javaremote18.spring_boot.models.category.CategoryModel;
import com.sda.javaremote18.spring_boot.models.category.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoriesController {
    private CategoriesRepository categoriesRepository;
    private ItemsRepository itemsRepository;

    @Autowired
    public CategoriesController(CategoriesRepository categoriesRepository, ItemsRepository itemsRepository){
        this.itemsRepository = itemsRepository;
        this.categoriesRepository = categoriesRepository;
    }

    @PostMapping("/categories/create")
    public ServerResponse create(@RequestBody CategoryDto categoryDto) {
        if(!categoryDto.isValid()) {
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "Campurile sunt invalide!");
        }

        CategoryModel category = new CategoryModel();
        category.setTitle(categoryDto.getTitle());

        this.categoriesRepository.save(category);

        return new ServerResponse(HttpStatus.OK.value(), "categorie creata cu succes", "", category);
    }

    @PutMapping("/categories/{categoryId}")
    public ServerResponse update(@PathVariable int categoryId, @RequestBody CategoryDto categoryDto) {
        if(!categoryDto.isValid()) {
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "Campurile sunt invalide!");
        }

        CategoryModel categoryFromDb = this.categoriesRepository.findById(categoryId).orElse(null);

        if(categoryFromDb == null) {
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "Item-ul nu exista in baza de date");
        }

        categoryFromDb.setTitle(categoryDto.getTitle());


        this.categoriesRepository.save(categoryFromDb);

        return new ServerResponse(HttpStatus.OK.value(), "categorie actualizata cu succes", "", categoryFromDb);
    }

    @DeleteMapping("/categories/{categoryId}")
    public ServerResponse delete(@PathVariable int categoryId) {
        if(categoryId <= 0){
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "categoria nu exista in baza de date");
        }

        CategoryModel categoryFromDb = this.categoriesRepository.findById(categoryId).orElse(null);

        if(categoryFromDb == null){
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "categoria nu exista in baza de date");
        }

        this.categoriesRepository.delete(categoryFromDb);

        return new ServerResponse(HttpStatus.OK.value(), "categoria a fost stearsa cu succes", null);
    }

    @GetMapping("/categories")
    public ServerResponse findAll() {
        List<CategoryModel> categories = this.categoriesRepository.findAll();

        return new ServerResponse(HttpStatus.OK.value(), "Lista categorii", categories);
    }
}
