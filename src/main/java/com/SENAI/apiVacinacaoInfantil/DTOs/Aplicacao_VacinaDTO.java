package com.SENAI.apiVacinacaoInfantil.DTOs;

import java.time.LocalDate;

public class Aplicacao_VacinaDTO {

    private String dose;
    private LocalDate dt_aplicacao;

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
}
