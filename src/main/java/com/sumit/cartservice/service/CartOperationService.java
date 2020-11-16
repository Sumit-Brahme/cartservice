package com.sumit.cartservice.service;

import com.sumit.cartservice.dto.CartRequestDto;
import com.sumit.cartservice.entity.Cart;
import com.sumit.cartservice.entity.CartItem;
import com.sumit.cartservice.entity.Product;

import java.util.List;

public interface CartOperationService {
    List<CartItem> addToExistingCart(CartRequestDto cartRequestDto, List<Product> verifiedProductList, List<CartItem> cartItemList );
    CartItem addToNewCart(Cart cart, CartRequestDto cartRequestDto, List<Product> verifiedProductList );
    void clearCart(Cart cart) throws Exception;
    Cart storeCart(Cart cart , List<CartItem> cartItemList);
    Cart getCartById(Long id);
    void deleteCartItemById(Long cartItemId);
    Cart storeCartItem(Cart cart,CartItem cartItemList);
}
