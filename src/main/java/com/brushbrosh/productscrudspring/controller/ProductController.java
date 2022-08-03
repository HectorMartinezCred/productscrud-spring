package com.brushbrosh.productscrudspring.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brushbrosh.productscrudspring.dto.Message;
import com.brushbrosh.productscrudspring.dto.ProductDto;
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
            return new ResponseEntity(new Message("Product is not found"), HttpStatus.NOT_FOUND);
        }
        Product product = productService.getOne(id).get();
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @GetMapping("/detailname/{name}")
    public ResponseEntity<Product> getByName(@PathVariable("name") String name) {
        if(!productService.existsByName(name)){
            return new ResponseEntity(new Message("Product is not found"), HttpStatus.NOT_FOUND);
        }
        else{
            Product product = productService.getByName(name).get();
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductDto productDto) {
        if(StringUtils.isBlank(productDto.getName())){
            return new ResponseEntity(new Message("Product name is mandatory."), HttpStatus.BAD_REQUEST);
        }
        if(productDto.getPrice()<0){
            return new ResponseEntity(new Message("Price must be greater than 0."), HttpStatus.BAD_REQUEST);
        }
        if(productService.existsByName(productDto.getName())){
            return new ResponseEntity(new Message("Product name already exists."), HttpStatus.BAD_REQUEST);
        }
        Product product = new Product(productDto.getName(), productDto.getPrice());
        productService.save(product);
        return new ResponseEntity(new Message("Product has created"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody ProductDto productDto) {
        if(!productService.existsById(id)){
            return new ResponseEntity(new Message("Product is not found"), HttpStatus.NOT_FOUND);
        }
        if(productService.existsByName(productDto.getName()) && productService.getByName(productDto.getName()).get().getId() != id){
            return new ResponseEntity(new Message("Product name already exists."), HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(productDto.getName())){
            return new ResponseEntity(new Message("Product name is mandatory."), HttpStatus.BAD_REQUEST);
        }
        if(productDto.getPrice()<0){
            return new ResponseEntity(new Message("Price must be greater than 0."), HttpStatus.BAD_REQUEST);
        }
        Product product = productService.getOne(id).get();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        productService.save(product);
        return new ResponseEntity(new Message("Product has update"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        if(!productService.existsById(id)){
            return new ResponseEntity(new Message("Product is not found"), HttpStatus.NOT_FOUND);
        }
        productService.delete(id);
        return new ResponseEntity(new Message("Product has delete"), HttpStatus.OK);
    }
}
