package com.setec.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @Column(name = "product_name",unique=true,length =25)
    private String name;
    private long barcode;
    private double sellprice;
    private int unitinstock;
    @Column(name = "photo",nullable=true)
    private String photo;
    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBarcode() {
        return barcode;
    }

    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }

    public double getSellprice() {
        return sellprice;
    }

    public void setSellprice(double sellprice) {
        this.sellprice = sellprice;
    }

    public int getUnitinstock() {
        return unitinstock;
    }

    public void setUnitinstock(int unitinstock) {
        this.unitinstock = unitinstock;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }




}
