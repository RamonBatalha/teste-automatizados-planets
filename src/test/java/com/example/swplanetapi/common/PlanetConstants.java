package com.example.swplanetapi.common;


import com.example.swplanetapi.domain.Planet;

public class PlanetConstants {
    
    public static final Planet PLANET = new Planet("nome do planeta", "clima do planeta", "terreno do planeta");
    
    public static final Planet INVALID_PLANET = new Planet("", "", "");

    public static final Planet ID_PLANET = new Planet(1L, "cliente1", "climate1", "terrain1");
   

   
    
}
