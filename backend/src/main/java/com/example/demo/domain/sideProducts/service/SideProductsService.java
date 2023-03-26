package com.example.demo.domain.sideProducts.service;


import com.example.demo.domain.sideProducts.dto.request.SideProductRequest;
import com.example.demo.domain.sideProducts.dto.response.SideProductResponse;
import com.example.demo.domain.sideProducts.entity.SideProduct;

import java.util.List;
public interface SideProductsService {
    public SideProduct register(SideProductRequest sideProductRequest);

    //리스트
    List<SideProduct> list();
    //삭제
    void remove(Long productId);

    //수정
    SideProductResponse modify(Long productId, SideProductRequest sideProductRequest);
}
