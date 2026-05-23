package com.SENAI.apiVacinacaoInfantil.Entities;

public class Responsavel {

    private int idResponsavel;
    private String nome;
    private String cpf;
    private String telefone;

    // Construtor vazio
    public Responsavel() {

    }

    // Construtor completo
    public Responsavel(
            int idResponsavel,
            String nome,
            String cpf,
            String telefone) {

        this.idResponsavel = idResponsavel;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    // GETTERS E SETTERS

    public int getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(int idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}