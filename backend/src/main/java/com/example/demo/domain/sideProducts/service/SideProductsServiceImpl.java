package com.example.demo.domain.sideProducts.service;

import com.example.demo.domain.sideProducts.dto.request.SideProductRequest;
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

    @Override
    public SideProduct read(Long productId) {
        Optional<SideProduct> maybeSideProduct = sideProductsRepository.findByProductId(productId);

        if(maybeSideProduct.isEmpty()){
            log.info("없는데?");
            return null;
        }
        return maybeSideProduct.get();
    // 삭제
    @Override
    public void remove(Long productId) {
        sideProductsRepository.deleteById(productId);
    }
    }
}
