package com.SENAI.apiVacinacaoInfantil.DTOs;

public class VacinaDTO {

    private String nome_vacina;
    private String fabricante;
    private int doses_previstas;

    // Construtor vazio
    public VacinaDTO() {
    }

    // Construtor completo
    public VacinaDTO(String nome_vacina, String fabricante, int doses_previstas) {
        this.nome_vacina = nome_vacina;
        this.fabricante = fabricante;
        this.doses_previstas = doses_previstas;
    }

    // GETTERS E SETTERS
    public String getNome_vacina() {
        return nome_vacina;
    }

    public void setNome_vacina(String nome_vacina) {
        this.nome_vacina = nome_vacina;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public int getDoses_previstas() {
        return doses_previstas;
    }

    public void setDoses_previstas(int doses_previstas) {
        this.doses_previstas = doses_previstas;
    }

    // toString para exibir de forma legível
    @Override
    public String toString() {
        return String.format("%-20s | Fabricante: %-20s | Doses: %d",
                nome_vacina, fabricante, doses_previstas);
    }
}