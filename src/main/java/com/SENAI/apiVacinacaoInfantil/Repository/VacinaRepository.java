package com.SENAI.apiVacinacaoInfantil.Repository;

import com.SENAI.apiVacinacaoInfantil.Contracts.Repository.IVacinaRepository;
import com.SENAI.apiVacinacaoInfantil.DTOs.VacinaDTO;
import com.SENAI.apiVacinacaoInfantil.Infrastructure.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VacinaRepository implements IVacinaRepository {

    @Override
    public List<VacinaDTO> buscarTodasAsVacinas() {

        String sql = "SELECT nome_vacina, fabricante, doses_previstas FROM Vacina";

        List<VacinaDTO> vacinas = new ArrayList<>();

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                VacinaDTO vacina = new VacinaDTO(
                        rs.getString("nome_vacina"),
                        rs.getString("fabricante"),
                        rs.getInt("doses_previstas")
                );
                vacinas.add(vacina);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar vacinas: " + e.getMessage());
        }

        return vacinas;
    }
}