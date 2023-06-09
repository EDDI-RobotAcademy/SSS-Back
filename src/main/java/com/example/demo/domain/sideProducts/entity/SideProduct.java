package com.example.demo.domain.sideProducts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SideProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sideProductId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long price;

    @OneToOne(mappedBy = "sideProduct", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private SideProductImg sideProductImg;

    public SideProduct(String content, Long price, String title) {
        this.content = content;
        this.price = price;
        this.title = title;
    }

    public void registerImg(SideProductImg sideProductImg) {
        this.sideProductImg = sideProductImg;
    }


    public void modifySideProduct(String title, String content, Long price) {
        this.title = title;
        this.content = content;
        this.price = price;
    }
}
