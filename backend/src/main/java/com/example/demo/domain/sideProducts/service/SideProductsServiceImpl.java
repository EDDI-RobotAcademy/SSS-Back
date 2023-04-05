package com.example.demo.domain.sideProducts.service;


import com.example.demo.domain.sideProducts.dto.request.SideProductRequest;
import com.example.demo.domain.sideProducts.dto.response.SideProductResponse;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import com.example.demo.domain.sideProducts.entity.SideProductImg;
import com.example.demo.domain.sideProducts.repository.SideProductsImgRepository;
import com.example.demo.domain.sideProducts.repository.SideProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SideProductsServiceImpl implements SideProductsService {
    final private SideProductsRepository sideProductsRepository;
    final private SideProductsImgRepository sideProductsImgRepository;

    //등록
//    @Override
//    public SideProductResponse register(SideProductRequest sideProductRequest) {
//        SideProduct sideProduct = new SideProduct();
//
//        sideProduct.setTitle(sideProductRequest.getTitle());
//        sideProduct.setContent(sideProductRequest.getContent());
//        sideProduct.setPrice(sideProductRequest.getPrice());
//
//        sideProductsRepository.save(sideProduct);
//
//        SideProductResponse sideProductResponse = new SideProductResponse(
//                sideProduct.getSideProductId(),
//                sideProductRequest.getContent(),
//                sideProductRequest.getPrice(),
//                sideProductRequest.getTitle()
//        );
//
//        return sideProductResponse;
//    }


//    @Override
//    public void register(List<MultipartFile> sideProductImgList, SideProductRequest sideProductRequest) {
//    }

    @Override
    @Transactional
    public void register(MultipartFile sideProductImgList, SideProductRequest sideProductRequest) {
        SideProduct sideProduct = new SideProduct();

        sideProduct.setTitle(sideProductRequest.getTitle());
        sideProduct.setContent(sideProductRequest.getContent());
        sideProduct.setPrice(sideProductRequest.getPrice());



        final String fixedStringPath = "../../SSS-Front/frontend/src/assets/selfSalad/";
        try {
            log.info("requestImageFile - filename: " + sideProductImgList.getOriginalFilename());

            UUID randomName = UUID.randomUUID();
            String fileRandomName = randomName + sideProductImgList.getOriginalFilename();

            // 파일 경로지정
            FileOutputStream writer = new FileOutputStream(
                    fixedStringPath + fileRandomName);

            writer.write(sideProductImgList.getBytes());
            writer.close();

            SideProductImg sideProductImg = new SideProductImg(
                    sideProductImgList.getOriginalFilename(),
                    fileRandomName,
                    sideProduct
            );
            sideProductImg.registerToSideProduct();
            sideProductsImgRepository.save(sideProductImg);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sideProductsRepository.save(sideProduct);
    }

    // 리스트
    @Override
    @Transactional
    public List<SideProduct> list() {return sideProductsRepository.findAll(Sort.by(Sort.Direction.DESC, "sideProductId"));};

    // 상세페이지(읽기)
    @Override
    public SideProductResponse read(Long sideProductId) {
        Optional<SideProduct> maybeSideProduct = sideProductsRepository.findById(sideProductId);

        if(maybeSideProduct.isEmpty()){
            log.info("없는데?");
            return null;
        }
        SideProduct sideProduct = maybeSideProduct.get();

        SideProductResponse sideProductResponse = new SideProductResponse(
                sideProduct.getSideProductId(),
                sideProduct.getContent(),
                sideProduct.getPrice(),
                sideProduct.getTitle(),
                sideProduct.getSideProductImg()
        );
        return sideProductResponse;
    }

    // 삭제
    @Override
    public void remove(Long sideProductId) {
        sideProductsRepository.deleteById(sideProductId);
    }

    // 수정
    @Override
    public SideProductResponse modify(Long productId, SideProductRequest sideProductRequest) {
        Optional<SideProduct> maybeSideProduct = sideProductsRepository.findById(productId);

        if(maybeSideProduct.isEmpty()){
            return null;
        }

        SideProduct sideProduct = maybeSideProduct.get();

        sideProduct.setTitle(sideProductRequest.getTitle());
        sideProduct.setContent(sideProductRequest.getContent());
        sideProduct.setPrice(sideProductRequest.getPrice());

        sideProductsRepository.save(sideProduct);

        SideProductResponse sideProductResponse = new SideProductResponse(
                sideProduct.getSideProductId(),
                sideProductRequest.getContent(),
                sideProductRequest.getPrice(),
                sideProductRequest.getTitle()
        );

        return sideProductResponse;
    }


}
