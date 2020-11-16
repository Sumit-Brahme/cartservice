package com.sumit.cartservice.Utils;

import com.sumit.cartservice.dto.CartRequestDto;
import com.sumit.cartservice.dto.CartStatus;
import com.sumit.cartservice.entity.CartItem;
import com.sumit.cartservice.entity.Product;
import com.sumit.cartservice.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartUtils {

    @Autowired
    CartItemRepository cartItemRepository;

    public CartItem generateCartItem(Product product,CartRequestDto cartRequestDto){
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(cartRequestDto.getProductRequestDto().getQuantity());
        cartItem.setProduct(product);
        return cartItem;
    }

    public CartItem updateCartItem(CartRequestDto cartRequestDto,CartItem cartItem){
        if(cartRequestDto.getOperation().equals(CartStatus.ADD.name())) {
            cartItem.setQuantity(cartItem.getQuantity()+cartRequestDto.getProductRequestDto().getQuantity());
        }
        else if(cartRequestDto.getOperation().equals(CartStatus.SET.name())){
            cartItem.setQuantity(cartRequestDto.getProductRequestDto().getQuantity());
        }
        return cartItem;
    }

}
