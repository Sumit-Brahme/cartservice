package com.sumit.cartservice.service;

import com.sumit.cartservice.dto.CartRequestDto;
import com.sumit.cartservice.dto.CartResponseDto;

public interface CartService {
    CartResponseDto getCart(Long cartId);

    CartResponseDto createCart(CartRequestDto cartRequestDto) throws Exception;

    CartResponseDto removeFromCart(CartRequestDto cartRequestDto) throws Exception;

    CartResponseDto clearCart(CartRequestDto cartRequestDto) throws Exception;
}

