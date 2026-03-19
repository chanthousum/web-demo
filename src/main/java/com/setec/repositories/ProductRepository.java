package com.setec.repositories;

import com.setec.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findProductById(int id);
    @Query(value = "SELECT * FROM product WHERE product_name like CONCAT('%',:search,'%')",nativeQuery = true)
    List<Product> findProductByName(String search);
}
