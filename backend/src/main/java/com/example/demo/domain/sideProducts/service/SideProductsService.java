package com.example.demo.domain.sideProducts.service;


import com.example.demo.domain.sideProducts.controller.request.SideProductRequest;
import com.example.demo.domain.sideProducts.entity.SideProduct;

public interface SideProductsService {
    public SideProduct register(SideProductRequest sideProductRequest);

}
