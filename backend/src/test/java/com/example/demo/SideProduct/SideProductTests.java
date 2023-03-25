package com.example.demo.SideProduct;

import com.example.demo.domain.sideProducts.controller.request.SideProductRequest;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import com.example.demo.domain.sideProducts.service.SideProductsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SideProductTests {

    @Autowired
    private SideProductsService sideProductsService;

    @Test
    public void 사이드_메뉴_저장_테스트(){
        SideProductRequest sideProductRequest =
                new SideProductRequest("칼로리0",2000L, "제로콜라");

        System.out.println("sideProductService "+sideProductsService);
        sideProductsService.register(sideProductRequest);
    }

}
