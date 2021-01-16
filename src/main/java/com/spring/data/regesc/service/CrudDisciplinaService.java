package com.spring.data.regesc.service;

import com.spring.data.regesc.orm.Aluno;
import com.spring.data.regesc.orm.Disciplina;
import com.spring.data.regesc.orm.Professor;
import com.spring.data.regesc.repository.AlunoRepository;
import com.spring.data.regesc.repository.DisciplinaRepository;
import com.spring.data.regesc.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudDisciplinaService {
private DisciplinaRepository disciplinaRepository;
private ProfessorRepository professorRepository;
private AlunoRepository alunoRepository;


    public  CrudDisciplinaService(DisciplinaRepository disciplinaRepository,
                                  ProfessorRepository professorRepository,
                                  AlunoRepository alunoRepository){
        this.disciplinaRepository = disciplinaRepository;
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;
    }
    @Transactional
    public  void menu(Scanner scanner) {
        Boolean isTrue = true;

        while (isTrue) {
            System.out.println("Qual ação você quer executar?");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1 - Cadastrar nova Disciplina");
            System.out.println("2 - Atualizar Disciplina");
            System.out.println("3 - Listar Disciplina");
            System.out.println("4 - Remove uma Disciplina");
            System.out.println("5 - Matricular Aluno");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    this.Cadastrar(scanner);
                    break;
                case 2:
                    this.Atualizar(scanner);
                    break;
                case 3:
                    this.Listar();
                    break;
                case 4:
                    this.Remover(scanner);
                    break;
                case 5:
                    this.MatricularAluno(scanner);
                    break;
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
            List<Aluno> alunos = this.Matricular(scanner);

            Disciplina disciplina = new Disciplina(nome,semestre,professor);
            disciplina.setAlunos(alunos);
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

                List<Aluno> alunos = this.Matricular(scanner);

                disciplina.setNome(nome);
                disciplina.setSemestre(semestre);
                disciplina.setProfessor(professor);
                disciplina.setAlunos(alunos);

                disciplinaRepository.save(disciplina);

                System.out.println("atualizado\n");
            } else {
                System.out.println("Professor Id: " + professorId + "Não cadastrado");
            }



        } else {
            System.out.println(" o Id: " + id + "nao foi encontrado");
        }

    }
    @Transactional
    private void Listar(){
        Iterable<Disciplina> disciplinas = this.disciplinaRepository.findAll();

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

    private List<Aluno> Matricular(Scanner scanner) {
        Boolean isTrue = true;
        List<Aluno> alunos = new ArrayList<Aluno>();

        while (isTrue) {
            System.out.print("Id do Aluno a ser matriculado (Digite 0 para sair): ");
            Long alunoId = scanner.nextLong();

            if (alunoId > 0) {

                System.out.println("alunoId: " + alunoId);

                Optional<Aluno> optional = this.alunoRepository.findById(alunoId);


                if (optional.isPresent()) {
                    alunos.add(optional.get());
                } else {
                    System.out.println("O id de aluno " + alunoId + " nao existe");
                }
            }
            else {
                isTrue = false;
            }

        }
        return alunos;
    }

    private void  MatricularAluno(Scanner scanner){
        System.out.println("Digite o Id da Disciplina para matricular alunos :");
        Long id = scanner.nextLong();

        Optional<Disciplina> optionalDisciplina = this.disciplinaRepository.findById(id);

        if (optionalDisciplina.isPresent()){
            Disciplina disciplina = optionalDisciplina.get();
            List<Aluno> novosAlunos = this.Matricular(scanner);
            disciplina.getAlunos().addAll(novosAlunos);
            this.disciplinaRepository.save(disciplina);
        } else {
            System.out.println("O id da disciplina nao existe: " + id);
        }

    }
}
