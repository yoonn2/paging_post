package com.example.paging_sort.repository;

import com.example.paging_sort.Entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTitleContaining(String title);

    @Override
    ArrayList<Article> findAll();
}
