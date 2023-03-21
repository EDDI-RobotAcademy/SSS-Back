package com.example.demo.domain.selfSalad.service;

import com.example.demo.domain.selfSalad.Controller.dto.IngredientList;
import com.example.demo.domain.selfSalad.entity.Ingredient;
import com.example.demo.domain.selfSalad.repository.SelfSaladRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SelfSaladServiceImpl implements SelfSaladService {

    @Autowired
    final private SelfSaladRepository selfSaladRepository;

    public SelfSaladServiceImpl(SelfSaladRepository selfSaladRepository) {
        this.selfSaladRepository = selfSaladRepository;
    }

    @Override
    public List<IngredientList> list(){
         List<Ingredient> allData = selfSaladRepository.findAll(Sort.by(Sort.Direction.DESC, "ingredientId") );

         List<IngredientList> ingredientList = new ArrayList<>();

         for( Ingredient oneData : allData ){
             ingredientList.add(new IngredientList(
                     oneData.getIngredientId(),oneData.getName(),
                     oneData.getImageResource() ));
         }
         return ingredientList;

    }
}
