package com.SENAI.apiVacinacaoInfantil.Contracts.Repository;

import com.SENAI.apiVacinacaoInfantil.Entities.Responsavel;

public interface IResponsavelRepository {
       // volta o id do responsável para ser adicionado no objeto Criança, para depois mandar o objeto Criança para o Repository cadastrar a criança no banco de dados, pois tem o id_repository na Crianca.
       int inserirResponsavelNoBanco(Responsavel responsavel);
}
