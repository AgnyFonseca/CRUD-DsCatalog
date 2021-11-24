package com.agnyfonseca.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agnyfonseca.dscatalog.dto.ProductDTO;
import com.agnyfonseca.dscatalog.entities.Product;
import com.agnyfonseca.dscatalog.repositories.ProductRepository;
import com.agnyfonseca.dscatalog.services.exceptions.DatabaseException;
import com.agnyfonseca.dscatalog.services.exceptions.ResourceNotFoundException;

//Annotation responsável por registrar essa classe como um componente que vai participar
//do sistema de injeção de dependencia automatizado do Spring
//ou seja, quem irá gerenciar as instancias da dependencia do obj tipo ProductService vai ser o Spring 
//@Component @Repository => mesmo significado
@Service  
public class ProductService {
	
	@Autowired //indica uma instancia gerenciada pelo Spring
	private ProductRepository repository; //criando uma dependencia
	
	//Garante a integridade da transação
	//Argumento melhora a perfomance do db, sempre usar em transação de leitura
	//Buscando todos
	@Transactional(readOnly = true) 
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		Page<Product> list = repository.findAll(pageRequest);
		
//		List<ProductDTO> listDto = new ArrayList<>();
//		for (Product cat: list) {
//			listDto.add(new ProductDTO(cat));
//		}
		//transforma em stream, depois faz o map, .collect para então retransforma para lista. Expressão Lambda
		//return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
		
		//Page já é uma stream
		return list.map(x -> new ProductDTO(x));
	}
	
	//Buscando por Id
	@Transactional(readOnly = true) 
	public ProductDTO findById(Long id) {
		//Obj Optional, abordagem para evitar valor nulo, o retorno nunca será um valor nulo
		Optional<Product> obj = repository.findById(id);
		//Product entity = obj.get(); //met. get do Optional obtém o obj dentro do Optional
		Product entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		
		return new ProductDTO(entity, entity.getCategories()); 
	}
	
	//Inserindo novo produto
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		//entity.setName(dto.getName());
		entity = repository.save(entity);
		return new ProductDTO(entity);
		
	}
	
	//Atualizando um produto
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getOne(id);
			//entity.setName(dto.getName());
			entity = repository.save(entity);
			return new ProductDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}
	
	//Deletando um produto
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
}
