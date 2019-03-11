package com.brunobat.springboot.app;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@RestController
public class LegumeResource {

    private Set<Legume> legumes = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public LegumeResource() {
        legumes.add(new Legume("Carrot", "Root vegetable, usually orange"));
        legumes.add(new Legume("Zucchini", "Summer squash"));
    }

    @GetMapping("/legumes")
    @HystrixCommand(fallbackMethod = "fallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
            }
    )
    public Collection<Legume> list() {
        return legumes;
    }

    public Collection<Legume> fallback() {
        return Arrays.asList(new Legume("Broccoli", "This is the default legume, mate"));
    }
}
