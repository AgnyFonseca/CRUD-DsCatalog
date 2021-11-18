package com.agnyfonseca.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agnyfonseca.dscatalog.entities.Category;
import com.agnyfonseca.dscatalog.repositories.CategoryRepository;

//Annotation responsável por registrar essa classe como um componente que vai participar
//do sistema de injeção de dependencia automatizado do Spring
//ou seja, quem irá gerenciar as instancias da dependencia do obj tipo CategoryService vai ser o Spring 
//@Component @Repository => mesmo significado
@Service  
public class CategoryService {
	
	@Autowired //indica uma instancia gerenciada pelo Spring
	private CategoryRepository repository; //criando uma dependencia
	
	public List<Category> findAll() {
		return repository.findAll();
	}
}
