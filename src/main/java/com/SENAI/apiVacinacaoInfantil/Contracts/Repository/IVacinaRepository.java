package com.SENAI.apiVacinacaoInfantil.Contracts.Repository;

import com.SENAI.apiVacinacaoInfantil.DTOs.VacinaDTO;

import java.util.List;

public interface IVacinaRepository {
    List<VacinaDTO> buscarTodasAsVacinas();
}
