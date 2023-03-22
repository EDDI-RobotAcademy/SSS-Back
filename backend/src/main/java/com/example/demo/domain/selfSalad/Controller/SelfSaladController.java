package com.example.demo.domain.selfSalad.Controller;

import com.example.demo.domain.selfSalad.Controller.dto.IngredientList;
import com.example.demo.domain.selfSalad.service.SelfSaladService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ss")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class SelfSaladController {
    final private SelfSaladService selfSaladService;

    public SelfSaladController(SelfSaladService selfSaladService) {
        this.selfSaladService = selfSaladService;
    }

    @GetMapping("/list")
    public List<IngredientList> ingredientList () {
        log.info("boardList()");

        return selfSaladService.list();
    }

}
