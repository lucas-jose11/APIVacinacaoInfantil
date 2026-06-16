package com.SENAI.apiVacinacaoInfantil.Repository;

import com.SENAI.apiVacinacaoInfantil.Contracts.Repository.ICriancaRepository;
import com.SENAI.apiVacinacaoInfantil.DTOs.Aplicacao_VacinaDTO;
import com.SENAI.apiVacinacaoInfantil.DTOs.CarteiraVacinacaoCriancaDTO;
import com.SENAI.apiVacinacaoInfantil.Entities.Crianca;
import com.SENAI.apiVacinacaoInfantil.Entities.Responsavel;
import com.SENAI.apiVacinacaoInfantil.Infrastructure.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class CriancaRepository
        implements ICriancaRepository {


    @Override
    public void inserirCriancaNoBanco(Crianca crianca) {

        String sql =
                "INSERT INTO Crianca " +
                        "(nome, matricula_certidao, data_nascimento, id_responsavel) " +
                        "VALUES (?, ?, ?, ?)";

        try (
                Connection conn = DatabaseConnection.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, crianca.getNome());
            stmt.setString(2, crianca.getMatriculaCertidao());
            stmt.setDate(3, java.sql.Date.valueOf(crianca.getDataNascimento()));
            // Agora usa o ID do responsável
            stmt.setInt(4, crianca.getIdResponsavel());

            stmt.executeUpdate();

            System.out.println("Cadastro salvo no banco!");

        } catch (SQLIntegrityConstraintViolationException e) {

            System.out.println("\nERRO: Já existe uma criança cadastrada com essa matrícula da certidão!");

        } catch (Exception e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public CarteiraVacinacaoCriancaDTO buscarCarteiraVacNoBanco(String matriculaCertidao) {

        // Consulta SQL para localizar a criança pela matrícula da certidão
        String sqlCrianca = "SELECT * FROM Crianca WHERE matricula_certidao = ?";

        // Consulta SQL para buscar todas as vacinas aplicadas na criança
        // utilizando o id_crianca como chave estrangeira
        String sqlVacinas =
                "SELECT v.nome_vacina, av.dose, av.data_aplicacao " +
                        "FROM Aplicacao_Vacina av " +
                        "INNER JOIN Lote l ON av.id_lote = l.id_lote " +
                        "INNER JOIN Vacina v ON l.id_vacina = v.id_vacina " +
                        "WHERE av.id_crianca = ?";

        // DTO que armazenará todas as informações da carteira de vacinação
        CarteiraVacinacaoCriancaDTO carteiraEncontrada = null;

        // Variável para guardar o ID da criança encontrado no banco
        int idCriancaEncontrada = 0;

        try (Connection conn = DatabaseConnection.conectar()) {

            // =====================================================
            // ETAPA 1 - BUSCAR DADOS DA CRIANÇA
            // =====================================================
            try (PreparedStatement stmtCrianca = conn.prepareStatement(sqlCrianca)) {

                // Substitui o '?' da consulta pela matrícula informada
                stmtCrianca.setString(1, matriculaCertidao);

                try (ResultSet rsCrianca = stmtCrianca.executeQuery()) {

                    // Verifica se encontrou algum registro
                    if (rsCrianca.next()) {

                        // Instancia o DTO que representará a carteira
                        carteiraEncontrada = new CarteiraVacinacaoCriancaDTO();

                        // Preenche os dados básicos da criança
                        carteiraEncontrada.setNome_crianca(
                                rsCrianca.getString("nome"));

                        carteiraEncontrada.setMatricula_cert(
                                rsCrianca.getString("matricula_certidao"));

                        carteiraEncontrada.setData_nascimento(
                                rsCrianca.getDate("data_nascimento")
                                        .toLocalDate());

                        // Guarda o ID da criança para buscar as vacinas depois
                        idCriancaEncontrada =
                                rsCrianca.getInt("id_crianca");
                    }
                }
            }

            // =====================================================
            // ETAPA 2 - BUSCAR VACINAS DA CRIANÇA
            // =====================================================
            // Só executa se a criança foi encontrada
            if (carteiraEncontrada != null && idCriancaEncontrada > 0) {

                // Lista que armazenará todas as doses encontradas
                java.util.List<Aplicacao_VacinaDTO> listaDeDoses =
                        new java.util.ArrayList<>();

                try (PreparedStatement stmtVacinas =
                             conn.prepareStatement(sqlVacinas)) {

                    // Substitui o '?' da consulta pelo ID da criança
                    stmtVacinas.setInt(1, idCriancaEncontrada);

                    try (ResultSet rsVacinas =
                                 stmtVacinas.executeQuery()) {

                        // Percorre todas as vacinas encontradas
                        while (rsVacinas.next()) {

                            // Cria um DTO para cada vacina encontrada
                            Aplicacao_VacinaDTO dose =
                                    new Aplicacao_VacinaDTO();

                            // Preenche o nome da dose
                            dose.setDose(
                                    rsVacinas.getString("dose"));

                            // Verifica se existe data cadastrada
                            if (rsVacinas.getDate("data_aplicacao")
                                    != null) {

                                // Converte java.sql.Date para LocalDate
                                dose.setDt_aplicacao(
                                        rsVacinas.getDate("data_aplicacao")
                                                .toLocalDate());
                            }

                            //obtem o nome da vacina
                            String nomeVacina = rsVacinas.getString("nome_vacina");

                            //verifica se o valor no banco é nulo
                            if (nomeVacina != null) {
                                //Pega o valor armazanado em nomeVacina e coloca dentro do objeto DTO
                                dose.setNome_vacina(nomeVacina);
                            }

                            // Adiciona a vacina na lista
                            listaDeDoses.add(dose);
                        }
                    }
                }

                // Associa a lista de vacinas ao DTO da carteira
                carteiraEncontrada.setVacinas(listaDeDoses);
            }

        } catch (Exception e) {

            // Exibe mensagem caso ocorra algum erro
            System.out.println(
                    "Erro ao buscar a carteira no banco: "
                            + e.getMessage());
        }

        // Retorna a carteira preenchida ou null caso não encontre
        return carteiraEncontrada;
    }

    @Override
    public void inserirVacinaNaCarteira(String matriculaCertidao, Aplicacao_VacinaDTO dto) {

        // Busca o id_crianca pela matrícula
        String sqlBuscarCrianca =
                "SELECT id_crianca FROM Crianca WHERE matricula_certidao = ?";

        // Busca o id_vacina pelo nome da vacina
        String sqlBuscarVacina =
                "SELECT id_vacina FROM Vacina WHERE nome_vacina = ?";

        // Busca o id_lote pelo id_vacina
        String sqlBuscarLote =
                "SELECT id_lote FROM Lote WHERE id_vacina = ? LIMIT 1";

        // Insere na tabela Aplicacao_Vacina
        String sqlInserir =
                "INSERT INTO Aplicacao_Vacina (id_crianca, id_lote, dose, data_aplicacao) " +
                        "VALUES (?, ?, ?, ?)";
        //conecta com o banco e depois encerra conexão automaticamente
        try (Connection conn = DatabaseConnection.conectar()) {

            // Pega o id da criança
            int idCrianca = 0;
            try (PreparedStatement stmt = conn.prepareStatement(sqlBuscarCrianca)) {
                stmt.setString(1, matriculaCertidao);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        idCrianca = rs.getInt("id_crianca");
                    } else {
                        System.out.println("Criança não encontrada.");
                        return;
                    }
                }
            }

            // Pega o id da vacina pelo nome
            int idVacina = 0;
            try (PreparedStatement stmt = conn.prepareStatement(sqlBuscarVacina)) {
                stmt.setString(1, dto.getNome_vacina());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        idVacina = rs.getInt("id_vacina");
                    } else {
                        System.out.println("Vacina '" + dto.getNome_vacina() + "' não encontrada no banco.");
                        return;
                    }
                }
            }

            //Pega o id do lote pela vacina
            int idLote = 0;
            try (PreparedStatement stmt = conn.prepareStatement(sqlBuscarLote)) {
                stmt.setInt(1, idVacina);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        idLote = rs.getInt("id_lote");
                    } else {
                        System.out.println("Nenhum lote encontrado para essa vacina.");
                        return;
                    }
                }
            }

            // Insere a aplicação da vacina
            try (PreparedStatement stmt = conn.prepareStatement(sqlInserir)) {
                stmt.setInt(1, idCrianca);
                stmt.setInt(2, idLote);
                stmt.setString(3, dto.getDose());
                stmt.setDate(4, java.sql.Date.valueOf(dto.getDt_aplicacao()));
                stmt.executeUpdate();
                System.out.println("Vacina adicionada no banco com sucesso!");
            }

        } catch (Exception e) {
            System.out.println("Erro ao inserir vacina: " + e.getMessage());
        }
    }
}