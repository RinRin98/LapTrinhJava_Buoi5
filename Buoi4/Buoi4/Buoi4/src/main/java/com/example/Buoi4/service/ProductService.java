package com.example.Buoi4.service;


import com.example.Buoi4.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.*;

@Service
public class ProductService {
    private  List<Product> list;
    public  ProductService(){
        list= new ArrayList<>();
        list.add(new Product(1,"Lập trình web Spring MVC","Ánh Nguyễn",10));
        list.add(new Product(2,"Lập trình web Spring","Ánh Nguyễn",9));
        list.add(new Product(3,"Lập trình web","Ánh Nguyễn",8));
        list.add(new Product(4,"Lập trình","Ánh Nguyễn",7));
    }
    public List<Product> getAllBooks(){
        return list;
    }
    public void add(Product product){
        list.add(product);

    }
    public Product getId(int id){
         return list.stream().filter(p->p.getId()==id).findFirst().orElse(null);

    }
    public void update(int id, Product editProduct){
        Product findProduct=getId(id);
        if(findProduct!=null){
            list.remove(findProduct);
            list.add(editProduct);
        }
    }
    public void delete(int  id){
        Product findProduct=getId(id);
        if(findProduct!=null){
            list.remove(findProduct);
        }
    }
    public List<Product> search(String name){
        if(name.isBlank()) return  list;
        return list.stream().filter(p-> p.getName().contains(name)).toList();
    }

}
