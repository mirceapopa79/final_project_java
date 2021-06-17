package com.sda.javaremote18.spring_boot.controllers.orders;

import com.sda.javaremote18.spring_boot.models.order.OrderModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrdersRepository extends CrudRepository<OrderModel, Integer> {

    List<OrderModel> findAll();

//    @Modifying
//    @Query("delete from Order o where o.id = ?1")
//    void deleteById(Long orderId);
}
