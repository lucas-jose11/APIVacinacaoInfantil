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
            stmt.setDate(3,java.sql.Date.valueOf(crianca.getDataNascimento()));
            // Agora usa o ID do responsável
            stmt.setInt(4, crianca.getIdResponsavel());

            stmt.executeUpdate();

            System.out.println( "Cadastro salvo no banco!");

        } catch (SQLIntegrityConstraintViolationException e) {

            System.out.println("\nERRO: Já existe uma criança cadastrada com essa matrícula da certidão!");

        } catch (Exception e) {
            System.out.println( "Erro ao salvar: " + e.getMessage());
        }
    }

    public CarteiraVacinacaoCriancaDTO buscarCarteiraVacNoBanco(String matriculaCertidao) {
        // 1. Busca a criança filtrando pela matrícula da certidão
        String sqlCrianca = "SELECT * FROM Crianca WHERE matricula_certidao = ?";

        // 2. Busca as vacinas filtrando pelo id_crianca (chave estrangeira na tabela de vacinas)
        // ATENÇÃO: Verifique se o nome da tabela de vacinas no seu banco é exatamente 'Aplicacao_Vacina'
        String sqlVacinas = "SELECT dose, data_aplicacao FROM Aplicacao_Vacina WHERE id_crianca = ?";

        CarteiraVacinacaoCriancaDTO carteiraEncontrada = null;
        int idCriancaEncontrada = 0;

        try (Connection conn = DatabaseConnection.conectar()) {

            // --- ETAPA 1: BUSCA OS DADOS DA CRIANÇA ---
            try (PreparedStatement stmtCrianca = conn.prepareStatement(sqlCrianca)) {
                stmtCrianca.setString(1, matriculaCertidao);

                try (ResultSet rsCrianca = stmtCrianca.executeQuery()) {
                    if (rsCrianca.next()) {
                        carteiraEncontrada = new CarteiraVacinacaoCriancaDTO();

                        // Preenche o DTO usando os nomes exatos das colunas do seu banco
                        carteiraEncontrada.setNome_crianca(rsCrianca.getString("nome"));
                        carteiraEncontrada.setMatricula_cert(rsCrianca.getString("matricula_certidao"));
                        carteiraEncontrada.setData_nascimento(rsCrianca.getDate("data_nascimento").toLocalDate());

                        // Pega o ID numérico da coluna 'id_crianca' que vimos na foto
                        idCriancaEncontrada = rsCrianca.getInt("id_crianca");
                    }
                }
            }

            // --- ETAPA 2: BUSCA AS VACINAS (Só roda se a criança foi encontrada) ---
            if (carteiraEncontrada != null && idCriancaEncontrada > 0) {
                java.util.List<Aplicacao_VacinaDTO> listaDeDoses = new java.util.ArrayList<>();

                try (PreparedStatement stmtVacinas = conn.prepareStatement(sqlVacinas)) {
                    // Passamos o ID numérico (ex: 5) para buscar as vacinas na outra tabela
                    stmtVacinas.setInt(1, idCriancaEncontrada);

                    try (ResultSet rsVacinas = stmtVacinas.executeQuery()) {
                        while (rsVacinas.next()) {
                            Aplicacao_VacinaDTO dose = new Aplicacao_VacinaDTO();

                            // Certifique-se de que os nomes 'dose' e 'data_aplicacao' existem na tabela de vacinas
                            dose.setDose(rsVacinas.getString("dose"));

                            if (rsVacinas.getDate("data_aplicacao") != null) {
                                dose.setDt_aplicacao(rsVacinas.getDate("data_aplicacao").toLocalDate());
                            }

                            listaDeDoses.add(dose);
                        }
                    }
                }

                // Injeta a lista preenchida dentro do DTO da carteira
                carteiraEncontrada.setVacinas(listaDeDoses);
            }

        } catch (Exception e) {
            // Se houver erro de coluna não encontrada na tabela de vacinas, este print vai te avisar!
            System.out.println("Erro ao buscar a carteira no banco: " + e.getMessage());
        }

        return carteiraEncontrada;
    }

    }
