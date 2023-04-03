package com.l2i_e_commerce.dao;

import com.l2i_e_commerce.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    
	// Requête personnalisée pour rechercher des articles par titre ou auteur avec des noms incomplets
    @Query("SELECT i FROM Item i WHERE LOWER(i.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(i.author.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Item> searchByTitleOrAuthor(@Param("query") String query);
    
    // Requête personnalisée pour récupérer les articles en fonction de la pertinence (par exemple, les articles en stock)
    @Query("SELECT i FROM Item i WHERE i.stock.quantityInStock > 0")
    List<Item> findItemsInStock();

    // Requête personnalisée pour récupérer les articles les plus vendus
    @Query("SELECT i FROM Item i ORDER BY i.totalSales DESC")
    List<Item> findMostSoldItems();
}

