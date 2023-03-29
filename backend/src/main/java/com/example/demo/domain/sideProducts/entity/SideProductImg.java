package com.example.demo.domain.sideProducts.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class SideProductImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgId;

    @Column
    private String originImg;
    @Column
    private String editedImg;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sideProductId")
    private SideProduct sideProduct;

    public SideProductImg (String originImg, String editedImg, SideProduct sideProduct) {
        this.originImg = originImg;
        this.editedImg = editedImg;
        this.sideProduct = sideProduct;
    }
    public void registerToSideProduct(){
        this.sideProduct.setSideProductImg(this);
    }


}
