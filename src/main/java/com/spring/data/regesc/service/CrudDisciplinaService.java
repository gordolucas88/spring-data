package com.spring.data.regesc.service;

import com.spring.data.regesc.orm.Disciplina;
import com.spring.data.regesc.orm.Professor;
import com.spring.data.regesc.repository.DisciplinaRepository;
import com.spring.data.regesc.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudDisciplinaService {
private DisciplinaRepository disciplinaRepository;
private ProfessorRepository professorRepository;


    public  CrudDisciplinaService(DisciplinaRepository disciplinaRepository,
                                  ProfessorRepository professorRepository){
        this.disciplinaRepository = disciplinaRepository;
        this.professorRepository = professorRepository;
    }

    public  void menu(Scanner scanner) {
        Boolean isTrue = true;

        while (isTrue) {
            System.out.println("Qual ação você quer executar?");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1 - Cadastrar nova Disciplina");
            System.out.println("2 - Atualizar Disciplina");
            System.out.println("3 - Listar Disciplina");
            System.out.println("4 - Remove uma Disciplina");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    this.Cadastrar(scanner);
                    break;
                case 2:
                    this.Atualizar(scanner);
                case 3:
                    this.Listar();
                case 4:
                    this.Remover(scanner);
                default:
                    isTrue = false;
                    break;

            }
        }
        System.out.println();

    }

    private void Cadastrar(Scanner scanner){
        System.out.print("Digite o nome da Disciplina:");
        String nome = scanner.next();

        System.out.print("Digite o Semestre:");
        Integer semestre = scanner.nextInt();

        System.out.print("Professor ID:");
        Long professorID = scanner.nextLong();

        Optional<Professor> optional = professorRepository.findById(professorID);
        if(optional.isPresent()){
            Professor professor = optional.get();
            Disciplina disciplina = new Disciplina(nome,semestre,professor);
            disciplinaRepository.save(disciplina);
            System.out.println("Cadastrada disciplina \n");
        } else {
            System.out.println("Professor ID: " + professorID + "não existe");
        }

    }

    private void Atualizar (Scanner scanner){
        System.out.print("Digite o Id do da Disciplina a ser atualizado: ");
        Long id = scanner.nextLong();
        Optional<Disciplina> optionalDisciplina = this .disciplinaRepository.findById(id);


        if (optionalDisciplina.isPresent()){
            Disciplina disciplina = optionalDisciplina.get();

            System.out.print("Digite o nome da Disciplina:");
            String nome = scanner.next();

            System.out.print("Digite o semestre da Disciplina:");
            Integer semestre = scanner.nextInt();

            System.out.print("Digite o ID do Professor:");
            Integer professorId = scanner.nextInt();

            Optional<Professor> optionalProfessor = this.professorRepository.findById(id);
            if(optionalProfessor.isPresent()){
                Professor professor = optionalProfessor.get();
                disciplina.setNome(nome);
                disciplina.setSemestre(semestre);
                disciplina.setProfessor(professor);

                disciplinaRepository.save(disciplina);

                System.out.println("atualizado\n");
            } else {
                System.out.println("Professor Id: " + professorId + "Não cadastrado");
            }



        } else {
            System.out.println(" o Id: " + id + "nao foi encontrado");
        }

    }

    private void Listar(){
        Iterable<Disciplina> disciplinas = this.disciplinaRepository.findAll();
//        for(Disciplina disciplina : disciplinas){
//            System.out.println(disciplinas);
//        }
        disciplinas.forEach(disciplina -> {
            System.out.println(disciplina);
        });
        System.out.println();
    }

    private void Remover(Scanner scanner){
        System.out.println("Digite o Id da Disciplina para ser excluido");
        Long id = scanner.nextLong();
        this.disciplinaRepository.deleteById(id);

        System.out.println("Disciplina Excluida");

    }
}
