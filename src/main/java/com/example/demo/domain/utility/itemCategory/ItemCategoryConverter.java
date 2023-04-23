package com.example.demo.domain.utility.itemCategory;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ItemCategoryConverter implements Converter<String, ItemCategoryType>  {

    @Override
    public ItemCategoryType convert(String itemCategoryType) {

        return ItemCategoryType.valueOf(itemCategoryType);
    }
}
