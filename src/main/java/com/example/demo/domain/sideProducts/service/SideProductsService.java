package com.example.demo.domain.sideProducts.service;


import com.example.demo.domain.sideProducts.dto.request.SideProductRequest;
import com.example.demo.domain.sideProducts.dto.response.SideProductResponse;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import com.example.demo.domain.sideProducts.entity.SideProductImg;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SideProductsService {
    //등록
//    void register(
//            List<MultipartFile> sideProductImgList,
//            SideProductRequest sideProductRequest);
    void register(MultipartFile sideProductImgList, SideProductRequest sideProductRequest);


    //리스트
    List<SideProduct> list();

    //상세페이지(읽기)
    SideProductResponse read(Long sideProductId);

    //삭제
    void remove(Long sideProductId);

    //수정
    SideProductResponse modify(Long sideProductId, SideProductRequest sideProductRequest, MultipartFile sideProductImgList);
}

