package com.ra.myapi;

import com.ra.Article;
import com.ra.Commande;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommandeTest {
    private static final String URLBASE = "http://localhost:8080/commandes";


    @Test
    @Order(1)
    public void getListCommande(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Commande>> response =  restTemplate.exchange(URLBASE, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Commande>>() {
                });
        List<Commande> commandes = response.getBody();
        assertTrue((commandes.size()) >= 2);
    }

    @Test
    @Order(2)
    public void ajouterCommande(){
        RestTemplate restTemplate = new RestTemplate();
        Commande commande = new Commande();
        List<Article> articles = new ArrayList<>();
        Article article = new Article();
        article.setId(55);
        article.setName("Disque SSD");
        article.setDescription("Asus");
        articles.add(article);
        commande.setArticles(articles);
        commande.setNumeroCommande(34);
        commande.setNumeroClient(45);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Commande> requestEntity = new HttpEntity<Commande>(commande,
                headers);

        ResponseEntity<Commande> response =
                restTemplate.postForEntity(URLBASE, requestEntity, Commande.class);

        Commande result = response.getBody();

        assertNotNull(result);
        assertEquals(result.getNumeroCommande(), commande.getNumeroCommande());
    }
    @Test
    @Order(4)
    public void updateCommande() {
        List<Article> articles = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        Commande commande = new Commande();
        commande.setNumeroClient(1);
        commande.setNumeroCommande(1);
        Article myArticle = new Article();
        myArticle.setId(1);
        myArticle.setName("name changed !!!");
        myArticle.setDescription("Changed !!!");
        articles.add(myArticle);
        commande.setArticles(articles);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Commande> requestEntity = new HttpEntity<Commande>(commande,
                headers);
        ResponseEntity<Commande> response = restTemplate.exchange(URLBASE,
                HttpMethod.PUT, requestEntity, Commande.class);
        Commande result = response.getBody();
        assertNotNull(result);
        assertEquals(result.getNumeroCommande(), commande.getNumeroCommande());
        assertEquals(result.getNumeroClient(), commande.getNumeroClient());
    }

    @Test
    @Order(3)
    public void getArticleInCommande(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Article> response =  restTemplate.exchange(URLBASE + String.valueOf("/") + 1 + String.valueOf("/")+1, HttpMethod.GET, null,
                new ParameterizedTypeReference<Article>() {
                });
       Article article = response.getBody();
        assertNotNull((article));
    }

}
