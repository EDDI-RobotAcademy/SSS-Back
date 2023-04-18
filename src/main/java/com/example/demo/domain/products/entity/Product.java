package com.example.demo.domain.products.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Id;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(length = 120, nullable = false)
    private String title;

    @Column(nullable = false)
    private Long price;

    @Lob
    private String content;

    @Column(nullable = false)
    private int viewCnt = 0;

    @Column(nullable = false)
    private int favoriteCnt = 0;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true) //LAZY 오류 - failed to lazily initialize a collection of role
    private List<ProductImg> productImgs = new ArrayList<>();             //orphanRemoval = true : 부모 엔티티에서 자식 엔티티 삭제 가능

    public Product(String title, Long price, String content) {
        this.title = title;
        this.price = price;
        this.content = content;
    }

    public void updateViewCnt() {
        this.viewCnt += 1;
    }
}
