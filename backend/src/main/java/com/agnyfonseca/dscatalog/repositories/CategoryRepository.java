package com.agnyfonseca.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agnyfonseca.dscatalog.entities.Category;

//Camada de acesso a dados
@Repository //indicando a interface como um componente injetável, passa a ser gerenciado pelo Spring
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
