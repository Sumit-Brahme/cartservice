package com.sumit.cartservice.controller;

import com.sumit.cartservice.dto.CartRequestDto;
import com.sumit.cartservice.dto.CartResponseDto;
import com.sumit.cartservice.dto.CartStatus;
import com.sumit.cartservice.dto.ResponseStatus;
import com.sumit.cartservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.sumit.cartservice.dto.StatusDto.CART_GET_ERROR;
import static com.sumit.cartservice.dto.StatusDto.CART_ITEM_ADD_ERROR;

@Controller
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/cart/get/{cartId}")
    @ResponseBody
    public ResponseEntity getCart(@PathVariable("cartId") Long cartId) {
        CartResponseDto cartResponseDto = new CartResponseDto();
        ResponseStatus responseStatus = new ResponseStatus();
        try {
            if (cartId != null) {
                cartResponseDto = cartService.getCart(cartId);
            }else{
                responseStatus.setStatus(CART_GET_ERROR.statusMessage);
                cartResponseDto.setResponseStatus(responseStatus);
            }
        } catch (Exception e) {
            responseStatus.setStatus(CART_GET_ERROR.statusMessage);
            cartResponseDto.setResponseStatus(responseStatus);
        }
        return new ResponseEntity(cartResponseDto, HttpStatus.OK);
    }

    @PostMapping("/cart/create")
    @ResponseBody
    public ResponseEntity createCart(@RequestBody CartRequestDto cartRequestDto) {
        CartResponseDto cartResponseDto = new CartResponseDto();
        ResponseStatus responseStatus = new ResponseStatus();
        try {
            if (cartRequestDto != null) {
                if (cartRequestDto.getOperation().equals(CartStatus.ADD.name()) || cartRequestDto.getOperation().equals(CartStatus.SET.name())) {
                    cartResponseDto = cartService.createCart(cartRequestDto);
                } else if (cartRequestDto.getOperation().equals(CartStatus.REMOVE.name())) {
                    cartResponseDto = cartService.removeFromCart(cartRequestDto);
                } else if (cartRequestDto.getOperation().equals(CartStatus.CLEAR.name())) {
                    cartResponseDto = cartService.clearCart(cartRequestDto);
                }
            }else{
                responseStatus.setStatus(CART_ITEM_ADD_ERROR.statusMessage);
            }
        } catch (Exception e) {
            responseStatus.setStatusMessage(e.getMessage());
            cartResponseDto.setResponseStatus(responseStatus);
        }
        return new ResponseEntity(cartResponseDto, HttpStatus.OK);
    }
}
