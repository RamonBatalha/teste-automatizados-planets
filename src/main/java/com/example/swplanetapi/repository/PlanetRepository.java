package com.example.swplanetapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.swplanetapi.domain.Planet;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {
    
}
