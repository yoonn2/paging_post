package com.example.paging_sort.service;

import com.example.paging_sort.Entity.Article;
import com.example.paging_sort.repository.ArticleRepository;
import com.example.paging_sort.viewmodel.ArticleViewModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<ArticleViewModel> index() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(ArticleViewModel::new)
                .collect(Collectors.toList());
    }

    public ArticleViewModel show(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return null;
        }
        return new ArticleViewModel(article);
    }

    public ArticleViewModel create(ArticleViewModel viewModel) {
        Article article = viewModel.toEntity();
        if (article.getId() != null) {
            return null;
        }
        Article savedArticle = articleRepository.save(article);
        return new ArticleViewModel(savedArticle);
    }

    public ArticleViewModel update(Long id, ArticleViewModel viewModel) {
        Article article = viewModel.toEntity();
        log.info("id: {}, article: {}", id, article.toString());
        Article target = articleRepository.findById(id).orElse(null);
        if (target == null || id != article.getId()) {
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return null;
        }
        target.patch(article);
        Article updated = articleRepository.save(target);
        return new ArticleViewModel(updated);

    }

    public ArticleViewModel delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);
        if (target == null) {
            return null;
        }
        articleRepository.delete(target);
        return new ArticleViewModel(target);
    }

    public List<ArticleViewModel> searchByTitle(String title) {
        List<Article> articles = articleRepository.findByTitleContaining(title);
        return articles.stream()
                .map(ArticleViewModel::new)
                .collect(Collectors.toList());

    }
    @Transactional
    public List<ArticleViewModel> createArticles(List<ArticleViewModel> viewModels) {
        List<Article> articleList = viewModels.stream()
                .map(ArticleViewModel::toEntity)
                .collect(Collectors.toList());
        articleList.forEach(articleRepository::save);
        articleRepository.findById(-1L)
                .orElseThrow(() -> new IllegalArgumentException("결제 실패!"));
        return articleList.stream()
                .map(ArticleViewModel::new)
                .collect(Collectors.toList());
    }
}
