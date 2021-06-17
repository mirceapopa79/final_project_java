package com.sda.javaremote18.spring_boot.models.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sda.javaremote18.spring_boot.models.UserModel;
import com.sda.javaremote18.spring_boot.models.category.CategoryModel;
import com.sda.javaremote18.spring_boot.models.order.OrderModel;
import com.sda.javaremote18.spring_boot.models.sub_category.SubCategoryModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_items")
public class ItemModel {
    /**
     *  ID -> INTEGER/LONG
     *  TITLE -> STRING
     *  OWNER -> USERMODEL relation ManyToOne
     *  PRICE -> DOUBLE
     *  DESCRIPTION -> STRING
     *  DATE -> DATE
     *  DELETED -> BOOLEAN
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private String description;
    private String imageUrl;
    private Double price;
    private Date date;
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_category")
    private CategoryModel category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_sub_category")
    private SubCategoryModel subCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_item")
    private UserModel owner;

    @JsonIgnore
    @ManyToMany(mappedBy = "items", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @Fetch(value= FetchMode.SELECT)
    private List<OrderModel> orders;

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public UserModel getOwner() {
        return owner;
    }

    public void setOwner(UserModel owner) {
        this.owner = owner;
    }

    public void setSubCategory(SubCategoryModel subCategory) {
        this.subCategory = subCategory;
    }

    public SubCategoryModel getSubCategory() {
        return subCategory;
    }

    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "ItemModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", date=" + date +
                ", deleted=" + deleted +
                ", category=" + category +
                ", subCategory=" + subCategory +
                ", owner=" + owner +
                '}';
    }
}
