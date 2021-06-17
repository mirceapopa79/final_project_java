package com.sda.javaremote18.spring_boot.models.category;

import com.sda.javaremote18.spring_boot.utils.Utils;

public class CategoryDto {
    private Integer id;
    private String title;

    public boolean isValid() {
//        int counter = 0;
//        if(!Utils.isValidString(this.title)){
//            counter++;
//        }
//        return counter == 0;
    return Utils.isValidString(this.title);
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
}
