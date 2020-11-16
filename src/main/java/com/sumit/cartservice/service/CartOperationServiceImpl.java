package com.sumit.cartservice.service;

import com.sumit.cartservice.Utils.CartUtils;
import com.sumit.cartservice.dto.CartRequestDto;
import com.sumit.cartservice.dto.CartStatus;
import com.sumit.cartservice.entity.Cart;
import com.sumit.cartservice.entity.CartItem;
import com.sumit.cartservice.entity.Product;
import com.sumit.cartservice.repository.CartItemRepository;
import com.sumit.cartservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartOperationServiceImpl implements CartOperationService {

    @Autowired
    CartUtils cartUtils;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    public List<CartItem> addToExistingCart(CartRequestDto cartRequestDto, List<Product> verifiedProductList, List<CartItem> cartItemList) {
        List<CartItem> finalCartItemList = new ArrayList<>();
        List<Long> productIdsAlreadyPresentInCart = cartItemList.stream().map(CartItem::getProduct).map(Product::getId).collect(Collectors.toList());
        verifiedProductList.forEach(product -> {
            if (!CollectionUtils.isEmpty(productIdsAlreadyPresentInCart)) {
                if (productIdsAlreadyPresentInCart.contains(product.getId())) {
                    CartItem cartItemToBeUpdated = cartItemList.stream().filter(cartItem -> product.getId() == cartItem.getProduct().getId()).findFirst().orElse(null);
                    cartItemToBeUpdated = cartUtils.updateCartItem(cartRequestDto, cartItemToBeUpdated);
                    finalCartItemList.add(cartItemToBeUpdated);
                } else if (cartRequestDto.getOperation().equals(CartStatus.ADD.name())) {
                    CartItem cartItem = cartUtils.generateCartItem(product, cartRequestDto);
                    finalCartItemList.add(cartItem);
                }
            }
        });
        return finalCartItemList;
    }

    public CartItem addToNewCart(Cart cart, CartRequestDto cartRequestDto, List<Product> verifiedProductList) {
        CartItem cartItem = cartUtils.generateCartItem(verifiedProductList.get(0), cartRequestDto);
        cartItem.setCart(cart);
        return cartItem;
    }

    public void clearCart(Cart cart) throws Exception {
        try {
            cart.getCartItemList().forEach(cartItem -> {
                cartItemRepository.deleteById(cartItem.getId());
            });
            cartRepository.deleteById(cart.getId());
        } catch (Exception e) {
            throw new Exception("Error while clearing cart.");
        }
    }

    public void deleteCartItemById(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public Cart getCartById(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        return cart;
    }

    public Cart storeCart(Cart cart, List<CartItem> cartItemList) {
        cart.setCartItemList(cartItemList);
        Cart finalCart = cart;
        cartItemList.forEach(cartItem -> {
            cartItem.setCart(finalCart);
        });
        cart = cartRepository.save(cart);
        cartItemRepository.saveAll(cartItemList);
        return cart;
    }

    @Override
    public Cart storeCartItem(Cart cart, CartItem cartItem) {
        cartItem.setCart(cart);
        cart = cartRepository.save(cart);
        cartItemRepository.save(cartItem);
        return cart;
    }

}
