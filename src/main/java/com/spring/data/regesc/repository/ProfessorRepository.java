package com.spring.data.regesc.repository;

import com.spring.data.regesc.orm.Professor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository // Informa que eh um repositorio
public interface ProfessorRepository extends CrudRepository<Professor, Long> {


}
