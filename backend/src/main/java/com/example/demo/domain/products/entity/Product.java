package com.example.demo.domain.products.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Id;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
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
    private int goodCnt = 0;

    @CreationTimestamp
    private Date regDate;

    @UpdateTimestamp
    private Date updDate;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL) //LAZY 오류 - failed to lazily initialize a collection of role
    private List<ProductImg> productImgs = new ArrayList<>();

    public Product(String title, Long price, String content) {
        this.title = title;
        this.price = price;
        this.content = content;
    }
}
