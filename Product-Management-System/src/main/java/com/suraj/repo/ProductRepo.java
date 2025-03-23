package com.suraj.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suraj.model.Product;

public interface ProductRepo extends JpaRepository<Product, Integer>{

}
