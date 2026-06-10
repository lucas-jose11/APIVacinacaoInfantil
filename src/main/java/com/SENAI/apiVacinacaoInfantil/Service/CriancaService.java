package com.SENAI.apiVacinacaoInfantil.Service;

import com.SENAI.apiVacinacaoInfantil.Contracts.Repository.ICriancaRepository;
import com.SENAI.apiVacinacaoInfantil.Contracts.Repository.IResponsavelRepository;
import com.SENAI.apiVacinacaoInfantil.Contracts.Service.ICriancaService;
import com.SENAI.apiVacinacaoInfantil.DTOs.CarteiraVacinacaoCriancaDTO;
import com.SENAI.apiVacinacaoInfantil.DTOs.CriancaDTO;
import com.SENAI.apiVacinacaoInfantil.Entities.Crianca;
import com.SENAI.apiVacinacaoInfantil.Entities.Responsavel;
import com.SENAI.apiVacinacaoInfantil.Repository.CriancaRepository;
import com.SENAI.apiVacinacaoInfantil.Repository.ResponsavelRepository;

public class CriancaService implements ICriancaService {



    private ICriancaRepository repositoryCrianca = new CriancaRepository();
    private IResponsavelRepository repositoryResponsavel = new ResponsavelRepository();

    public void cadastrarCrianca(CriancaDTO dto) {
        // Criar o objeto de tipo Entity Responsável a partir da CriancaDTO, que tem o ResponsavelDTO dentro dela
        Responsavel responsavel = new Responsavel();
        responsavel.setNome( dto.getResponsavel().getNome());
        responsavel.setCpf(dto.getResponsavel().getCpf());
        responsavel.setTelefone(dto.getResponsavel().getTelefone() );

        // Criar o objeto de tipo Entity Criança a partir do CriancaDTO
        Crianca crianca = new Crianca();
        crianca.setNome(dto.getNome());
        crianca.setMatriculaCertidao( dto.getMatriculaCertidao() );
        crianca.setDataNascimento(dto.getDataNascimento());
        

        //Mandando o objeto Responsável criado para o Repoisotyr, que irá verificar pelo Cpf se o Responsável já existe no banco de dados, e caso não exista, irá cadastrar o Responsável. Voltará o id do responsável para ser adicionado no objeto Criança, para depois mandar o objeto Criança para o Repository cadastrar a criança no banco de dados, pois tem o id_repository na Crianca.
        responsavel.setIdResponsavel(repositoryResponsavel.inserirResponsavelNoBanco(responsavel));

        // Adicionando o objeto Responsável (com id_responsavel) no objeto Criança
        crianca.setResponsavel(responsavel);

        // Mandando o objeto Criança criado para o Repository salvar no banco de dados
        repositoryCrianca.inserirCriancaNoBanco(crianca);
    }

    public CarteiraVacinacaoCriancaDTO buscarCarteira(String matriculaCrianca) {
        CarteiraVacinacaoCriancaDTO carteiraVacinas = repositoryCrianca.buscarCarteiraVacNoBanco(matriculaCrianca);

        return carteiraVacinas;
    }


}