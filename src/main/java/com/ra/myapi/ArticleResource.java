package com.ra.myapi;

import com.ra.Article;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value = "/article")
public class ArticleResource {
    private List<Article> articles;
    private int id = 0;
    private static final Logger logger =  LoggerFactory.getLogger(ArticleResource.class);
    @PostConstruct
    public void init() {
        articles = new ArrayList<Article>();
        Article article = null;
        article = new Article();
        article.setId(id++);
        article.setName("Carte graphique");

        Article article2 = null;
        article2 = new Article();
        article2.setId(id++);
        article2.setName("Carte mémoire");
        articles.add(article);
        articles.add(article2);
    }
    @GetMapping()
    public ResponseEntity<List<Article>> articles() {
        logger.info(" liste article ");
        return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<Article> article(@PathVariable("id") int id) {
        logger.info(" liste article ");
        Article article = articles.stream().filter(article1 ->
             article1.getId() == id
        ).findFirst()
                .orElse(null);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }
    @GetMapping("{id}/{name}")
    public ResponseEntity<Article> getArticleByIdByName(
            @PathVariable("id") long id ,
            @PathVariable("name") String name
    ){
        logger.info("Voici l'article :  ");
      Article articleByIdByName = articles.stream().filter(
              article->
                        article.getId() == id && article.getName().equals(name)
                ).findFirst()
                .orElse(null);

        return new ResponseEntity<Article>(articleByIdByName, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> ajouterArticle(@RequestBody() Article article ){
        article.setId(id++);
        this.articles.add(article);
        return new ResponseEntity<Article>(article,HttpStatus.OK);

    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> modifierArticle(@RequestBody() Article article){
        logger.info("Modifier l'article");
        Article art = this.articles.stream().filter(
                articleSearch -> articleSearch.getId() == article.getId())
                .findFirst()
                .orElse(null);

        if(art!=null){
            art.setName(article.getName());
            art.setDescription(article.getDescription());
        }

        return  new ResponseEntity<Article>(article,HttpStatus.OK);
    }
}
