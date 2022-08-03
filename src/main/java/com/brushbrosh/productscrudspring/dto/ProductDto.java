package com.brushbrosh.productscrudspring.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ProductDto {
    
    @NotBlank
    private String name;

    @Min(0)
    private float price;

    public ProductDto() {
    }

    public ProductDto(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    

}
