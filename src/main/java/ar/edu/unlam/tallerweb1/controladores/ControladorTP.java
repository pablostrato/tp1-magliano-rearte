package ar.edu.unlam.tallerweb1.controladores;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;

@Controller
public class ControladorTP {

	// La anotacion @Inject indica a Spring que en este atributo se debe setear (inyeccion de dependencias)
	// un objeto de una clase que implemente la interface ServicioLogin, dicha clase debe estar anotada como
	// @Service o @Repository y debe estar en un paquete de los indicados en applicationContext.xml
	@Inject
	private ServicioLogin servicioLogin;

	//PUNTO 6 **
	@RequestMapping(path="/{operacion}/{cadena}", method=RequestMethod.GET)
	public ModelAndView metodoCadena(@PathVariable String operacion, @PathVariable String cadena) {
		
		ModelMap model = new ModelMap();
		
		String resultado = null;
		
		switch(operacion){
		case "pasarAMayuscula":
			resultado = cadena.toUpperCase();
			break;
			
		case "pasarAMinuscula":
			resultado = cadena.toLowerCase();
			break;
			
		case "invertirOrden":
			StringBuilder invertir = new StringBuilder(cadena);
			resultado = invertir.reverse().toString();
			break;
			
		case "cantidadDeCaracteres":
			resultado = String.valueOf(cadena.length());
			break;
			
		default:
		}
		
		model.put("operacionModel", operacion);
		model.put("cadenaModel", cadena);
		model.put("resultadoModel",resultado);
		
		return new ModelAndView("visualizarCadena", model);
	}
}
