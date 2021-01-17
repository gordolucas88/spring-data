package com.spring.data.regesc.repository;

import com.spring.data.regesc.orm.Professor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Informa que eh um repositorio
public interface ProfessorRepository extends CrudRepository<Professor, Long> {

    //Native SQL
    @Query(nativeQuery = true,
            value = "SELECT * FROM professores p INNER JOIN disciplinas d ON p.id = d.professor_id WHERE p.nome like :nomeProfessor% AND d.nome like :nomeDisciplina% ")
    List<Professor> findProfessorAtribuido(String nomeProfessor, String nomeDisciplina);


}
