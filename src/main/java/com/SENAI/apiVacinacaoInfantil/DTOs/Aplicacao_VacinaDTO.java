package com.SENAI.apiVacinacaoInfantil.DTOs;

import java.time.LocalDate;

public class Aplicacao_VacinaDTO {

    private String dose;
    private LocalDate dt_aplicacao;
    private String nome_vacina;
    private int numero_lote;

    public String getNome_vacina() {
        return nome_vacina;
    }

    public void setNome_vacina(String nome_vacina) {
        this.nome_vacina = nome_vacina;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public LocalDate getDt_aplicacao() {
        return dt_aplicacao;
    }

    public void setDt_aplicacao(LocalDate dt_aplicacao) {
        this.dt_aplicacao = dt_aplicacao;
    }

    public int getNumero_lote() {
        return numero_lote;
    }

    public void setNumero_lote(int numero_lote) {
        this.numero_lote = numero_lote;
    }

    //imprimir a vacina e seus atributos
    @Override
    public String toString() {
        return """
        ┌─────────────────────────
        │ Vacina: %s
        │ Dose: %s
        │ Data aplicação: %s
        │ Lote: %s
        └─────────────────────────
        """.formatted(nome_vacina,dose, dt_aplicacao,numero_lote);
    }
}
