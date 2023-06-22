package com.l2i_e_commerce.dao;

import com.l2i_e_commerce.dto.OrderLineDto;
import com.l2i_e_commerce.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    @Query("SELECT b.imageUrl, b.title, o.unitPriceHT, t.tvaRate, o.unitPriceTTC, o.orderedQuantity, o.unitPriceTTC * o.orderedQuantity FROM OrderLine o, Book b, TVA t WHERE o.book.id = b.id AND b.tva.id = t.id AND o.order.id = :orderId")
    List<Object[]> findByOrderId(@Param("orderId") Long orderId);
}
