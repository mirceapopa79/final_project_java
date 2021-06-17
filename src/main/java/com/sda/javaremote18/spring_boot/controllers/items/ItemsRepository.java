package com.sda.javaremote18.spring_boot.controllers.items;

import com.sda.javaremote18.spring_boot.models.item.ItemModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemsRepository extends CrudRepository<ItemModel, Integer> {
    List<ItemModel> findAllByDeletedFalse();
    List<ItemModel> findAll();
}
