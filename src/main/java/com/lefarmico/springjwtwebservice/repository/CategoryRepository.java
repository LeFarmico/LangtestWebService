package com.lefarmico.springjwtwebservice.repository;

import com.lefarmico.springjwtwebservice.entity.Category;
import com.lefarmico.springjwtwebservice.entity.FileCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(
            nativeQuery = true,
            name = "SELECT * FROM category WHERE languageId= language_id:"
    )
    public List<Category> findByLanguageId(@Param("language_id") Long languageId);
}
