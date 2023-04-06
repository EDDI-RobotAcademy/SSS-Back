package com.example.demo.domain.products.service;

import com.example.demo.domain.products.entity.Review;
import com.example.demo.domain.products.service.request.ReviewRegisterRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {

    void register(List<MultipartFile> reviewImgList, ReviewRegisterRequest request);

}
