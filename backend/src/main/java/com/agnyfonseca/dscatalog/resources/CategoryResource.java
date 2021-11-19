package com.agnyfonseca.dscatalog.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.agnyfonseca.dscatalog.dto.CategoryDTO;
import com.agnyfonseca.dscatalog.services.CategoryService;

@RestController //Indica que a classe é um controlador REST
@RequestMapping(value = "/categories") //Rota Rest do recurso, geralmente o nome da rota é no plural
public class CategoryResource {
	
	@Autowired //injetando
	private CategoryService service; //dependencia do CategoryService
	
	//Primeiro Endpoint -GET
	//ReponseEntity = objeto que encapsula uma resposta http, <> generic
	//GetMapping = configura que esse metodo é um Endpoint
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		//service foi chamado, que por sua vez chamou o repository que foi no db,trouxe, instanciou e guardou os obj no list
		List<CategoryDTO> list = service.findAll();  
		
		//.ok() = resposta 200 - .body() = corpo da resposta / cód. 200 SUCESSO
		return ResponseEntity.ok().body(list); //retorno da lista
	}
	
	@GetMapping(value = "/{id}") //criando rota /categories/{id} -GET
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
		//@PathVariable está casando o id do param. com o id da rota
		
		CategoryDTO dto = service.findById(id); //método findById no Service
		return ResponseEntity.ok().body(dto);
	}
	
	//INSERIR - POST
	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri(); //inserir o location no cabeçalho da resposta 
		return ResponseEntity.created(uri).body(dto); //cód. 201 CRIADO/INSERIDO
	}
}

//CONTROLADOR TEM DEPENDENCIA PRO SERVICE, E O SERVICE PARA O REPOSITORY
