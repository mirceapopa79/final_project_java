package com.sda.javaremote18.spring_boot.controllers.orders;

import com.sda.javaremote18.spring_boot.controllers.items.ItemsRepository;
import com.sda.javaremote18.spring_boot.controllers.users.UsersRepository;
import com.sda.javaremote18.spring_boot.models.ServerResponse;
import com.sda.javaremote18.spring_boot.models.UserModel;
import com.sda.javaremote18.spring_boot.models.item.ItemModel;
import com.sda.javaremote18.spring_boot.models.order.OrderDto;
import com.sda.javaremote18.spring_boot.models.order.OrderModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrdersController {

    private UsersRepository usersRepository;
    private OrdersRepository ordersRepository;
    private ItemsRepository itemsRepository;

    @Autowired
    public OrdersController(UsersRepository usersRepository, OrdersRepository ordersRepository, ItemsRepository itemsRepository) {
        this.usersRepository = usersRepository;
        this.ordersRepository = ordersRepository;
        this.itemsRepository = itemsRepository;
    }

    @PostMapping("/orders/create/{userId}")
    public ServerResponse create(@PathVariable int userId, @RequestBody List<ItemModel> items) {
        System.out.println("post order method accessed");
        System.out.println(items.size());
        items.stream().forEach(item -> System.out.println(item.toString()));
        UserModel owner = this.usersRepository.findById(userId).orElse(null);

        if(owner == null) {
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "OwnerId invalid");
        }

        if(items.size() == 0) {
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(),"Order is empty");
        }

        OrderModel order = new OrderModel();
        order.setUser(owner);
        order.setItems(items);
        this.ordersRepository.save(order);

        return new ServerResponse(HttpStatus.OK.value(), "Order creat cu succes", "", order);
    }

    @PutMapping("/orders/{orderId}")
    public ServerResponse update(@PathVariable int orderId, @RequestBody OrderDto orderDto) {

        OrderModel orderFromDb = this.ordersRepository.findById(orderId).orElse(null);

        if(orderFromDb == null) {
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "Order-ul nu exista in baza de date");
        }

        UserModel owner = this.usersRepository.findById(orderDto.getUserId()).orElse(null);

        if(owner == null) {
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "OwnerId invalid");
        }

        List<ItemModel> orderItems;
        if(orderDto.getItems().size() == 0) {
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(),"Order is empty");
        } else {
            orderItems = new ArrayList<>(orderDto.getItems());
        }

        OrderModel order = new OrderModel();
        order.setUser(owner);
        order.setItems(orderItems);

        this.ordersRepository.save(order);

        return new ServerResponse(HttpStatus.OK.value(), "Order actualizat cu succes", "", order);
    }


    @DeleteMapping("/orders/{orderId}")
    public ServerResponse delete(@PathVariable int orderId) {
        if(orderId <= 0){
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "Order-ul nu exista in baza de date");
        }

        OrderModel orderFromDb = this.ordersRepository.findById(orderId).orElse(null);

        if(orderFromDb == null){
            return new ServerResponse(HttpStatus.BAD_REQUEST.value(), "Order-ul nu exista in baza de date");
        }

        this.ordersRepository.delete(orderFromDb);

        return new ServerResponse(HttpStatus.OK.value(), "Order-ul a fost anulat", null);
    }


    @GetMapping("/orders")
    public ServerResponse findAll() {
        List<OrderModel> orders = this.ordersRepository.findAll();

        return new ServerResponse(HttpStatus.OK.value(), "Lista comenzi", orders);
    }

}
