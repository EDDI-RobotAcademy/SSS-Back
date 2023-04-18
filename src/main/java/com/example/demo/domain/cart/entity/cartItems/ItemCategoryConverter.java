package com.example.demo.domain.cart.entity.cartItems;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ItemCategoryConverter implements Converter<String, ItemCategoryType>  {

    @Override
    public ItemCategoryType convert(String itemCategoryType) {

        return ItemCategoryType.valueOf(itemCategoryType);
    }
}
