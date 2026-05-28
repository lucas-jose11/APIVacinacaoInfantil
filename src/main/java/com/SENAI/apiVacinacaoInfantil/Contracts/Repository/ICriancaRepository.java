package com.SENAI.apiVacinacaoInfantil.Contracts.Repository;

import com.SENAI.apiVacinacaoInfantil.Entities.Crianca;

public interface ICriancaRepository {

    void inserirCriancaNoBanco(Crianca crianca);

    Crianca buscarCriancaNoBanco(String matriculaCertidao);
}