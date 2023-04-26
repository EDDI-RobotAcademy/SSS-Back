package com.example.demo.domain.products.service;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.products.controller.form.FavoriteResponse;
import com.example.demo.domain.products.controller.form.ProductImgResponse;
import com.example.demo.domain.products.entity.Favorite;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.repository.FavoriteRepository;
import com.example.demo.domain.products.repository.ProductsImgRepository;
import com.example.demo.domain.products.repository.ProductsRepository;
import com.example.demo.domain.products.service.response.FavoriteListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    final private FavoriteRepository favoriteRepository;
    final private ProductsRepository productsRepository;
    final private MemberRepository memberRepository;
    final private ProductsImgRepository productsImgRepository;

    public FavoriteResponse changeLike(Long memberId, Long productId) {
        Optional<Member> maybeMember = memberRepository.findById(memberId);
        Optional<Product> maybeProduct = productsRepository.findById(productId);

        if(maybeMember.isPresent() && maybeProduct.isPresent()) {
            Optional<Favorite> maybeFavorite = favoriteRepository.findByProductAndMember(productId, memberId);

            if(maybeFavorite.isPresent()) {
                log.info("찜내역 O");
                Boolean isLike = maybeFavorite.get().isLike();

                if(isLike) {
                    log.info("찜O->찜X");
                    maybeFavorite.get().setIsLike(false);
                    maybeFavorite.get().getProduct().setFavoriteCnt(maybeFavorite.get().getProduct().getFavoriteCnt() - 1);
                    favoriteRepository.save(maybeFavorite.get());
                    productsRepository.save(maybeFavorite.get().getProduct());
                    return new FavoriteResponse(false, maybeFavorite.get().getMember().getMemberId(), maybeFavorite.get().getProduct().getProductId());
                } else {
                    log.info("찜X->찜O");
                    maybeFavorite.get().setIsLike(true);
                    maybeFavorite.get().getProduct().setFavoriteCnt(maybeFavorite.get().getProduct().getFavoriteCnt() + 1);
                    favoriteRepository.save(maybeFavorite.get());
                    productsRepository.save(maybeFavorite.get().getProduct());
                    return new FavoriteResponse(true, maybeFavorite.get().getMember().getMemberId(), maybeFavorite.get().getProduct().getProductId());
                }
            } else {
                log.info("찜내역 X");
                Favorite favorite = new Favorite();
                favorite.setIsLike(true);
                favorite.setMember(maybeMember.get());
                favorite.setProduct(maybeProduct.get());
                favoriteRepository.save(favorite);

                Product product = favorite.getProduct();
                product.setFavoriteCnt(product.getFavoriteCnt() + 1);
                productsRepository.save(product);

                return new FavoriteResponse(true, favorite.getMember().getMemberId(), favorite.getProduct().getProductId());
            }
        }
        throw new RuntimeException("해당 멤버나 상품 없음");
    }

    @Override
    public Boolean likeStatus(Long memberId, Long productId) {
        Optional<Product> maybeProduct = productsRepository.findById(productId);
        Optional<Member> maybeMember = memberRepository.findById(memberId);

        if(maybeMember.isPresent() && maybeProduct.isPresent()) {
            Optional<Favorite> maybeFavorite = favoriteRepository.findByProductAndMember(productId, memberId);
            if(maybeFavorite.isPresent()) {
                Boolean isLike = maybeFavorite.get().isLike();
                if(isLike) {
                    log.info("찜O");
                    return true;
                } else {
                    log.info("찜X");
                    return false;
                }
            } else {
                log.info("찜 없음");
                return false;
            }
        }
        throw new RuntimeException("찜 없음");
    }

    @Override
    public List<FavoriteListResponse> favoriteList (Long memberId) {
        List<Favorite> favorites = favoriteRepository.findFavoriteByMemberId(memberId);
        List<FavoriteListResponse> favoriteList = new ArrayList<>();

        for(Favorite favorite : favorites) {
            List<ProductImgResponse> productImgList = productsImgRepository.findImagePathByProductId(favorite.getProduct().getProductId());
            favoriteList.add(new FavoriteListResponse(
                    favorite.getFavoriteId(), favorite.isLike(), favorite.getProduct().getProductId(),
                    favorite.getProduct().getTitle(), favorite.getProduct().getPrice(),
                    productImgList, favorite.getMember().getMemberId()
            ));
        }
        return favoriteList;
    }
}
