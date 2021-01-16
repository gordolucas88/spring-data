package com.spring.data.regesc.service;

import com.spring.data.regesc.orm.Aluno;
import com.spring.data.regesc.orm.Disciplina;
import com.spring.data.regesc.orm.Professor;
import com.spring.data.regesc.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudAlunoService {

    private AlunoRepository alunoRepository;

    public CrudAlunoService(AlunoRepository alunoRepository){
        this.alunoRepository = alunoRepository;

    }

    @Transactional
    public void menu(Scanner scanner){
        Boolean isTrue = true;

        while (isTrue) {
            System.out.println("\nQual acao voce quer executar?");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1 - Cadastrar novo Aluno");
            System.out.println("2 - Atualizar um Aluno");
            System.out.println("3 - Visualizar Alunos");
            System.out.println("4 - Deletar Aluno");
            System.out.println("5 - Vizualizar um Aluno");

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
        System.out.print("Digite o nome do Aluno:");
        String nome = scanner.next();

        System.out.print("Digite a idade do Aluno:");
        Integer idade = scanner.nextInt();

        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setIdade(idade);
        this.alunoRepository.save(aluno);
        System.out.println("aluno salvo no Banco!!! \n");
    }

    private void Atualizar (Scanner scanner){
        System.out.print("Digite o Id do Aluno a ser atualizado: ");
        Long id = scanner.nextLong();

        Optional<Aluno> optional = this.alunoRepository.findById(id);

        if (optional.isPresent()){
            Aluno aluno = optional.get();
            System.out.print("Digite o nome do Aluno:");
            String nome = scanner.next();

            System.out.print("Digite a idade do Aluno:");
            Integer idade = scanner.nextInt();

            aluno.setNome(nome);
            aluno.setIdade(idade);
            alunoRepository.save(aluno);

            System.out.println("Aluno atualizado com sucesso");
        } else {
            System.out.println(" o Id: " + id + "nao foi encontrado");
        }

    }

    private void  AtualizarSemFindById(Scanner scanner) {
        System.out.print("Digite o Id do Aluno a ser atualizado: ");
        Long id = scanner.nextLong();

        System.out.print("Digite o nome do aluno:");
        String nome = scanner.next();

        System.out.print("Digite a idade do Aluno:");
        Integer idade = scanner.nextInt();

        Aluno aluno = new Aluno();


        aluno.setId(id);
        aluno.setNome(nome);
        aluno.setIdade(idade);
        alunoRepository.save(aluno);

        System.out.println("Aluno atualizado com sucesso");
        System.out.println(" o Id: " + id + "nao foi encontrado");

    }

    private void Listar(){
        Iterable<Aluno> alunos = this.alunoRepository.findAll();
        alunos.forEach(System.out::println);
        System.out.println();
    }
    @Transactional
    private void ListarUm(Scanner scanner){
        System.out.println("Digite o Id do Aluno:");
        long id = scanner.nextLong();

        Optional<Aluno> optional = alunoRepository.findById(id);
        if(optional.isPresent()){
            Aluno aluno = optional.get();

            System.out.println("Aluno: {");
            System.out.println("Id: " + aluno.getId());
            System.out.println("Nome: " + aluno.getNome());
            System.out.println("Idade: " + aluno.getIdade());
            System.out.println("Disciplinas: [");
            if(aluno.getDisciplinas() != null){
                for (Disciplina disciplina : aluno.getDisciplinas()){
                    System.out.println("\tId: " + disciplina.getId());
                    System.out.println("\tNome: " + disciplina.getNome());
                    System.out.println("\tSemestre: " + disciplina.getSemestre());
                }
                System.out.println("]\n");
            }
            }

        else {
            System.out.println("Aluno Inexistente");
        }
    }
    private void Remover(Scanner scanner){
        System.out.println("Digite o Id do Aluno para ser excluido");
        Long id = scanner.nextLong();
        this.alunoRepository.deleteById(id);

        System.out.println("Aluno Excluido");

    }


}
