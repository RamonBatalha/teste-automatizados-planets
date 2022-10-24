package com.example.swplanetapi.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.swplanetapi.domain.Planet;
import com.example.swplanetapi.services.PlanetService;



@RestController
@RequestMapping("/planets")
public class PlanetController {
    
    @Autowired
    private PlanetService planetService;

    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody @Valid Planet planet){
          
        Planet planetedCreated = planetService.create(planet);

        return ResponseEntity.status(HttpStatus.CREATED).body(planetedCreated);

    }
     
    @GetMapping(value = "/{id}")
    public Planet findById(@PathVariable("id") Long id){
          return planetService.findById(id);
    }

    @GetMapping
    public List<Planet> findAll(){
          return planetService.findAll();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
             planetService.delete(id);

             return ResponseEntity.noContent().build();
    }

    @PutMapping
    public Planet update(
        @RequestBody Planet client 
    ) {
       
        return planetService.update(client);
    }
    
}
