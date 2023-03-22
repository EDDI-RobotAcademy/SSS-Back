package com.example.demo.domain.selfSalad.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ImageResource {
    private String imagePath;

}
