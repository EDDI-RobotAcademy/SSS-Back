package com.example.demo.domain.products.service;

import com.example.demo.domain.products.controller.form.ProductImgResponse;
import com.example.demo.domain.products.controller.form.ProductReadResponse;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.entity.ProductImg;
import com.example.demo.domain.products.repository.ProductsImgRepository;
import com.example.demo.domain.products.repository.ProductsRepository;
import com.example.demo.domain.products.service.request.ProductsRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductsServiceImpl implements ProductsService {

    final private ProductsRepository productsRepository;
    final private ProductsImgRepository productsImgRepository;

    @Override
    public List<Product> list() {
        List<Product> productList = productsRepository.findAll(Sort.by(Sort.Direction.DESC, "productId"));

        log.info("상품 리스트: " + String.valueOf(productList));

        return productList;
    }

    @Override
    public void register(List<MultipartFile> files, ProductsRegisterRequest request) {
        List<ProductImg> imgList = new ArrayList<>();
        Product product = request.toProduct();

        product.setTitle(request.getTitle());
        product.setPrice(request.getPrice());
        product.setContent(request.getContent());

        for (MultipartFile multipartFile : files) {
            UUID uuid = UUID.randomUUID();
            String originImg = multipartFile.getOriginalFilename();
            String editedImg = uuid + originImg;
            String imgPath = "C:/khproj/SSS-Front/frontend/src/assets/";

            ProductImg productImg = new ProductImg();
            productImg.setOriginImg(originImg);
            productImg.setProduct(product);
            productImg.setEditedImg(editedImg);
            productImg.setImgPath(imgPath);
            imgList.add(productImg);
            log.info(multipartFile.getOriginalFilename());

            try {

                FileOutputStream writer = new FileOutputStream(
                        imgPath + editedImg
                );

                writer.write(multipartFile.getBytes());
                writer.close();
                log.info("file upload success");

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        productsRepository.save(product);
        productsImgRepository.saveAll(imgList);
    }

    @Override
    public Product read(Long productId) {
        Optional<Product> maybeProduct = productsRepository.findById(productId);

        if (maybeProduct.isEmpty()) {
            log.info("없음!");
            return null;
        }
        Product product = maybeProduct.get();
        return product;
    }

    @Override
    public List<ProductImgResponse> findProductImage(Long productId) {
        List<ProductImgResponse> productImgList = productsImgRepository.findImagePathByProductId(productId);

        return productImgList;
    }

}
