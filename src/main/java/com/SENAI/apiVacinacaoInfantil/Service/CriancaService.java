package com.SENAI.apiVacinacaoInfantil.Service;

import com.SENAI.apiVacinacaoInfantil.Contracts.Repository.ICriancaRepository;
import com.SENAI.apiVacinacaoInfantil.Contracts.Service.ICriancaService;
import com.SENAI.apiVacinacaoInfantil.DTOs.CriancaDTO;
import com.SENAI.apiVacinacaoInfantil.Entities.Crianca;
import com.SENAI.apiVacinacaoInfantil.Entities.Responsavel;
import com.SENAI.apiVacinacaoInfantil.Repository.CriancaRepository;

public class CriancaService
        implements ICriancaService {

    private ICriancaRepository repository =
            new CriancaRepository();


    public void cadastrar(
            CriancaDTO dto) {

        Responsavel Responsavel = new Responsavel();
        Responsavel.setNome(
                dto.getResponsavel().getNome()
        );

        Responsavel.setCpf(
                dto.getResponsavel().getCpf()
        );

        Responsavel.setTelefone(
                dto.getResponsavel().getTelefone()
        );

        Crianca crianca =
                new Crianca();

        crianca.setNome(
                dto.getNome()
        );

        crianca.setMatriculaCertidao(
                dto.getMatriculaCertidao()
        );

        crianca.setDataNascimento(
                dto.getDataNascimento()
        );

        crianca.setResponsavel(Responsavel);

        repository.salvar(crianca);
    }


}