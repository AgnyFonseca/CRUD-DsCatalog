package com.agnyfonseca.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agnyfonseca.dscatalog.dto.CategoryDTO;
import com.agnyfonseca.dscatalog.entities.Category;
import com.agnyfonseca.dscatalog.repositories.CategoryRepository;

import com.agnyfonseca.dscatalog.services.exceptions.EntityNotFoundException;

//Annotation responsável por registrar essa classe como um componente que vai participar
//do sistema de injeção de dependencia automatizado do Spring
//ou seja, quem irá gerenciar as instancias da dependencia do obj tipo CategoryService vai ser o Spring 
//@Component @Repository => mesmo significado
@Service  
public class CategoryService {
	
	@Autowired //indica uma instancia gerenciada pelo Spring
	private CategoryRepository repository; //criando uma dependencia
	
	//Garante a integridade da transação
	//Argumento melhora a perfomance do db, sempre usar em transação de leitura
	//Buscando todos
	@Transactional(readOnly = true) 
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll();
		
//		List<CategoryDTO> listDto = new ArrayList<>();
//		for (Category cat: list) {
//			listDto.add(new CategoryDTO(cat));
//		}
		
		//transforma em stream, depois faz o map, .collect para então retransforma para lista. Expressão Lambda
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}
	
	//Buscando po Id
	@Transactional(readOnly = true) 
	public CategoryDTO findById(Long id) {
		//Obj Optional, abordagem para evitar valor nulo, o retorno nunca será um valor nulo
		Optional<Category> obj = repository.findById(id);
		//Category entity = obj.get(); //met. get do Optional obtém o obj dentro do Optional
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		
		return new CategoryDTO(entity); 
	}
	
	//Inserindo novo produto
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
		
	}
}
