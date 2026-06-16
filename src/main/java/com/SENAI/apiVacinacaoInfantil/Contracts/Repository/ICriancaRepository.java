package com.SENAI.apiVacinacaoInfantil.Contracts.Repository;

import com.SENAI.apiVacinacaoInfantil.DTOs.Aplicacao_VacinaDTO;
import com.SENAI.apiVacinacaoInfantil.DTOs.CarteiraVacinacaoCriancaDTO;
import com.SENAI.apiVacinacaoInfantil.Entities.Crianca;

public interface ICriancaRepository {

    void inserirCriancaNoBanco(Crianca crianca);

    CarteiraVacinacaoCriancaDTO buscarCarteiraVacNoBanco(String matriculaCertidao);

    void inserirVacinaNaCarteira(String matriculaCertidao, Aplicacao_VacinaDTO dto);
}