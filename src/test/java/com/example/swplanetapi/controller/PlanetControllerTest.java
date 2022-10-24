package com.example.swplanetapi.controller;


import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.swplanetapi.common.PlanetConstants;
import com.example.swplanetapi.domain.Planet;
import com.example.swplanetapi.repository.PlanetRepository;
import com.example.swplanetapi.services.PlanetService;
import com.fasterxml.jackson.databind.ObjectMapper;

//anotação responsável pelo acesso aos métodos teste de requisição
@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {
    
    //responsável pelos métodos de requisição de teste
    @Autowired
    private MockMvc mockMvc;
    
    //responsável pera desserialização e serialização dos dados
    @Autowired
    private ObjectMapper objectMapper;
     
    //Mock do service
    @MockBean
    private PlanetService planetService;
    
    @MockBean
    private PlanetRepository planetRepository;
     

    //--------------------------- Testes Create --------------------------------
    @Test
    public void createPlanet_WithValidData_ReturnsCreated() throws Exception{
           
          //Mock do service
          when(planetService.create(PlanetConstants.PLANET)).thenReturn(PlanetConstants.PLANET);
           
          //testando o status de created e o retorno(retorna o próprio planet)
          mockMvc.perform(MockMvcRequestBuilders.post("/planets").content(objectMapper.writeValueAsString(PlanetConstants.PLANET)).contentType(MediaType.APPLICATION_JSON))
          .andExpect(MockMvcResultMatchers.status().isCreated())
          .andExpect(MockMvcResultMatchers.jsonPath("$").value(PlanetConstants.PLANET));
    }
     
    @Test
    public void createPlanet_WithInvalidData_ReturnsBadRequest() throws Exception {

         //Mock para dados vazios
         Planet emptyPlanet = new Planet();
         //Mock para dados invalidos
         Planet invalidPlanet = new Planet("", "","");
         
         //testando o Bad Request dados vazios
         mockMvc.perform(MockMvcRequestBuilders.post("/planets").content(objectMapper.writeValueAsString(emptyPlanet)).contentType(MediaType.APPLICATION_JSON))
         .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());

         //testando o Bad Request dados invalidos
         mockMvc.perform(MockMvcRequestBuilders.post("/planets").content(objectMapper.writeValueAsString(invalidPlanet)).contentType(MediaType.APPLICATION_JSON))
         .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
        
    }

    //----------------------------------Testes findById --------------------------------//

    @Test
    public void findByID_ByExistingID_ReturnsPlanet() throws Exception{
           
          //Mock do service
          when(planetService.findById(1L)).thenReturn(PlanetConstants.PLANET);
           
          //testando o findByID 
          mockMvc.perform(MockMvcRequestBuilders.get("/planets/1"))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.jsonPath("$").value(PlanetConstants.PLANET));
    }
    
   //----------------------------------Testes findAll --------------------------------//

   @Test
    public void findAll_ReturnsAllPlanets() throws Exception{
           
               
          List<Planet> planets = new ArrayList<Planet>();

          Planet planet1 = new Planet(1L, "planet1", "climate1", "terrai1");
          Planet planet2 = new Planet(2L, "planet2", "climate2", "terrai2");

          planets.add(planet1);
          planets.add(planet2);
          
          //Mock do service
          when(planetService.findAll()).thenReturn(planets);
           
          //testando o findAll
          mockMvc.perform(MockMvcRequestBuilders.get("/planets"))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
          .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("planet1"))
          .andExpect(MockMvcResultMatchers.jsonPath("$[0].climate").value("climate1"))
          .andExpect(MockMvcResultMatchers.jsonPath("$[0].terrain").value("terrai1"))
          .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
          .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("planet2"))
          .andExpect(MockMvcResultMatchers.jsonPath("$[1].climate").value("climate2"))
          .andExpect(MockMvcResultMatchers.jsonPath("$[1].terrain").value("terrai2"))
          .andDo(MockMvcResultHandlers.print());
      
      }

      //------------------------ Testando Delete ----------------------------------------------//
        
      @Test
      public void deletePlanet_ReturnNoPlanets() throws Exception {
            
            //teste de retorno no content
           mockMvc.perform(MockMvcRequestBuilders.delete("/planets/1"))
             .andExpect(MockMvcResultMatchers.status().isNoContent())
             .andDo(MockMvcResultHandlers.print());
      }

      //---------------------------- Test Put ------------------------------//
       
     
      @Test
      public void updatePlanet_ReturnPLanetUpdated() throws Exception {

            Planet planet = new Planet(1L, "planet1", "climate1", "terrai1");
            Planet updatedPlanet = new Planet(1L, "updated", "updated", "updated");

            when(planetRepository.findById(1L)).thenReturn(Optional.of(planet));
            when(planetRepository.save(updatedPlanet)).thenReturn(updatedPlanet);

            when(planetService.update(updatedPlanet)).thenReturn(updatedPlanet);

            mockMvc.perform(MockMvcRequestBuilders.put("/planets")
            .content(objectMapper.writeValueAsString(updatedPlanet)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$").value(updatedPlanet))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updatedPlanet.getName()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.climate").value(updatedPlanet.getClimate()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.terrain").value(updatedPlanet.getTerrain()))
            .andDo(MockMvcResultHandlers.print());
      }
      

}
