package com.spring.data.regesc.repository;

import com.spring.data.regesc.orm.Aluno;
import org.springframework.data.repository.CrudRepository;

public interface AlunoRepository extends CrudRepository<Aluno, Long> {
}
