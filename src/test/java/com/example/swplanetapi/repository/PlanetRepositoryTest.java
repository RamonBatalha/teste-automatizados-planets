package com.example.swplanetapi.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;

import com.example.swplanetapi.common.PlanetConstants;
import com.example.swplanetapi.domain.Planet;

@DataJpaTest
public class PlanetRepositoryTest {
    
    @Autowired
    private PlanetRepository planetRepository;
     
    //cria métodos de acesso ao BD
    @Autowired
    private TestEntityManager testEntityManager;
    
    //zerando o id depois de cada teste para não ocorre erro de persistencia dupla de id
    @AfterEach
    public void afterEach() {
        PlanetConstants.PLANET.setId(null);
    }
     
    // ----------------------------- Test create --------------------------------------------//
    @Test
    public  void createPlanet_WithValidData_ReturnsPlanet () {
        Planet planet = planetRepository.save(PlanetConstants.PLANET);

        Planet sut = testEntityManager.find(Planet.class, planet.getId());
         
        //verificando se não retorna um valor nulo
        Assertions.assertThat(sut).isNotNull();
         
        //validando se a busca retorna os valores salvos, temos que fazer essa validação em cada item, pois o PLANET não possui um id
        Assertions.assertThat(sut.getName()).isEqualTo(PlanetConstants.PLANET.getName());
        Assertions.assertThat(sut.getClimate()).isEqualTo(PlanetConstants.PLANET.getClimate());
        Assertions.assertThat(sut.getTerrain()).isEqualTo(PlanetConstants.PLANET.getTerrain());
    }
     
    @Test
    public void createPlanet_WithInvalidData_ThrowsException() {
        //Mock para dados vazios
        Planet emptyPlanet = new Planet();
        //Mock para dados invalidos
        Planet invalidPlanet = new Planet("", "","");
         
        //teste para erro quando os campos forem vazios
        Assertions.assertThatThrownBy(() -> planetRepository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);
        //teste para erro quando os campos forem invalidos
        Assertions.assertThatThrownBy(() -> planetRepository.save(invalidPlanet)).isInstanceOf(RuntimeException.class);
       
    }

    //------------------------------------ Teste findById  --------------------------------//
     
    @Test
    public void getPLanet_ByExistingId_ReturnsPlanet(){
        Planet planet = testEntityManager.persistFlushFind(PlanetConstants.PLANET);

        Optional<Planet> planetOpt = planetRepository.findById(planet.getId());

        Assertions.assertThat(planetOpt).isNotEmpty();
        Assertions.assertThat(planetOpt.get()).isEqualTo(planet);
    }
    
    //Aqui não estamos persistindo nada, então o retorno é vazio
    @Test
    public void getPLanet_ByUnexistingId_ReturnsEmpty() {
        Optional<Planet> planetOpt = planetRepository.findById(1L);

        Assertions.assertThat(planetOpt).isEmpty();
    }

    //------------------------------------ Teste findAll  --------------------------------//
    
    @Test
    public void findAllPlanets_WithValidData_ReturnsListPlanet() {
          
        //Mock dos dois planetas
        Planet planet1 = new Planet( "planet1", "climate1", "terrai1");
        Planet planet2 = new Planet( "planet2", "climate2", "terrai2");
         
        //Persistindo dois planetas 
        Planet Planet1 = testEntityManager.persistFlushFind(planet1);
        Planet Planet2 = testEntityManager.persistFlushFind(planet2);
         
        //utilizando o findAll e armazenando os resultados
        List<Planet> PlanetsResponse = planetRepository.findAll();

        //teste para retorno não vazio
        Assertions.assertThat(PlanetsResponse).isNotEmpty();
        //teste para saber se contain os planetas persistidos na lista
        Assertions.assertThat(PlanetsResponse).contains(planet1, planet2);
        //teste para saber se o tamanho corresponde a 2
        Assertions.assertThat(PlanetsResponse).hasSize(2);
        //teste para saber se o posicionamento bate
        Assertions.assertThat(PlanetsResponse.get(0)).isEqualTo(Planet1);
        Assertions.assertThat(PlanetsResponse.get(1)).isEqualTo(Planet2);

    }
    
    @Test
    public void findAllPlanets_WithNoDatas_ReturnsListPlanetEmpty() {
      
        //utilizando o findAll sem dados armazenados
        List<Planet> PlanetsResponse = planetRepository.findAll();

        //teste para retorno vazio
        Assertions.assertThat(PlanetsResponse).isEmpty();
         
        //teste para tamanho de lista vazia
        Assertions.assertThat(PlanetsResponse).hasSize(0);
    }

    //---------------------------- Teste Delete --------------------------------//
    @Test
    public void removePlanet_WithExistingId_RemovePLanetsFromDatabase() {
        //adicionando dados
        Planet planet = testEntityManager.persistAndFlush(PlanetConstants.PLANET);
        
        //testando o método repassando o id do dado adicionado
        planetRepository.deleteById(planet.getId());
         
        //Verificando se o valor é Null após a exclusão
        Planet removedPlanet = testEntityManager.find(Planet.class, planet.getId());
        Assertions.assertThat(removedPlanet).isNull();
    }
    
    @Test
    public void removePLanet_WithUnexistingId_ThrowsException() {
        
        //testando se retornamos um erro de resultado vazio caso não exista id
        Assertions.assertThatThrownBy(() -> planetRepository.deleteById(1L)).isInstanceOf(EmptyResultDataAccessException.class);
    }
}
