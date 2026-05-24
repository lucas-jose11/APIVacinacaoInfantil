package com.SENAI.apiVacinacaoInfantil.Entities;

import java.time.LocalDate;

public class Crianca {

    private int idCrianca;
    private String nome;
    private String matriculaCertidao;
    private LocalDate dataNascimento;

    // Associação
    private Responsavel responsavel;


    // Construtor vazio
    public Crianca() {

    }

    // Construtor completo
    public Crianca(
            int idCrianca,
            String nome,
            String matriculaCertidao,
            LocalDate dataNascimento,
            Responsavel responsavel
            ) {

        this.idCrianca = idCrianca;
        this.nome = nome;
        this.matriculaCertidao = matriculaCertidao;
        this.dataNascimento = dataNascimento;
        this.responsavel = responsavel;
    }


    // GETTERS E SETTERS

    public int getIdCrianca() {
        return idCrianca;
    }

    public void setIdCrianca(int idCrianca) {
        this.idCrianca = idCrianca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatriculaCertidao() {
        return matriculaCertidao;
    }

    public void setMatriculaCertidao(
            String matriculaCertidao) {

        this.matriculaCertidao =
                matriculaCertidao;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(
            LocalDate dataNascimento) {

        this.dataNascimento =
                dataNascimento;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public int getIdResponsavel() {
        return responsavel.getIdResponsavel();
    }
}