package com.SENAI.apiVacinacaoInfantil.Repository;

import com.SENAI.apiVacinacaoInfantil.Contracts.Repository.ICriancaRepository;
import com.SENAI.apiVacinacaoInfantil.Entities.Crianca;
import com.SENAI.apiVacinacaoInfantil.Entities.Responsavel;
import com.SENAI.apiVacinacaoInfantil.Infrastructure.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

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

    public Crianca buscarCriancaNoBanco(String matriculaCertidao) {
        // A sintaxe do SELECT com a condição WHERE para filtrar pela matrícula
        String sql = "SELECT * FROM Crianca WHERE matricula_certidao = ?";

        Crianca criancaEncontrada = null; // Começa nulo, só preenche se encontrar no banco

        try (
                Connection conn = DatabaseConnection.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            // 1. Passa a matrícula recebida no parâmetro para o script SQL
            stmt.setString(1, matriculaCertidao);

            // 2. Executa a busca no banco de dados e guarda o resultado no ResultSet
            try (java.sql.ResultSet rs = stmt.executeQuery()) {

                // 3. Verifica se o banco encontrou alguma linha com essa matrícula
                if (rs.next()) {
                    criancaEncontrada = new Crianca();

                    // Pega os dados das colunas do banco e preenche o objeto Java
                    criancaEncontrada.setIdCrianca(rs.getInt("id_crianca"));
                    criancaEncontrada.setNome(rs.getString("nome"));
                    criancaEncontrada.setMatriculaCertidao(rs.getString("matricula_certidao"));
                    criancaEncontrada.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());


                    Responsavel responsavelBanco = new Responsavel();

                    responsavelBanco.setIdResponsavel(rs.getInt("id_responsavel"));

                    criancaEncontrada.setResponsavel(responsavelBanco);
                }
            }

        } catch (Exception e) {
            // Trata qualquer erro de conexão ou de SQL que possa ocorrer
            System.out.println("Erro ao buscar a criança no banco: " + e.getMessage());
        }

        // Retorna a criança com os dados (ou null se não encontrar)
        return criancaEncontrada;
    }

    }
