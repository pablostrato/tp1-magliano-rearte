package ar.edu.unlam.tallerweb1.persistencia;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Ciudad;
import ar.edu.unlam.tallerweb1.modelo.Continente;
import ar.edu.unlam.tallerweb1.modelo.Pais;
import ar.edu.unlam.tallerweb1.modelo.Ubicacion;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class TestTP extends SpringTest{
    
    //2. PAISES DE HABLA INGLESA
    @Test
    @Transactional  @Rollback(true)
    public void idiomaIngles(){
    	
    	//**************************************************** Carga Pais
    	Pais p1 = new Pais();
    	p1.setNombre("Estados Unidos");
    	p1.setIdioma("Ingles");
    	getSession().save(p1);
    	
    	//**************************************************** Criteria
    	Criteria query = getSession().createCriteria(Pais.class)
    			.add(Restrictions.eq("idioma", "Ingles"));
    	
    	assertThat(query.list()).hasSize(1);
	}
    
    @Test
    //3. PAISES UBICADOS EN EUROPA
    @Transactional  @Rollback(true)
    public void paisesEuropa(){
    	
    	//**************************************************** Carga Continentes
    	Continente c1 = new Continente();
    	c1.setNombre("Europa");
    	getSession().save(c1);
    	
    	Continente c2 = new Continente();
    	c2.setNombre("Asia");
    	getSession().save(c2);
    	
    	//**************************************************** Carga Paises
    	Pais p1 = new Pais();
    	p1.setNombre("Holanda");
    	p1.setContinente(c1);
    	getSession().save(p1);
    	
    	Pais p2 = new Pais();
    	p2.setNombre("Alemania");
    	p2.setContinente(c1);
    	getSession().save(p2);
    	
    	Pais p3 = new Pais();
    	p3.setNombre("Japon");
    	p3.setContinente(c2);
    	getSession().save(p3);

    	//**************************************************** Criteria
    	Criteria query = getSession().createCriteria(Pais.class)
				.createAlias("continente", "continenteBuscado")
				.add(Restrictions.eq("continenteBuscado.nombre", "Europa"));
    	
    	assertThat(query.list()).hasSize(2);
	}
    
    //4. PAISES CUYA CAPITAL ESTA AL NORTE DEL TROPICO DE CANCER
    @Test
    @Transactional  @Rollback(true)
    public void capitalesNorteTropicoCancer(){
    	
    	//**************************************************** Carga Ubicacion
    	Ubicacion ubicacion1 = new Ubicacion();
    	ubicacion1.setLatitud(23.27);
    	getSession().save(ubicacion1);
    	
    	Ubicacion ubicacion2 = new Ubicacion();
    	ubicacion2.setLatitud(45.8);
    	getSession().save(ubicacion2);
    	
    	Ubicacion ubicacion3 = new Ubicacion();
    	ubicacion3.setLatitud(14.94);
    	getSession().save(ubicacion3);
    	
    	//**************************************************** Carga Ciudades
    	Ciudad elCairo = new Ciudad();
    	elCairo.setUbicacionGeografica(ubicacion1);
    	getSession().save(elCairo);
    	
    	Ciudad 	nuevaDelhi = new Ciudad();
    	nuevaDelhi.setUbicacionGeografica(ubicacion2);
    	getSession().save(nuevaDelhi);
    	
    	Ciudad buenosAires = new Ciudad();
    	buenosAires.setUbicacionGeografica(ubicacion3);
    	getSession().save(buenosAires);
    	
    	//**************************************************** Carga Paises
    	Pais egipto = new Pais();
    	egipto.setCapital(elCairo);
    	getSession().save(egipto);
    	
    	Pais india = new Pais();
    	india.setCapital(nuevaDelhi);
    	getSession().save(india);
    	
    	Pais argentina = new Pais();
    	argentina.setCapital(buenosAires);
    	getSession().save(argentina);
    	
    	//**************************************************** Criteria
    	Criteria query = getSession().createCriteria(Pais.class)
				.createAlias("capital", "cap")
				.createAlias("cap.ubicacionGeografica", "ubic")
				.add(Restrictions.gt("ubic.latitud", 23.26));
    	
    	assertThat(query.list()).hasSize(2);
    }
    
    //5. CIUDADES DEL HEMISFERIO SUR
    @Test
    @Transactional  @Rollback(true)
    public void ciudadesHemisferioSur(){
    	
    	//**************************************************** Carga Ubicacion
    	Ubicacion ubicacion1 = new Ubicacion();
    	ubicacion1.setLongitud(64.0);
    	getSession().save(ubicacion1);
    	
    	Ubicacion ubicacion2 = new Ubicacion();
    	ubicacion2.setLongitud(133.0);
    	getSession().save(ubicacion2);
    	
    	Ubicacion ubicacion3 = new Ubicacion();
    	ubicacion3.setLongitud(-10.0);
    	getSession().save(ubicacion3);
    	
    	
    	//**************************************************** Carga Ciudades
    	Ciudad buenosAires = new Ciudad();
    	buenosAires.setUbicacionGeografica(ubicacion1);
    	getSession().save(buenosAires);
    	
    	Ciudad canberra = new Ciudad();
    	buenosAires.setUbicacionGeografica(ubicacion2);
    	getSession().save(canberra);
    	
    	Ciudad laHabana = new Ciudad();
    	buenosAires.setUbicacionGeografica(ubicacion3);
    	getSession().save(laHabana);
    	
    	//**************************************************** Carga Paises
    	Pais argentina = new Pais();
    	argentina.setCapital(buenosAires);
    	getSession().save(argentina);
    	
    	Pais australia = new Pais();
    	australia.setCapital(canberra);
    	getSession().save(australia);
    	
    	Pais cuba = new Pais();
    	cuba.setCapital(laHabana);
    	getSession().save(cuba);
    	
    	//**************************************************** Criteria
    	Criteria query = getSession().createCriteria(Ciudad.class)
				.createAlias("ubicacionGeografica", "ubic")
				.add(Restrictions.lt("ubic.longitud", 0.0));
    	
    	assertThat(query.list()).hasSize(1);
    	}	
}
