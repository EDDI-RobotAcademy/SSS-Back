package com.example.demo.domain.selfSalad.Controller;

import com.example.demo.domain.selfSalad.entity.Ingredient;
import com.example.demo.domain.selfSalad.service.SelfSaladService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public List<Ingredient> ingredientList () {
        log.info("boardList()");

        return selfSaladService.list();
    }



}
