package com.example.paging_sort.viewmodel;

import com.example.paging_sort.Entity.Article;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ArticleViewModel {

    private Long id;
    private String title;
    private String content;

    public ArticleViewModel(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }

    public Article toEntity() {
        return new Article(id, title, content);
    }



}
