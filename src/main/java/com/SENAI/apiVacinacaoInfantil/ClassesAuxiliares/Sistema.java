package com.SENAI.apiVacinacaoInfantil.ClassesAuxiliares;

import java.util.Scanner;
public class Sistema {

    public void LimparTela(){
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public void IniciarMenu() {

        Scanner sc = new Scanner(System.in);
        int op;

        System.out.println("Bem-vindo ao Criança Check-Out!");
        System.out.println("O sistema completo para acompanhar as vacinas de seu filho.\n");

        do {

            System.out.println("\nEscolha uma opção:");

            System.out.println("[1] - Cadastrar minha criança.");
            System.out.println("[2] - Verificar a carteira de vacinação.");
            System.out.println("[3] - Adicionar vacina.");
            System.out.println("[4] - Deletar vacina.");
            System.out.println("[5] - SAIR");

            System.out.print("Digite a opção: ");
            op = sc.nextInt();

            switch (op) {

                case 1:
                    CadastrarCrianca();
                    break;

                case 2:
                    VerificarCarteiraDeVacinacao();
                    break;

                case 3:
                    AdicionarVacinaNaCarteira();
                    break;

                case 4:
                    DeletarVacinaNaCarteira();
                    break;

                case 5:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (op != 5);

        sc.close();
        LimparTela();
    }

    public void CadastrarCrianca(){

    }

    public void VerificarCarteiraDeVacinacao(){

    }

    public void AdicionarVacinaNaCarteira(){

    }

    public void DeletarVacinaNaCarteira(){

    }

}