package com.spring.data.regesc.service;


import com.spring.data.regesc.orm.Disciplina;
import com.spring.data.regesc.orm.Professor;
import com.spring.data.regesc.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Scanner;

@Service
@Transactional
public class CrudProfessorService {

    private ProfessorRepository professorRepository;

    public  CrudProfessorService(ProfessorRepository professorRepository){
        this.professorRepository = professorRepository;
    }

    public  void menu(Scanner scanner) {
        Boolean isTrue = true;

            while (isTrue) {
                System.out.println("Qual ação você quer executar?");
                System.out.println("0 - Voltar ao menu anterior");
                System.out.println("1 - Cadastrar novo Professor");
                System.out.println("2 - Atualizar Professor");
                System.out.println("3 - Listar Professores");
                System.out.println("4 - Remove um Professor");
                System.out.println("5 - Visualizar um Professor");

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
                        this.ListarUm(scanner);
                        break;
                    default:
                        isTrue = false;
                        break;

                }
            }
            System.out.println();

    }

    private void Cadastrar(Scanner scanner){
        System.out.print("Digite o nome do professor:");
        String nome = scanner.next();

        System.out.print("Digite o prontuario do professor:");
        String prontuario = scanner.next();

        Professor professor = new Professor();
        professor.setNome(nome);
        professor.setProntuario(prontuario);
        this.professorRepository.save(professor);
        System.out.println("Professor salvo no Banco!!! \n");
    }

    private void Atualizar (Scanner scanner){
        System.out.print("Digite o Id do Professor a ser atualizado: ");
        Long id = scanner.nextLong();

        Optional<Professor> optional = this.professorRepository.findById(id);

        if (optional.isPresent()){
            Professor professor = optional.get();
            System.out.print("Digite o nome do professor:");
            String nome = scanner.next();

            System.out.print("Digite o prontuario do professor:");
            String prontuario = scanner.next();

            professor.setNome(nome);
            professor.setProntuario(prontuario);
            professorRepository.save(professor);

            System.out.println("Professor atualizado com sucesso");
        } else {
            System.out.println(" o Id: " + id + "nao foi encontrado");
        }

    }

    private void  AtualizarSemFindById(Scanner scanner) {
        System.out.print("Digite o Id do Professor a ser atualizado: ");
        Long id = scanner.nextLong();

        System.out.print("Digite o nome do professor:");
        String nome = scanner.next();

        System.out.print("Digite o prontuario do professor:");
        String prontuario = scanner.next();

            Professor professor = new Professor();


            professor.setId(id);
            professor.setNome(nome);
            professor.setProntuario(prontuario);
            professorRepository.save(professor);

            System.out.println("Professor atualizado com sucesso");
            System.out.println(" o Id: " + id + "nao foi encontrado");

    }

    private void Listar(){
        Iterable<Professor> professores = this.professorRepository.findAll();
        professores.forEach(professor -> {
            System.out.println(professor);
        });
        System.out.println();
    }

    private void ListarUm(Scanner scanner){
        System.out.println("Digite o Id do Professor:");
        long id = scanner.nextLong();

        Optional<Professor> optional = professorRepository.findById(id);
        if(optional.isPresent()){
            Professor professor = optional.get();

            System.out.println("Professor: {");
            System.out.println("Id: " + professor.getId());
            System.out.println("Nome: " + professor.getNome());
            System.out.println("Prontuario: " + professor.getProntuario());
            System.out.println("Disciplinas: [");
            for (Disciplina disciplina : professor.getDisciplinas()){
                System.out.println("\tId: " + disciplina.getId());
                System.out.println("\tNome: " + disciplina.getNome());
                System.out.println("\tSemestre: " + disciplina.getSemestre());
            }
            System.out.println("]\n");
        }
        else {
            System.out.println("Professor Inexistente");
        }
    }
    private void Remover(Scanner scanner){
        System.out.println("Digite o Id do Professor para ser excluido");
        Long id = scanner.nextLong();
        Optional<Professor> optional = professorRepository.findById(id);
        if (optional.isPresent()){
            professorRepository.deleteById(id);
            System.out.println("Professor Excluido");
        } else {
            System.out.println("Professor nao existe");
        }

        System.out.println();

    }
}
