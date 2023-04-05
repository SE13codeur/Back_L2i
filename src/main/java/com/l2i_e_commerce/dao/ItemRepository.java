package com.l2i_e_commerce.dao;

import com.l2i_e_commerce.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    
    // Requête personnalisée pour récupérer les articles en fonction de la pertinence (par exemple, les articles en stock)
    @Query("SELECT i FROM Item i WHERE i.inStock = true")
    List<Item> findItemsInStock();

    // Requête personnalisée pour récupérer les articles les plus vendus
    @Query("SELECT i FROM Item i ORDER BY i.totalSales DESC")
    List<Item> findMostSoldItems();
}

