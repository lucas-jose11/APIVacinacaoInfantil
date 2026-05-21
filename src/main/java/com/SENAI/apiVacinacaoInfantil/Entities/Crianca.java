package com.SENAI.apiVacinacaoInfantil.Entities;

import java.sql.Date;

public class Crianca {

    private int id_Crianca;
    private String nome;
    private String matricula_Certidao;
    private Date data_Nascimento;
    private Pai pai;
    private Mae mae;

}