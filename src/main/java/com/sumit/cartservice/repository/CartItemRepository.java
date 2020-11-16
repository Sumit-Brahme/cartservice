package com.sumit.cartservice.repository;

import com.sumit.cartservice.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
