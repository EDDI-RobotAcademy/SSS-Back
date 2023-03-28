package com.example.demo.domain.sideProducts.service;

import com.example.demo.domain.sideProducts.dto.request.SideProductRequest;
import com.example.demo.domain.sideProducts.dto.response.SideProductResponse;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import com.example.demo.domain.sideProducts.repository.SideProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    // 리스트
    @Override
    public List<SideProduct> list() {
        return sideProductsRepository.findAll(Sort.by(Sort.Direction.DESC,"productId"));
    }

    // 상세페이지(읽기)
    @Override
    public SideProductResponse read(Long productId) {
        Optional<SideProduct> maybeSideProduct = sideProductsRepository.findByProductId(productId);

        if(maybeSideProduct.isEmpty()){
            log.info("없는데?");
            return null;
        }
        SideProduct sideProduct = maybeSideProduct.get();

        SideProductResponse sideProductResponse = new SideProductResponse(
                sideProduct.getProductId(),
                sideProduct.getContent(),
                sideProduct.getPrice(),
                sideProduct.getTitle()
        );
        return sideProductResponse;
    }

    // 삭제
    @Override
    public void remove(Long productId) {
        sideProductsRepository.deleteById(productId);
    }

    // 수정
    @Override
    public SideProductResponse modify(Long productId, SideProductRequest sideProductRequest) {
        Optional<SideProduct> maybeSideProduct = sideProductsRepository.findByProductId(productId);

        if(maybeSideProduct.isEmpty()){
            return null;
        }

        SideProduct sideProduct = maybeSideProduct.get();

        sideProduct.setTitle(sideProductRequest.getTitle());
        sideProduct.setContent(sideProductRequest.getContent());
        sideProduct.setPrice(sideProductRequest.getPrice());

        sideProductsRepository.save(sideProduct);

        SideProductResponse sideProductResponse = new SideProductResponse(
                sideProduct.getProductId(),
                sideProductRequest.getContent(),
                sideProductRequest.getPrice(),
                sideProductRequest.getTitle()
        );

        return sideProductResponse;
    }
}
