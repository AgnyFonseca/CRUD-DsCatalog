package com.agnyfonseca.dscatalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.agnyfonseca.dscatalog.dto.ProductDTO;
import com.agnyfonseca.dscatalog.services.ProductService;

@RestController //Indica que a classe é um controlador REST
@RequestMapping(value = "/products") //Rota Rest do recurso, geralmente o nome da rota é no plural
public class ProductResource {
	
	@Autowired //injetando
	private ProductService service; //dependencia do ProductService
	
	//Primeiro Endpoint -GET
	//ReponseEntity = objeto que encapsula uma resposta http, <> generic
	//GetMapping = configura que esse metodo é um Endpoint
	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy
			) {
		//PAGINAÇÃO
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		//service foi chamado, que por sua vez chamou o repository que foi no db,trouxe, instanciou e guardou os obj no list
		Page<ProductDTO> list = service.findAllPaged(pageRequest);  
		
		//.ok() = resposta 200 - .body() = corpo da resposta / cód. 200 SUCESSO - OK
		return ResponseEntity.ok().body(list); //retorno da lista
	}
	
	@GetMapping(value = "/{id}") //criando rota /categories/{id} -GET
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		//@PathVariable está casando o id do param. com o id da rota
		
		ProductDTO dto = service.findById(id); //método findById no Service
		return ResponseEntity.ok().body(dto);
	}
	
	//INSERIR - POST
	@PostMapping
	public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri(); //inserir o location no cabeçalho da resposta 
		return ResponseEntity.created(uri).body(dto); //cód. 201 CRIADO/INSERIDO - CREATED
	}
	
	//ATUALIZAR - PUT
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto) {
		dto = service.update(id, dto);
		
		return ResponseEntity.ok().body(dto);
	}
	
	//DELETAR - DELETE
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build(); //resposta 204 - Sucesso e corpo da resposta vazio - NO CONTENT
	}
}

//CONTROLADOR TEM DEPENDENCIA PRO SERVICE, E O SERVICE PARA O REPOSITORY
