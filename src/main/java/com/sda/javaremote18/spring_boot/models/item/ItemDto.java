package com.sda.javaremote18.spring_boot.models.item;

import com.sda.javaremote18.spring_boot.utils.Utils;

import java.util.Date;

// Dto = Data Transfer Object
public class ItemDto {
    private Integer id;
    private String title;
    private String description;
    private String imageUrl;
    private Double price;
    private Integer ownerId;
    private Integer categoryId;
    private Integer subCategoryId;

    public boolean isValid() {
        int counter = 0;
        if(!Utils.isValidString(this.title) && !Utils.isValidString(this.description) && !Utils.isValidString(this.imageUrl)) {//daca title nu este valid si descrierea nu este valida
            counter++;
        }
        if(Double.isNaN(this.price)) {  //isNaN verifica daca parametrul este numar
            counter++;
        }
        if(ownerId < 0) {
            counter++;
        }
        if(categoryId < 0){
            counter++;
        }
        if(subCategoryId < 0){
            counter++;
        }
        return counter == 0;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }
}
