package com.agnyfonseca.dscatalog.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agnyfonseca.dscatalog.entities.Category;

@RestController //Indica que a classe é um controlador Rest
@RequestMapping(value = "/categories") //Rota Rest do recurso, geralmente o nome da rota é no plural
public class CategoryResource {
	
	//Primeiro Endpoint
	//ReponseEntity = objeto que encapsula uma resposta http, <> generic
	//GetMapping = configura que esse metodo é um Endpoint
	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		//Lista de Category
		List<Category> list = new ArrayList<>();
		//Adicionando valores à lista
		list.add(new Category(1L, "Books"));
		list.add(new Category(2L, "Electronics"));
		
		//.ok() = resposta 200 - .body() = corpo da resposta
		return ResponseEntity.ok().body(list);
	}
}
