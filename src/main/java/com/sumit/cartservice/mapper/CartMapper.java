package com.sumit.cartservice.mapper;

import com.sumit.cartservice.dto.CartRequestDto;
import com.sumit.cartservice.dto.CartResponseDto;
import com.sumit.cartservice.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {
    @Autowired
    ObjectMapperUtils objectMapperUtils;

    public Cart mapCartRequestDtoToCart(CartRequestDto cartRequestDto){
        return objectMapperUtils.map(cartRequestDto, Cart.class);
    }

    public CartResponseDto mapCartToCartResponseDto(Cart cart){
        return objectMapperUtils.map(cart,CartResponseDto.class);
    }
}
