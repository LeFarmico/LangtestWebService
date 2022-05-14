package com.lefarmico.springjwtwebservice;

import com.lefarmico.springjwtwebservice.entity.Category;
import com.lefarmico.springjwtwebservice.entity.FileCategory;
import com.lefarmico.springjwtwebservice.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JPAFileCategoryRepositoryTests {

    @Autowired
    CategoryRepository categoryRepository;

    private Category testCategory ;

    @BeforeEach
    void setUp() {
        testCategory = Category.builder()
                .categoryName("Ресторан")
                .languageId(1L)
                .build();
    }

    @DisplayName("JUnit test for save category operation")
    @Test
    void givenCategoryObject_whenSave_thenReturn_CategoryWord() {
        Category category = Category.builder()
                .categoryName("Животные")
                .languageId(2L)
                .build();
        Category savedCategory = categoryRepository.save(category);
        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit test for get all category operation")
    @Test
    void givenCategoryList_whenFindAll_thenCategoryList() {
        Category category1 = Category.builder()
                .categoryName("Животные")
                .languageId(2L)
                .build();

        List<Category> categoryList = categoryRepository.findAll();

        categoryRepository.save(testCategory);
        categoryRepository.save(category1);

        List<Category> newCategoryList = categoryRepository.findAll();

        assertThat(newCategoryList).isNotNull();
        assertThat(newCategoryList.size()).isEqualTo(categoryList.size() + 2);
    }

    @DisplayName("JUnit test for get category by id operation")
    @Test
    void givenCategoryObject_whenFindById_thenReturnCategoryObject() {

        categoryRepository.save(testCategory);
        Optional<Category> optionalCategory = categoryRepository.findById(testCategory.getId());
        if (optionalCategory.isPresent()) {
            Category categoryDB = optionalCategory.get();

            assertThat(categoryDB).isNotNull();
        } else {
            fail("Category not found.");
        }

    }

    @DisplayName("JUnit test for update category operation")
    @Test
    void givenCategoryObject_whenUpdateCategory_thenReturnUpdatedCategory() {

        categoryRepository.save(testCategory);
        Optional<Category> optionalCategory = categoryRepository.findById(testCategory.getId());
        if (optionalCategory.isPresent()) {
            Category categoryDB = optionalCategory.get();
            categoryDB.setCategoryName("новая_категория");
            categoryDB.setLanguageId(5L);
            categoryDB.setImmutable(false);

            Category updatedCategory = categoryRepository.save(categoryDB);

            assertThat(updatedCategory.getCategoryName()).isEqualTo("новая_категория");
            assertThat(updatedCategory.getLanguageId()).isEqualTo(5L);
            assertThat(updatedCategory.getImmutable()).isEqualTo(false);
        } else {
            fail("Category not found.");
        }
    }

    @DisplayName("JUnit test for delete category operation")
    @Test
    void givenCategoryObject_whenDelete_thenRemoveCategory() {

        categoryRepository.save(testCategory);

        categoryRepository.deleteById(testCategory.getId());
        Optional<Category> categoryOptional = categoryRepository.findById(testCategory.getId());

        assertThat(categoryOptional).isEmpty();
    }
}
