package com.ra.myapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping(value = "/monitoring")
public class IsAlive {

    private static final Logger logger = LoggerFactory.getLogger(IsAlive.class);

    @GetMapping(value = "/isAlive")
    public ResponseEntity<String> isAlive()
    {
        logger.info(" is Alive ");
        return new ResponseEntity<String>("Réponse du serveur: "+HttpStatus.OK.name(),
                HttpStatus.OK);
    }
}