package com.spring.data.regesc.service;

import com.spring.data.regesc.orm.Aluno;
import com.spring.data.regesc.orm.Professor;
import com.spring.data.regesc.repository.AlunoRepository;
import com.spring.data.regesc.repository.ProfessorRepository;
import javassist.compiler.ast.Symbol;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class RelatorioService {
    private AlunoRepository alunoRepository;
    private ProfessorRepository professorRepository;

    public RelatorioService(AlunoRepository alunoRepository, ProfessorRepository professorRepository) {
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
    }

    public void menu(Scanner scanner){
        Boolean isTrue = true;

        while (isTrue) {
            System.out.println("\nQual relatorio voce deseja?");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1 - Alunos por Nome");
            System.out.println("2 - Alunos por nome e idade <=");
            System.out.println("3 - Alunos por nome e idade >=");
            System.out.println("4 - Alunos matriculados nome e idade >=");
            System.out.println("5 - Professores Atribuidos");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    this.alunosPorNome(scanner);
                    break;
                case 2:
                    this.alunosPorNomeIdadeMenorIgual(scanner);
                    break;
                case 3:
                    this.alunosPorNomeIdadeMaiorIgual(scanner);
                    break;
                case 4:
                    this.alunosMatriculadosNomeIdadeMaiorIgual(scanner);
                    break;
                case 5:
                    this.professoresAtribuidos(scanner);
                    break;
                default:
                    isTrue = false;
                    break;

            }
        }
        System.out.println();

    }


    private  void alunosPorNome(Scanner scanner){
        System.out.print("Nome: ");
        String nome = scanner.next();

        List<Aluno> alunos = this.alunoRepository.findByNomeContaining(nome);
        for(Aluno aluno : alunos){
            System.out.println(aluno);
        }
    }

    private  void alunosPorNomeIdadeMenorIgual(Scanner scanner){
        System.out.print("Nome: ");
        String nome = scanner.next();

        System.out.println("Idade: ");
        Integer idade = scanner.nextInt();

        List<Aluno> alunos = this.alunoRepository.findByNomeStartingWithAndIdadeLessThanEqual(nome, idade);
        for(Aluno aluno : alunos){
            System.out.println(aluno);
        }
    }

    private  void alunosPorNomeIdadeMaiorIgual(Scanner scanner){
        System.out.print("Nome: ");
        String nome = scanner.next();

        System.out.println("Idade: ");
        Integer idade = scanner.nextInt();

        List<Aluno> alunos = this.alunoRepository.findNomeIdadeIgualOuMaior(nome, idade);
        for(Aluno aluno : alunos){
            System.out.println(aluno);
        }
    }

    private  void alunosMatriculadosNomeIdadeMaiorIgual(Scanner scanner){
        System.out.print("Nome: ");
        String nomeAluno = scanner.next();

        System.out.println("Idade: ");
        Integer idadeAluno = scanner.nextInt();

        System.out.println("Disciplina: ");
        String nomeDisciplina = scanner.next();

        List<Aluno> alunos = this.alunoRepository.findNomeIdadeIgualOuMaiorMatriculado(nomeAluno, idadeAluno, nomeDisciplina );
        for(Aluno aluno : alunos){
            System.out.println(aluno);
        }
    }

    private  void professoresAtribuidos(Scanner scanner){
        System.out.print("Nome: ");
        String nomeProfessor = scanner.next();

        System.out.println("Disciplina: ");
        String nomeDisciplina = scanner.next();

        List<Professor> professores = this.professorRepository.findProfessorAtribuido(nomeProfessor, nomeDisciplina);
        for(Professor professor : professores){
            System.out.println(professores);
        }
    }
}
