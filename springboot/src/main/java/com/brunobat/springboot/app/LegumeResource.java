package com.brunobat.springboot.app;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;

@RestController
public class LegumeResource {

    @PersistenceContext()
    private EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity provision() {
        manager.merge(new Legume("Carrot", "Root vegetable, usually orange"));
        manager.merge(new Legume("Zucchini", "Summer squash"));
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/legumes")
    @HystrixCommand(fallbackMethod = "fallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
            }
    )
    public Collection<Legume> list() {
        return manager.createQuery("SELECT l FROM Legume l").getResultList();
    }

    public Collection<Legume> fallback() {
        return Arrays.asList(new Legume("Broccoli", "This is the default legume, mate"));
    }
}
