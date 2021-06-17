package com.sda.javaremote18.spring_boot.models.order;

import com.sda.javaremote18.spring_boot.models.item.ItemModel;

import java.util.List;

public class OrderDto {
    private Integer id;
    private Integer userId;
    private List<ItemModel> items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<ItemModel> getItems() {
        return items;
    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
    }
}
