package com.ra.myapi;



import com.ra.Article;
import com.ra.Commande;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("commandes")
public class CommandeResource {
    private int numClient=10;
    private int numCommande = 1;
    private int  idArticle = 1;
    private List<Article> articles1;
    private List<Article> articles2;

    private List<Commande> commandes;
    @PostConstruct
    public  void init(){
        articles1 = new ArrayList<>();
        articles2 = new ArrayList<>();
        commandes = new ArrayList<>();
        Commande commande1 = new Commande();
        commande1.setNumeroCommande(numCommande++);
        commande1.setNumeroClient(numClient++);

        Article article1 = new Article();
        article1.setId(idArticle++);
        article1.setName("Carte graphique");
        article1.setDescription("ma carte graphique");

        Commande commande2 = new Commande();
        commande2.setNumeroCommande(numCommande++);
        commande2.setNumeroClient(numClient++);
        Article article2 = new Article();
        article2.setId(idArticle++);
        article2.setName("Carte graphique 2");
        article2.setDescription("ma carte graphique 2");

        this.articles2.add(article2);
        this.articles1.add(article1);

        commande1.setArticles(this.articles1);
        commande2.setArticles(this.articles2);

        commandes.add(commande1);
        commandes.add(commande2);
    }
    @GetMapping()
    public ResponseEntity<List<Commande>> getListeCommandes(){
            return new ResponseEntity<List<Commande>>(commandes, HttpStatus.OK);
    }

    @GetMapping("{numeroCommande}")
    public ResponseEntity<Commande> getListeByNumeroClient(@PathVariable("numeroCommande") int numeroCommande){
        Commande com = commandes.stream().filter(commande -> commande.getNumeroCommande() == numeroCommande).findFirst().orElse(null);
        return new ResponseEntity<Commande>(com,HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Commande> ajouterCommande(@RequestBody Commande commande){
        this.commandes.add(commande);
        return new ResponseEntity<Commande>(commande,HttpStatus.OK);
    }
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Commande> updateCommande(@RequestBody() Commande commande){
        Commande com = commandes.stream().filter(commande1 -> commande1.getNumeroCommande()==commande.getNumeroCommande()).findFirst().orElse(null);
        if(com!=null){
            com.setNumeroCommande(commande.getNumeroCommande());
            com.setNumeroClient(commande.getNumeroCommande());
            com.setArticles(commande.getArticles());
        }
        return new ResponseEntity<Commande>(com,HttpStatus.OK);
    }
    @GetMapping("{id}/{numCommande}")
    public ResponseEntity<Article> getArticleInCommande(@PathVariable("id") int id, @PathVariable("numCommande") int numCommande) {
        Commande commande = commandes.stream()
                .filter(c -> c.getNumeroCommande() == numCommande)
                .findFirst()
                .orElse(null);

        if (commande == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Article article = commande.getArticles().stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);

        if (article == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(article, HttpStatus.OK);
    }

}
