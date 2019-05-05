package ar.edu.unlam.tallerweb1.persistencia;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Continente;
import ar.edu.unlam.tallerweb1.modelo.Pais;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

// Clase que prueba la conexion a la base de datos. Hereda de SpringTest por lo que corre dentro del contexto
// de spring
public class ConexionBaseDeDatosTest extends SpringTest{

    @Test
    @Transactional @Rollback(true)
    public void pruebaConexion(){
        assertThat(getSession().isConnected()).isTrue();
    }
    
    //2. PAISES DE HABLA INGLESA.
    @Test
    @Transactional  @Rollback(true)
    public void idiomaIngles(){
    	
    	//Se carga el objeto
    	Pais p1 = new Pais();
    	p1.setNombre("Estados Unidos");
    	p1.setIdioma("Ingles");
    	getSession().save(p1);
    	
    	//Criteria
    	Criteria query = getSession().createCriteria(Pais.class)
    			.add(Restrictions.eq("idioma", "Ingles"));
    	
    	assertThat(query.list()).hasSize(1);
	}
    
    @Test
    //3. PAISES UBICADOS EN EUROPA
    @Transactional  @Rollback(true)
    public void paisesEuropa(){
    	
    	//Carga Continentes
    	Continente c1 = new Continente();
    	c1.setNombre("Europa");
    	getSession().save(c1);
    	
    	Continente c2 = new Continente();
    	c2.setNombre("Asia");
    	getSession().save(c2);
    	
    	//Carga Paises
    	Pais p1 = new Pais();
    	p1.setNombre("Holanda");
    	p1.setContinente_r(c1);
    	getSession().save(p1);
    	
    	Pais p2 = new Pais();
    	p2.setNombre("Alemania");
    	p2.setContinente_r(c1);
    	getSession().save(p2);
    	
    	Pais p3 = new Pais();
    	p3.setNombre("Japon");
    	p3.setContinente_r(c2);
    	getSession().save(p3);

    	//Criteria
    	Criteria query = getSession().createCriteria(Pais.class)
				.createAlias("continente_r", "continenteBuscado")
				.add(Restrictions.eq("continenteBuscado.nombre", "Europa"));
    	
    	assertThat(query.list()).hasSize(2);
	}
    
    //4. PAISES CUYA CAPITAL ESTA AL NORTE DEL TROPICO DE CANCER
    
    //5. CIUDADES DEL HEMISFERIO SUR
}
