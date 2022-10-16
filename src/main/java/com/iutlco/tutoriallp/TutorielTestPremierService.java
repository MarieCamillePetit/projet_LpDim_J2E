/**
 * 
 */
package com.iutlco.tutoriallp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author antoineoffroy
 *
 */
@RestController
@RequestMapping
public class TutorielTestPremierService {
	
	@GetMapping
	public String getTestPremiereMethod() {
		return "Bravo ! Vous êtes prêts à démarrer le tutoriel :)";	
	}
}
