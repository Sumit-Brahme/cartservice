package com.sumit.cartservice.service;

import com.sumit.cartservice.dto.ResponseStatus;
import com.sumit.cartservice.relay.CartRelay;
import com.sumit.cartservice.Utils.CartUtils;
import com.sumit.cartservice.dto.CartRequestDto;
import com.sumit.cartservice.dto.CartResponseDto;
import com.sumit.cartservice.entity.Cart;
import com.sumit.cartservice.entity.CartItem;
import com.sumit.cartservice.entity.Product;
import com.sumit.cartservice.mapper.CartMapper;
import com.sumit.cartservice.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

import static com.sumit.cartservice.dto.StatusDto.*;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    CartUtils cartUtils;

    @Autowired
    CartRelay cartRelay;

    @Autowired
    CartOperationService cartOperationService;

    @Override
    public CartResponseDto getCart(Long cartId) {
        ResponseStatus responseStatus=new ResponseStatus();
        CartResponseDto cartResponseDto = new CartResponseDto();
        Cart cart = cartOperationService.getCartById(cartId);
        if (cart != null) {
            cartResponseDto = cartMapper.mapCartToCartResponseDto(cart);
            responseStatus.setStatus(CART_GET_SUCCESS.statusMessage);
            cartResponseDto.setResponseStatus(responseStatus);
        }else{
            responseStatus.setStatus(CART_GET_ERROR.statusMessage);
            responseStatus.setStatusMessage("Invalid Cart Id.");
            cartResponseDto.setResponseStatus(responseStatus);
        }
        return cartResponseDto;
    }

    @Override
    public CartResponseDto createCart(CartRequestDto cartRequestDto) throws Exception {
        ResponseStatus responseStatus = new ResponseStatus();
        CartResponseDto cartResponseDto = new CartResponseDto();
        Cart cart = null;
        List<CartItem> cartItemList = null;
        List<Product> verifiedProductList = cartRelay.verifyProducts(Arrays.asList(cartRequestDto.getProductRequestDto()));
        if (!CollectionUtils.isEmpty(verifiedProductList)) {
            if (cartRequestDto.getId() != null) {
                cart = cartOperationService.getCartById(cartRequestDto.getId());
                if (cart != null) {
                    cartItemList = cartOperationService.addToExistingCart(cartRequestDto, verifiedProductList, cart.getCartItemList());
                    if (cartItemList.size() != 0) {
                        cart = cartOperationService.storeCartItem(cart, cartItemList.get(cartItemList.size() - 1));
                        responseStatus.setStatus(CART_ITEM_ADD_SUCCESS.statusMessage);
                    } else {
                        responseStatus.setStatus(CART_ITEM_ADD_ERROR.statusMessage);
                        responseStatus.setStatusMessage("Product Not Found in Cart.");
                    }
                } else {
                    responseStatus.setStatus(CART_ITEM_ADD_ERROR.statusMessage);
                    responseStatus.setStatusMessage("Invalid cart id.");
                }
            } else {
                cart = cartMapper.mapCartRequestDtoToCart(cartRequestDto);
                CartItem cartItem = cartOperationService.addToNewCart(cart, cartRequestDto, verifiedProductList);
                cart.setCartItemList(Arrays.asList(cartItem));
                cart = cartOperationService.storeCartItem(cart, cartItem);
                responseStatus.setStatus(CART_ITEM_ADD_SUCCESS.statusMessage);
            }
        } else {
            responseStatus.setStatus(CART_ITEM_ADD_ERROR.statusMessage);
            responseStatus.setStatusMessage("Products verification failed.");
        }
        if (cart != null) {
            cartResponseDto = cartMapper.mapCartToCartResponseDto(cart);
        }
        cartResponseDto.setResponseStatus(responseStatus);
        return cartResponseDto;
    }

    @Override
    public CartResponseDto removeFromCart(CartRequestDto cartRequestDto) throws Exception {
        ResponseStatus responseStatus = new ResponseStatus();
        CartResponseDto cartResponseDto = new CartResponseDto();
        Cart cart = cartOperationService.getCartById(cartRequestDto.getId());
        if (cart != null) {
            if (cartRequestDto.getCartItemId() != null) {
                List<CartItem> cartItemList = cart.getCartItemList();
                if (cartItemList.size() == 1) {
                    if (cartItemList.get(0).getId().equals(cartRequestDto.getCartItemId())) {
                        cartOperationService.clearCart(cart);
                        cartItemList.removeIf(cartItem -> cartItem.getId().equals(cartRequestDto.getCartItemId()));
                        responseStatus.setStatus(CART_ITEM_REMOVE_SUCCESS.statusMessage);
                    } else {
                        responseStatus.setStatus(CART_ITEM_REMOVE_ERROR.statusMessage);
                        responseStatus.setStatusMessage("Invalid cartItem id.");
                    }
                } else {
                    cartOperationService.deleteCartItemById(cartRequestDto.getCartItemId());
                    cartItemList.removeIf(cartItem -> cartItem.getId().equals(cartRequestDto.getCartItemId()));
                    responseStatus.setStatus(CART_ITEM_REMOVE_SUCCESS.statusMessage);
                }
            } else {
                responseStatus.setStatus(CART_ITEM_REMOVE_ERROR.statusMessage);
                responseStatus.setStatusMessage("Invalid cartItem id.");
            }
        } else {
            responseStatus.setStatus(CART_ITEM_REMOVE_ERROR.statusMessage);
            responseStatus.setStatusMessage("Invalid cart id.");
        }
        if (cart != null) {
            cartResponseDto = cartMapper.mapCartToCartResponseDto(cart);
        }
        cartResponseDto.setResponseStatus(responseStatus);
        return cartResponseDto;
    }

    @Override
    public CartResponseDto clearCart(CartRequestDto cartRequestDto) throws Exception {
        ResponseStatus responseStatus = new ResponseStatus();
        Cart cart = cartOperationService.getCartById(cartRequestDto.getId());
        CartResponseDto cartResponseDto = new CartResponseDto();
        try {
            if (cart != null) {
                cartOperationService.clearCart(cart);
                responseStatus.setStatus(CART_CLEAR_SUCCESS.statusMessage);
            } else {
                responseStatus.setStatus(CART_CLEAR_ERROR.statusMessage);
                responseStatus.setStatusMessage("Invalid cart id.");
            }
        } catch (Exception e) {
            responseStatus.setStatus(CART_CLEAR_ERROR.statusMessage);
            responseStatus.setStatusMessage("Invalid cart id.");
        }
        cartResponseDto.setResponseStatus(responseStatus);
        return cartResponseDto;
    }
}
