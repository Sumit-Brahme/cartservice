package com.sumit.cartservice.repository;

import com.sumit.cartservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
