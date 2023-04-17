package com.example.demo.domain.products.controller.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteResponse {
    private Boolean isLike;
    private Long memberId;
    private Long productId;
}
