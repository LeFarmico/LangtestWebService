package com.lefarmico.springjwtwebservice.initialData;

import com.lefarmico.springjwtwebservice.entity.Category;
import com.lefarmico.springjwtwebservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryInitialData {

    @Autowired
    CategoryRepository categoryRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        List<Category> categoryList = new ArrayList<>();
        Category category1 = Category.builder()
                .id(1L)
                .languageId(1L)
                .categoryName("Animals")
                .immutable(true)
                .build();
        Category category2 = Category.builder()
                .id(2L)
                .languageId(1L)
                .categoryName("Food")
                .immutable(true)
                .build();
        Category category3 = Category.builder()
                .id(3L)
                .languageId(2L)
                .categoryName("Animales")
                .immutable(true)
                .build();
        Category category4 = Category.builder()
                .id(4L)
                .languageId(2L)
                .categoryName("Alimento")
                .immutable(true)
                .build();
        categoryList.add(category1);
        categoryList.add(category2);
        categoryList.add(category3);
        categoryList.add(category4);
        categoryRepository.saveAll(categoryList);
    }
}
