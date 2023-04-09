package com.example.demo.domain.products.service.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRegisterRequest {

    private Long productId;
    private String writer;
    private int rating;
    private String content;

}
