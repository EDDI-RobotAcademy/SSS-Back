package com.example.demo.domain.sideProducts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SideProductImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgId;

    @Column
    private String editedImg;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "side_product_id")
    private SideProduct sideProduct;

    public SideProductImg (String editedImg, SideProduct sideProduct) {
        this.editedImg = editedImg;
        this.sideProduct = sideProduct;
    }

    public void registerToSideProduct(){
        this.sideProduct.setSideProductImg(this);
    }

}
