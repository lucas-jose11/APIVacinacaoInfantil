package com.SENAI.apiVacinacaoInfantil.DTOs;

import java.time.LocalDate;

public class CriancaDTO {
    private String nome;
    private String matriculaCertidao;
    private LocalDate dataNascimento;

    private ResponsavelDTO responsavel;

    // Construtor vazio
    public CriancaDTO() {

    }

    // Construtor completo
    public CriancaDTO(String nome, String matriculaCertidao, LocalDate dataNascimento) {
        this.nome = nome;
        this.matriculaCertidao = matriculaCertidao;
        this.dataNascimento = dataNascimento;
    }

    // GETTERS E SETTERS
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatriculaCertidao() {
        return matriculaCertidao;
    }

    public void setMatriculaCertidao(String matriculaCertidao) {
        this.matriculaCertidao = matriculaCertidao;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public ResponsavelDTO getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(ResponsavelDTO responsavel) {
        this.responsavel = responsavel;
    }

}
