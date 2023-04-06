package com.example.demo.domain.config;

import com.example.demo.domain.selfSalad.entity.Amount;
import com.example.demo.domain.selfSalad.entity.AmountType;
import com.example.demo.domain.selfSalad.entity.Category;
import com.example.demo.domain.selfSalad.entity.CategoryType;
import com.example.demo.domain.selfSalad.repository.AmountRepository;
import com.example.demo.domain.selfSalad.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class DBInitializer {
    private final CategoryRepository categoryRepository;
    private final AmountRepository amountRepository;

    @PostConstruct
    private void init () {
        log.debug("initializer 시작!");

        initCategoryTypes();
        initAmountTypes();

        log.debug("initilizer 동작 완료!");
    }

    private void initAmountTypes () {
        log.debug("Category Type 초기화!");

        try {
            final Set<AmountType> amounts =
                    amountRepository.findAll()
                            .stream()
                            .map(Amount::getAmountType)
                            .collect(Collectors.toSet());

            for (AmountType type: AmountType.values()) {
                if (!amounts.contains(type)) {
                    final Amount amount = new Amount(type);
                    amountRepository.save(amount);
                    log.info("New amount, {}, 추가되었습니다.", amount);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void initCategoryTypes () {
        log.debug("Category Type 초기화!");

        try {
            final Set<CategoryType> categories =
                    categoryRepository.findAll()
                            .stream()
                            .map(Category::getCategoryType)
                            .collect(Collectors.toSet());

            for (CategoryType type: CategoryType.values()) {
                if (!categories.contains(type)) {
                    final Category category = new Category(type);
                    categoryRepository.save(category);
                    log.info("New category, {}, 추가되었습니다.", category);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
