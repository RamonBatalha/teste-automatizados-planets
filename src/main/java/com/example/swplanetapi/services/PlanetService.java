package com.example.swplanetapi.services;




import java.util.List;

import org.springframework.stereotype.Service;

import com.example.swplanetapi.Exceptions.ResourceNotFoundException;
import com.example.swplanetapi.domain.Planet;
import com.example.swplanetapi.repository.PlanetRepository;

@Service
public class PlanetService {
       
     
     private PlanetRepository planetRepository;

     public PlanetService(PlanetRepository planetRepository){
        this.planetRepository = planetRepository;
     }

     public Planet create(Planet planet){
          return planetRepository.save(planet);
     }

     public Planet update(Planet planet){
          
          var entity = planetRepository.findById(planet.getId()).orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));
          
        entity.setName(planet.getName());
        entity.setClimate(planet.getClimate());
        entity.setTerrain(planet.getTerrain());

          return planetRepository.save(entity);
     }

     public List<Planet> findAll(){
          return planetRepository.findAll();
     }

     public Planet findById(Long id){
           
          //entity é um Planet caso tenha id
          var entity = planetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));
           
          return entity;

     }

      public void delete(Long id){

          var entity = planetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));

          planetRepository.delete(entity);
      }
}
