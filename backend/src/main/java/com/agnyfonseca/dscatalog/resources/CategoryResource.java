package com.agnyfonseca.dscatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agnyfonseca.dscatalog.dto.CategoryDTO;
import com.agnyfonseca.dscatalog.services.CategoryService;

@RestController //Indica que a classe é um controlador Rest
@RequestMapping(value = "/categories") //Rota Rest do recurso, geralmente o nome da rota é no plural
public class CategoryResource {
	
	@Autowired //injetando
	private CategoryService service; //dependencia do CategoryService
	
	//Primeiro Endpoint
	//ReponseEntity = objeto que encapsula uma resposta http, <> generic
	//GetMapping = configura que esse metodo é um Endpoint
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		//service foi chamado, que por sua vez chamou o repository que foi no db,trouxe, instanciou e guardou os obj no list
		List<CategoryDTO> list = service.findAll();  
		
		//.ok() = resposta 200 - .body() = corpo da resposta
		return ResponseEntity.ok().body(list); //retorno a lista
	}
}

//CONTROLADOR TEM DEPENDENCIA PRO SERVICE, E O SERVICE PARA O REPOSITORY
