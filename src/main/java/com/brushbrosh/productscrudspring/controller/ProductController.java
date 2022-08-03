package com.brushbrosh.productscrudspring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brushbrosh.productscrudspring.dto.Message;
import com.brushbrosh.productscrudspring.entity.Product;
import com.brushbrosh.productscrudspring.service.ProductService;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<List<Product>> list() {
        List<Product> list = productService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") int id) {
        if(!productService.existsById(id)){
            return new ResponseEntity(new Message("The product is not found"), HttpStatus.NOT_FOUND);
        }
        else{
            Product product = productService.getOne(id).get();
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        }
    }

}
