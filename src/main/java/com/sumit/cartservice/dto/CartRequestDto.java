package com.sumit.cartservice.dto;

import java.util.List;

public class CartRequestDto {
    private Long id;
    private String operation;
    private Long cartItemId;
    private ProductRequestDto productRequestDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public ProductRequestDto getProductRequestDto() {
        return productRequestDto;
    }

    public void setProductRequestDto(ProductRequestDto productRequestDto) {
        this.productRequestDto = productRequestDto;
    }
}
