package com.brunobat.springboot.app;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@RestController
public class FruitResource {

    private Set<Fruit> fruits = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public FruitResource() {
        fruits.add(new Fruit("Apple", "Winter fruit"));
        fruits.add(new Fruit("Pineapple", "Tropical fruit"));
    }

    @GetMapping("/fruits")
    public Set<Fruit> list() {
        return fruits;
    }

    @PostMapping("/fruits")
    public Set<Fruit> add(@RequestBody Fruit fruit) {
        fruits.add(fruit);
        return fruits;
    }

    @DeleteMapping("/fruits")
    public Set<Fruit> delete(@RequestBody final Fruit fruit) {
        fruits.remove(fruit);
        return fruits;
    }
}
