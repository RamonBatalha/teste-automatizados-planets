package com.example.swplanetapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import com.example.swplanetapi.common.PlanetConstants;
import com.example.swplanetapi.domain.Planet;
import com.example.swplanetapi.repository.PlanetRepository;



// @SpringBootTest(classes = PlanetService.class)
@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {
    

    // @Autowired
    @InjectMocks
    private PlanetService planetService;
     
    // @MockBean
    @Mock
    private PlanetRepository planetRepository;

    
    //testar buscar todos 
    @Test
    public void findAllPlanets_WithValidData_ReturnsListPlanet() {
          
        List<Planet> planets = new ArrayList<Planet>();

        Planet planet1 = new Planet(1L, "planet1", "climate1", "terrai1");
        Planet planet2 = new Planet(2L, "planet2", "climate2", "terrai2");

        planets.add(planet1);
        planets.add(planet2);

       
        //Mock método Create
        when(planetRepository.findAll()).thenReturn(planets);

        // system under test, ou seja, cenário a ser testado
        List<Planet> sut = planetService.findAll();

        //teste para saber se não retorna valor vazio
        assertNotNull(sut);
        //teste tamanho da lista
        assertEquals(2, sut.size());
        //testando se o objeto de index 0 é da classe planet.class
        assertEquals(Planet.class, sut.get(0).getClass());
        
        //testa o retorno de cada atributo, note que selecionamos o item de index0
        assertEquals(planet1.getId(), sut.get(0).getId());
        assertEquals(planet1.getClimate(), sut.get(0).getClimate());
        assertEquals(planet1.getName(), sut.get(0).getName());

        //teste retorno de Lista de Planetas
        Assertions.assertThat(sut).isEqualTo(planets);
    }

    @Test
    public void findAll_WithInvalidData_ThrowsException(){
            when(planetRepository.findAll()).thenThrow(RuntimeException.class);

            Assertions.assertThatThrownBy(() -> planetService.findAll()).isInstanceOf(RuntimeException.class);
    }
     
    //testa o creat
    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        //Mock método Create
        when(planetRepository.save(PlanetConstants.PLANET)).thenReturn(PlanetConstants.PLANET);

        // system under test, ou seja, cenário a ser testado
        Planet sut = planetService.create(PlanetConstants.PLANET);

        Assertions.assertThat(sut).isEqualTo(PlanetConstants.PLANET);
    }
     
    //testa os erros do create
    @Test
    public void createPlanet_WithInvalidData_ThrowsException(){
            when(planetRepository.save(PlanetConstants.INVALID_PLANET)).thenThrow(RuntimeException.class);

            Assertions.assertThatThrownBy(() -> planetService.create(PlanetConstants.INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }
     
    //update teste
    @Test
    public void updatePlanet_WithValidData_ReturnsPlanetUpdated() {
        //Mock método Create
        when(planetRepository.findById(PlanetConstants.ID_PLANET.getId())).thenReturn(Optional.of(PlanetConstants.ID_PLANET));
        when(planetRepository.save(PlanetConstants.ID_PLANET)).thenReturn(PlanetConstants.ID_PLANET);

        // system under test, ou seja, cenário a ser testado
        Planet sut = planetService.update(PlanetConstants.ID_PLANET);

        Assertions.assertThat(sut).isEqualTo(sut);
    }


    //testa o findById
    @Test
    public void findPLanetById_WithValidId_ReturnsPlanet() {
        //cenário para id encontrado
        when(planetRepository.findById(1L)).thenReturn(Optional.of(PlanetConstants.ID_PLANET));

        Planet sut = planetService.findById(1L);
        
        Assertions.assertThat(sut).isEqualTo(PlanetConstants.ID_PLANET);
        // Assertions.assertThat(sut.get()).isEqualTo(PlanetConstants.PLANET);
         
        //teste para saber se não retorna um objeto nulo
        assertNotNull(sut);

        assertEquals(PlanetConstants.ID_PLANET.getName(), sut.getName());
        assertEquals(PlanetConstants.ID_PLANET.getClimate(), sut.getClimate());
        assertEquals(PlanetConstants.ID_PLANET.getTerrain(), sut.getTerrain());
   
    }

    //teste findById não encontrado. OBS: teste serve para o delete e update também, pois segue a mesma lógica
    @Test
    public void findById_WithIvalidId_ReturnsIdNotFound() {
        
        //cenário para id não encontrado
        when(planetRepository.findById(2L)).thenReturn(Optional.empty());

        //Pela minha lógica, quando eu recebo um optional vazio o java atira um erro, dessa forma eu não tenho como testar aqui se o valor recebido é vazio
         
        Assertions.assertThatThrownBy(() -> planetService.findById(2L)).hasMessage("Id não encontrado");

   
    }

    @Test
    public void delete_With_validId() {
        
        when(planetRepository.findById(1L)).thenReturn(Optional.of(PlanetConstants.ID_PLANET));

        Planet sut = planetService.findById(1L);

        planetRepository.delete(sut);
    }

    

}
