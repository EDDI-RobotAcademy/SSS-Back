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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.demo.domain.utility.common.CommonUtils.getSideProductById;

@Slf4j
@Service
@RequiredArgsConstructor
public class SideProductsServiceImpl implements SideProductsService {
    final private SideProductsRepository sideProductsRepository;
    final private SideProductsImgRepository sideProductsImgRepository;

    @Override
    @Transactional
    public void register(MultipartFile sideProductImgList, SideProductRequest sideProductRequest) {

        SideProduct sideProduct = sideProductRequest.toSideProduct();

        final String fixedStringPath = "../SSS-Front/src/assets/product/";
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
                    fileRandomName,
                    sideProduct
            );
            sideProductImg.registerToSideProduct();
            sideProductsImgRepository.save(sideProductImg);

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
        SideProduct sideProduct =
                getSideProductById(sideProductsRepository, sideProductId);

        return new SideProductResponse(
                sideProduct.getSideProductId(),
                sideProduct.getContent(),
                sideProduct.getPrice(),
                sideProduct.getTitle(),
                sideProduct.getSideProductImg()
        );
    }

    // 삭제
    @Override
    public void remove(Long sideProductId) {

        SideProduct sideProduct =
                getSideProductById(sideProductsRepository, sideProductId);

        sideProductsRepository.deleteById(sideProduct.getSideProductId());

        Optional<SideProduct> imageResource = sideProductsImgRepository.findImagePathBySideProductId(sideProductId);

        if (imageResource.isPresent()) {
            SideProductImg fileName = imageResource.get().getSideProductImg();
            File vueFile = new File("../SSS-Front/src/assets/product/" + fileName);

            if (vueFile.exists()) {
                vueFile.delete();
            } else {
                System.out.println("파일 삭제 실패!");
            }
            sideProductsImgRepository.deleteSpecificProduct(sideProductId);
        }
    }

    // 수정
    @Override
    public SideProductResponse modify(Long sideProductId, SideProductRequest sideProductRequest, MultipartFile sideProductImgList) {

        SideProduct sideProduct =
                getSideProductById(sideProductsRepository, sideProductId);

        sideProductsImgRepository.deleteSpecificProduct(sideProduct.getSideProductId());

        sideProduct.modifySideProduct(sideProductRequest.getTitle(),
                                      sideProductRequest.getContent(),
                                      sideProductRequest.getPrice()
        );

        final String fixedStringPath = "../SSS-Front/src/assets/product/";
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
                    fileRandomName,
                    sideProduct
            );
            sideProductImg.registerToSideProduct();
            sideProductsImgRepository.save(sideProductImg);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sideProductsRepository.save(sideProduct);

        return new SideProductResponse(
                sideProduct.getSideProductId(),
                sideProduct.getContent(),
                sideProduct.getPrice(),
                sideProduct.getTitle(),
                sideProduct.getSideProductImg()
        );
    }


}
