package com.sumit.cartservice.relay;

import com.sumit.cartservice.dto.ProductRelayResponse;
import com.sumit.cartservice.dto.ProductRequestDto;
import com.sumit.cartservice.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static com.sumit.cartservice.dto.StatusDto.PRODUCT_VERIFY_SUCCESS;

@Component
public class CartRelay {

    public List<Product> verifyProducts(List<ProductRequestDto> productRequestDtoList) throws Exception {
        String url="http://localhost:8090/product/verify";
        RestTemplate restTemplate=new RestTemplate();
        List<Product> verifiedProductList=null;
        ResponseEntity<ProductRelayResponse> productListResponseEntity = restTemplate.postForEntity(url, productRequestDtoList, ProductRelayResponse.class);
        if(productListResponseEntity.getStatusCode()== HttpStatus.CREATED) {
            ProductRelayResponse productRelayResponse = productListResponseEntity.getBody();
            if (productRelayResponse.getResponseStatus().getStatus().equals(PRODUCT_VERIFY_SUCCESS.statusMessage)) {
                verifiedProductList = productRelayResponse.getProductList();
            }
        }
        return verifiedProductList;
    }
}
