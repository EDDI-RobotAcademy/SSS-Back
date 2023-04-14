package com.example.demo.domain.products.service;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.products.entity.Favorite;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.repository.FavoriteRepository;
import com.example.demo.domain.products.repository.ProductsRepository;
import com.example.demo.domain.products.service.request.FavoriteInfoRequest;
import com.example.demo.domain.security.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    final private FavoriteRepository favoriteRepository;
    final private ProductsRepository productsRepository;
    final private MemberRepository memberRepository;
    final private RedisService redisService;

    public Boolean changeLike(FavoriteInfoRequest request) {
        Optional<Member> maybeMember = memberRepository.findById(request.getMemberId());
        Optional<Product> maybeProduct = productsRepository.findById(request.getProductId());

        if(maybeMember.isPresent() && maybeProduct.isPresent()) {
            Optional<Favorite> maybeFavorite = favoriteRepository.findByProductAndMember(request.getProductId(), request.getMemberId());

            if(maybeFavorite.isPresent()) {
                log.info("찜내역 O");
                Boolean isLike = maybeFavorite.get().isLike();

                if(isLike) {
                    log.info("찜O->찜X");
                    maybeFavorite.get().setIsLike(false);
                    favoriteRepository.save(maybeFavorite.get());
                    return false;
                } else {
                    log.info("찜X->찜O");
                    maybeFavorite.get().setIsLike(true);
                    favoriteRepository.save(maybeFavorite.get());
                    return true;
                }
            } else {
                log.info("찜내역 X");
                Favorite favorite = new Favorite();
                favorite.setIsLike(true);
                favorite.setMember(maybeMember.get());
                favorite.setProduct(maybeProduct.get());
                favoriteRepository.save(favorite);
                return true;
            }
        }
        throw new RuntimeException("해당 멤버나 상품 없음");
    }

    @Override
    public Boolean likeStatus(FavoriteInfoRequest request) {
        Optional<Product> maybeProduct = productsRepository.findById(request.getProductId());
        Optional<Member> maybeMember = memberRepository.findById(request.getMemberId());

        if(maybeMember.isPresent() && maybeProduct.isPresent()) {
            Optional<Favorite> maybeFavorite = favoriteRepository.findByProductAndMember(request.getProductId(), request.getMemberId());
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
    public List<Favorite> favoriteList (Long memberId) {
        return favoriteRepository.findFavoriteByMemberId(memberId);
    }
}
