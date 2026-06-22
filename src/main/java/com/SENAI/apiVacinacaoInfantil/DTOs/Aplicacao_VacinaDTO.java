package com.SENAI.apiVacinacaoInfantil.DTOs;

import java.time.LocalDate;

public class Aplicacao_VacinaDTO {

    private String dose;
    private LocalDate dt_aplicacao;
    private String nome_vacina;
    private int numero_lote;
    private LocalDate validade;


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

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }
//Imprime as vacinas e seus atributos
    @Override
    public String toString() {
        // Formata as datas para o padrão brasileiro
        String dataFormatada = dt_aplicacao != null ?
                dt_aplicacao.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A";

        String validadeFormatada = validade != null ?
                validade.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A";

        return """
        ┌─────────────────────────
        │ Vacina: %s
        │ Dose: %s
        │ Data da aplicação: %s
        │ Lote: %d
        │ Validade: %s
        └─────────────────────────
    """.formatted(nome_vacina, dose, dataFormatada, numero_lote, validadeFormatada);
    }
}