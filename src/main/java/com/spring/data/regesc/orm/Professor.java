package com.spring.data.regesc.orm;

import javax.persistence.*;

@Entity //Informo que a classe é uma tabela
@Table(name = "professores") //Definindo o nome da tabela, ao inves de usar o nome da classe
public class Professor {
    @Id //Define a coluna como PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Defini que a coluna vai ser tipo Identity
    private  Long id;
    @Column(nullable = false) //nao pode ser nulo
    private  String nome;
    @Column(nullable = false, unique = true) // Define a coluna como Unique
    private  String prontuario;

    @Deprecated // Server para indicar que nao utilizaremos, criado so por obrigacao do hibernate.
    public  Professor() {

    }

    public Professor(String nome, String prontuario) {
        this.nome = nome;
        this.prontuario = prontuario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProntuario() {
        return prontuario;
    }

    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", prontuario='" + prontuario + '\'' +
                '}';
    }
}
