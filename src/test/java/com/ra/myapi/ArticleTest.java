package com.ra.myapi;

import com.ra.Article;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleTest {
private String URLBASE ="http://localhost:8080/article";
    @Test
    @Order(1)
    public void articleLength(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Article>> response =  restTemplate.exchange(URLBASE, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Article>>() {
                });
        List<Article> articles = response.getBody();
        assertTrue((articles.size()) >= 2);
    }

    @Test
    @Order(2)
    public void getArticleById() {
        RestTemplate restTemplate = new RestTemplate();
        int articleId = 1;
        String urlArticleId = String.valueOf(articleId);
        ResponseEntity<Article> response =
                restTemplate.exchange(URLBASE.concat("/" + urlArticleId),
                        HttpMethod.GET, null, new
                                ParameterizedTypeReference<Article>() {
                                });
        Article article = response.getBody();
        assertNotNull(article);
    }

    @Test
    @Order(3)
    public void addArticle() {
        RestTemplate restTemplate = new RestTemplate();
        Article article = new Article();
        article.setId(55);
        article.setName("Disque SSD");
        article.setDescription("Asus");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Article> requestEntity = new HttpEntity<Article>(article,
                headers);

        ResponseEntity<Article> response =
                restTemplate.postForEntity(URLBASE, requestEntity, Article.class);

        Article result = response.getBody();

        assertNotNull(result);
        assertEquals(result.getName(), article.getName());
    }

    @Test
    @Order(4)
    public void updateArticle() {
        RestTemplate restTemplate = new RestTemplate();
        Article myArticle = new Article();
        myArticle.setId(1);
        myArticle.setName("name changed !!!");
        myArticle.setDescription("Changed !!!");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Article> requestEntity = new HttpEntity<Article>(myArticle,
                headers);
        ResponseEntity<Article> response = restTemplate.exchange(URLBASE,
                HttpMethod.PUT, requestEntity, Article.class);
        Article result = response.getBody();
        assertNotNull(result);
        assertEquals(result.getId(), myArticle.getId());
        assertEquals(result.getDescription(), myArticle.getDescription());
    }

}
