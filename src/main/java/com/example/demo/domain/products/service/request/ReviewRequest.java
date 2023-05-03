package com.example.demo.domain.products.service.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    private Long memberId;
    private Long productId;
    private int rating;
    private String content;
    private Long orderId;

}
