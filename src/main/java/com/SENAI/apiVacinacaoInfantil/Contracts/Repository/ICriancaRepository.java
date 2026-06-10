package com.SENAI.apiVacinacaoInfantil.Contracts.Repository;

import com.SENAI.apiVacinacaoInfantil.DTOs.CarteiraVacinacaoCriancaDTO;
import com.SENAI.apiVacinacaoInfantil.Entities.Crianca;

public interface ICriancaRepository {

    void inserirCriancaNoBanco(Crianca crianca);

    CarteiraVacinacaoCriancaDTO buscarCarteiraVacNoBanco(String matriculaCertidao);
}