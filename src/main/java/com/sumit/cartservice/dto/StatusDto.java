package com.sumit.cartservice.dto;

public enum StatusDto {
    CART_ITEM_ADD_SUCCESS("Item Added In Cart."),
    CART_ITEM_ADD_ERROR("Error Occurred While Adding Items In Cart."),
    CART_ITEM_REMOVE_SUCCESS("Item Removed From Cart."),
    CART_ITEM_REMOVE_ERROR("Error Occurred While Removing Items From Cart."),
    CART_CLEAR_SUCCESS("Cart Cleared."),
    CART_CLEAR_ERROR("Error Occurred While Clearing Cart."),
    CART_GET_SUCCESS("Cart Fetched Successfully."),
    CART_GET_ERROR("Error Occurred While Fetching Cart."),
    PRODUCT_VERIFY_SUCCESS("Products Verification Successful.");

    public final String statusMessage;

    StatusDto(String statusMessage) {
        this.statusMessage=statusMessage;
    }
}
