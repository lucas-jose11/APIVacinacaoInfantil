package com.SENAI.apiVacinacaoInfantil.Repository;

import com.SENAI.apiVacinacaoInfantil.Contracts.Repository.IResponsavelRepository;
import com.SENAI.apiVacinacaoInfantil.Entities.Responsavel;
import com.SENAI.apiVacinacaoInfantil.Infrastructure.DatabaseConnection;

import java.sql.*;

public class ResponsavelRepository implements IResponsavelRepository {

    @Override
    public int inserirResponsavelNoBanco(Responsavel responsavel) {

        Connection conn = null;

        try {
            conn = DatabaseConnection.conectar();

            // pega o CPF do objeto Responsável
            String cpfResponsavel =  responsavel.getCpf();

            // CPF já existe
            if (VerificarCpfResponsavel(cpfResponsavel) != -1) {
                // se existe, pega o id_responsavel e retorna. Não duplicando o responsável no banco de dados. 
                // -1 significa que o CPF não foi encontrado, ou seja, o responsável não existe no banco de dados, e pode ser cadastrado normalmente.
                return VerificarCpfResponsavel(cpfResponsavel);
            }

            // 2. Inserir responsável
            String sqlInsert =
                    "INSERT INTO Responsavel " +
                    "(nome, cpf, telefone) " +
                    "VALUES (?, ?, ?)";

            
            PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS);

            // Setar os parâmetros do PreparedStatement com os dados do objeto Responsável
            stmtInsert.setString(1,responsavel.getNome());
            stmtInsert.setString(2,responsavel.getCpf());
            stmtInsert.setString(3,responsavel.getTelefone());

            stmtInsert.executeUpdate();

            // Pega o id_responsavel gerado
            ResultSet generatedKeys =stmtInsert.getGeneratedKeys();

            if (generatedKeys.next()) {
                // Retornará o id_responsavel gerado
                return generatedKeys.getInt(1);
            }

            throw new RuntimeException( "Erro ao obter ID do responsável");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int VerificarCpfResponsavel(String cpf) {
        Connection conn = null;

        try {
            conn = DatabaseConnection.conectar();

            String sqlBusca =
                    "SELECT id_responsavel " +
                    "FROM Responsavel " +
                    "WHERE cpf = ?";

            PreparedStatement stmtBusca = conn.prepareStatement(sqlBusca);

            stmtBusca.setString(1, cpf);

            ResultSet rs = stmtBusca.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_responsavel");
            }

            return -1; // Retorna -1 se o CPF não for encontrado

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}