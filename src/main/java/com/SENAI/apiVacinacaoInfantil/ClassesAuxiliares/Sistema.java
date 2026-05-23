package com.SENAI.apiVacinacaoInfantil.ClassesAuxiliares;

import com.SENAI.apiVacinacaoInfantil.DTOs.CriancaDTO;
import com.SENAI.apiVacinacaoInfantil.DTOs.ResponsavelDTO;
import com.SENAI.apiVacinacaoInfantil.Service.CriancaService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        Scanner sc = new Scanner(System.in);
        ResponsavelDTO responsavelDTO = new ResponsavelDTO();
        CriancaDTO kidDTO = new CriancaDTO();

        LimparTela();

        System.out.println("=== CADASTRO DA CRIANÇA ===");

        // responsável
        System.out.println("\nComeçaremos pelo cadastro do RESPONSÁVEL.");

        System.out.print("Nome do responsável: ");
        responsavelDTO.setNome(sc.nextLine());

        System.out.print("CPF do responsável: ");
        responsavelDTO.setCpf(sc.nextLine());

        System.out.print("Telefone do responsável: ");
        responsavelDTO.setTelefone(sc.nextLine());


        // CRIANÇA
        System.out.println("\nAgora o cadastro de sua criança!");

        System.out.print("Nome da criança: ");
        kidDTO.setNome(sc.nextLine());

        System.out.print("Número da matrícula da Certidão de Nascimento (6 números): ");
        kidDTO.setMatriculaCertidao(sc.nextLine());

        System.out.print("Data de nascimento (dd/MM/yyyy): ");

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        kidDTO.setDataNascimento(
                LocalDate.parse(sc.nextLine(), formatter)
        );

        // Associação dos DTOs
        kidDTO.setResponsavel(responsavelDTO);

        // chamar service aqui
        CriancaService criancaService = new CriancaService();
        criancaService.cadastrar(kidDTO);

        System.out.println(
                "\nCriança e responsáveis cadastrados com sucesso!"
        );

        System.out.println(
                "Pressione ENTER para voltar ao menu."
        );

        sc.nextLine();
        sc.close();
    }

    public void VerificarCarteiraDeVacinacao(){

    }

    public void AdicionarVacinaNaCarteira(){

    }

    public void DeletarVacinaNaCarteira(){

    }

}