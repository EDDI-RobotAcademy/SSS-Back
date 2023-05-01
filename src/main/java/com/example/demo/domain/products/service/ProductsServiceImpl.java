package com.example.demo.domain.products.service;

import com.example.demo.domain.products.controller.form.ProductImgResponse;
import com.example.demo.domain.products.entity.Favorite;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.entity.ProductImg;
import com.example.demo.domain.products.repository.FavoriteRepository;
import com.example.demo.domain.products.repository.ProductsImgRepository;
import com.example.demo.domain.products.repository.ProductsRepository;
import com.example.demo.domain.products.service.request.ProductsInfoRequest;
import com.example.demo.domain.products.service.response.ProductListResponse;
import com.example.demo.domain.products.service.response.ProductReadResponse;
import com.example.demo.domain.utility.file.FileUploadUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
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
    final private FavoriteRepository favoriteRepository;


    public List<ProductListResponse> getList(String sortBy) {
        List<Product> products = productsRepository.findAll(Sort.by(Sort.Direction.DESC, sortBy));
        List<ProductListResponse> productList = new ArrayList<>();

        for(Product product : products) {
            List<ProductImgResponse> productImgList = productsImgRepository.findImagePathByProductId(product.getProductId());
            productList.add(new ProductListResponse(
                    product.getProductId(), product.getTitle(), product.getPrice(), product.getContent(),
                    product.getViewCnt(), product.getFavoriteCnt(), productImgList
            ));
        }
        return productList;
    }

    @Override
    public List<ProductListResponse> list() {
        return getList("productId");
    }

    @Override
    public void register(List<MultipartFile> files, ProductsInfoRequest request) throws IOException {
        List<ProductImg> imgList = new ArrayList<>();
        Product product = request.toProduct();

        product.setTitle(request.getTitle());
        product.setPrice(request.getPrice());
        product.setContent(request.getContent());
        product.setProductDetail(request.getProductDetail());

        for (MultipartFile multipartFile : files) {
            String originImg = multipartFile.getOriginalFilename();
            String editedImg = FileUploadUtils.generateUniqueFileName(originImg);
            String imgPath = "../SSS-Front/src/assets/product/" + editedImg;

            FileUploadUtils.writeFile(multipartFile, imgPath);

            ProductImg productImg = new ProductImg();
            productImg.setOriginImg(originImg);
            productImg.setProduct(product);
            productImg.setEditedImg(editedImg);
            productImg.setImgPath(imgPath);
            imgList.add(productImg);
            log.info(multipartFile.getOriginalFilename());
        }

        productsRepository.save(product);
        productsImgRepository.saveAll(imgList);
    }

    @Override
    public ProductReadResponse read(Long productId) {
        Optional<Product> maybeProduct = productsRepository.findById(productId);
        Product product = maybeProduct.get();
        if (maybeProduct.isEmpty()) {
            log.info("없음!");
            return null;
        }

        List<ProductImgResponse> productImgList = productsImgRepository.findImagePathByProductId(product.getProductId());
        ProductReadResponse productRead = new ProductReadResponse(
                product.getProductId(), product.getTitle(), product.getPrice(), product.getContent(),
                product.getViewCnt(), product.getFavoriteCnt(), product.getProductDetail(), productImgList);

        return productRead;
    }

    @Override
    public List<ProductImgResponse> findProductImage(Long productId) {
        List<ProductImgResponse> productImgList = productsImgRepository.findImagePathByProductId(productId);

        return productImgList;
    }

    @Transactional
    @Override
    public Product modify(Long productId, List<MultipartFile> productImgList, ProductsInfoRequest request) {
        List<ProductImg> imgList = new ArrayList<>();
        List<ProductImgResponse> removeImgs = productsImgRepository.findImagePathByProductId(productId);

        final String imgPath = "../SSS-Front/src/assets/product/";

        for(int i = 0; i < removeImgs.size(); i++) {
            String fileName = removeImgs.get(i).getEditedImg();
            System.out.println(fileName);

            File file = new File(imgPath + fileName);

            if (file.exists()) {
                file.delete();
            } else {
                System.out.println("파일 삭제 실패");
            }
        }
        productsImgRepository.deleteProductImgByProductId(productId);

        Optional<Product> maybeProduct = productsRepository.findById(productId);
        if(maybeProduct.isEmpty()) {
            System.out.println("해당 productId 정보 없음: " + productId);
            return null;
        }

        Product product = maybeProduct.get();
        product.setTitle(request.getTitle());
        product.setContent(request.getContent());
        product.setPrice(request.getPrice());
        product.setProductDetail(request.getProductDetail());

        try {
            for (MultipartFile multipartFile: productImgList) {
                log.info(multipartFile.getOriginalFilename());

                String original = multipartFile.getOriginalFilename();
                String edit = FileUploadUtils.generateUniqueFileName(original);
                String imagePath = imgPath + edit;

                FileUploadUtils.writeFile(multipartFile, imagePath);

                ProductImg productImg = new ProductImg();
                productImg.setOriginImg(original);
                productImg.setEditedImg(edit);
                productImg.setProduct(product);
                productImg.setImgPath(imgPath);
                imgList.add(productImg);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        productsRepository.save(product);
        productsImgRepository.saveAll(imgList);

        return product;
    }

    @Override
    public Product modifyWithoutImg(Long productId, ProductsInfoRequest request) {
        Optional<Product> maybeProduct = productsRepository.findById(productId);

        Product product = maybeProduct.get();
        product.setTitle(request.getTitle());
        product.setContent(request.getContent());
        product.setPrice(request.getPrice());
        product.setProductDetail(request.getProductDetail());

        productsRepository.save(product);
        return product;
    }

    @Transactional
    @Override
    public void delete(Long productId) {
        List<ProductImgResponse> removeImgs = productsImgRepository.findImagePathByProductId(productId);

        final String imgPath = "../SSS-Front/src/assets/product/";

        for(int i = 0; i < removeImgs.size(); i++) {
            String fileName = removeImgs.get(i).getEditedImg();
            System.out.println(fileName);

            File file = new File(imgPath + fileName);

            if(file.exists()) {
                file.delete();
            } else {
                System.out.println("삭제 실패");
            }
        }

        favoriteRepository.deleteByProduct_productId(productId);
        productsImgRepository.deleteProductImgByProductId(productId);
        productsRepository.deleteById(productId);
    }

    @Override
    public void viewCntUp(Long productId) {
        Optional<Product> maybeProduct = productsRepository.findById(productId);
        Product product = maybeProduct.get();
        product.updateViewCnt();
        productsRepository.save(product);
    }

    @Override
    public List<ProductListResponse> listByView() {
        return getList("viewCnt");
    }

    @Override
    public List<ProductListResponse> listByFavorite() {
        return getList("favoriteCnt");
    }
}

