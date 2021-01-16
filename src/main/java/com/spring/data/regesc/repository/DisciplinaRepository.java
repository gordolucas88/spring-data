package com.spring.data.regesc.repository;

import com.spring.data.regesc.orm.Disciplina;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository extends CrudRepository<Disciplina, Long> {
}
