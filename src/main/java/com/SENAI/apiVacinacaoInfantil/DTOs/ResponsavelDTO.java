package com.SENAI.apiVacinacaoInfantil.DTOs;

public class ResponsavelDTO {

    private String nome;
    private String cpf;
    private String telefone;

    // Construtor vazio
    public ResponsavelDTO() {

    }

    // Construtor completo
    public ResponsavelDTO(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    // GETTERS E SETTERS
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