package com.example.demo.domain.sideProducts.service;

import com.example.demo.domain.sideProducts.controller.request.SideProductRequest;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import com.example.demo.domain.sideProducts.repository.SideProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SideProductsServiceImpl implements SideProductsService {
    final private SideProductsRepository sideProductsRepository;


    @Override
    public SideProduct register(SideProductRequest sideProductRequest) {
        SideProduct sideProduct = new SideProduct();

        sideProduct.setTitle(sideProductRequest.getTitle());
        sideProduct.setContent(sideProductRequest.getContent());
        sideProduct.setPrice(sideProductRequest.getPrice());

        sideProductsRepository.save(sideProduct);

        return sideProduct;
    }
}
