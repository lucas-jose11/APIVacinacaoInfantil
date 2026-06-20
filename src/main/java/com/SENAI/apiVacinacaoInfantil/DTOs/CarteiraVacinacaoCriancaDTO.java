package com.SENAI.apiVacinacaoInfantil.DTOs;

import java.time.LocalDate;
import java.util.List;

public class CarteiraVacinacaoCriancaDTO {

    private String nome_crianca;
    private String matricula_cert;
    private LocalDate data_nascimento;
    private String nome_vacina;
    private int numero_lote;
    private LocalDate validade;
    private List<Aplicacao_VacinaDTO> vacinas = new java.util.ArrayList<>();

    public String getNome_crianca() {
        return nome_crianca;
    }

    public void setNome_crianca(String nome_crianca) {
        this.nome_crianca = nome_crianca;
    }

    public String getMatricula_cert() {
        return matricula_cert;
    }

    public void setMatricula_cert(String matricula_cert) {
        this.matricula_cert = matricula_cert;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getNome_vacina() {
        return nome_vacina;
    }

    public void setNome_vacina(String nome_vacina) {
        this.nome_vacina = nome_vacina;
    }

    public int getNumero_lote() {return numero_lote;}

    public void setNumero_lote(int numero_lote) {this.numero_lote = numero_lote;}

    public LocalDate getValidade() {return validade;}

    public void setValidade(LocalDate validade) {this.validade = validade;}

    public List<Aplicacao_VacinaDTO> getVacinas() {
        return vacinas;
    }

    public void setVacinas(List<Aplicacao_VacinaDTO> vacinas) {
        this.vacinas = vacinas;
    }
}
