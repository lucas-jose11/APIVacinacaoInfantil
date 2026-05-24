package com.SENAI.apiVacinacaoInfantil.Repository;

import com.SENAI.apiVacinacaoInfantil.Contracts.Repository.ICriancaRepository;
import com.SENAI.apiVacinacaoInfantil.Entities.Crianca;
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
}