package com.SENAI.apiVacinacaoInfantil.Repository;

import com.SENAI.apiVacinacaoInfantil.Contracts.Repository.ICriancaRepository;
import com.SENAI.apiVacinacaoInfantil.Entities.Crianca;
import com.SENAI.apiVacinacaoInfantil.Infrastructure.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CriancaRepository
        implements ICriancaRepository {

    @Override
    public void salvar(
            Crianca crianca) {

        String sql =
                "INSERT INTO Crianca " +
                        "(nome, matricula_certidao, data_nascimento, id_pai, id_mae) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conn = DatabaseConnection.conectar();

                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, crianca.getNome());

            stmt.setString(2, crianca.getMatriculaCertidao());

            stmt.setDate(
                    3,
                    java.sql.Date.valueOf(
                            crianca.getDataNascimento()
                    )
            );

            stmt.setInt(4, 0);
            stmt.setInt(5, 0);

            stmt.executeUpdate();

            System.out.println(
                    "Cadastro salvo no banco!"
            );

        } catch (Exception e) {

            System.out.println(
                    "Erro ao salvar: "
                            + e.getMessage()
            );
        }
    }
}