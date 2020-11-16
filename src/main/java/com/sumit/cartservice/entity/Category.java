package com.sumit.cartservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Product> productList;

    @ManyToOne
    @JoinColumn(name = "parentCategoryId" , referencedColumnName = "id")
    private Category parentCategory;

    @Transient
    private Long parent_category_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }


    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Long getParent_category_id() {
        return parent_category_id;
    }

    public void setParent_category_id(Long parent_category_id) {
        this.parent_category_id = parent_category_id;
    }
}
