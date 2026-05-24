package com.SENAI.apiVacinacaoInfantil.ClassesAuxiliares;

import com.SENAI.apiVacinacaoInfantil.DTOs.CriancaDTO;
import com.SENAI.apiVacinacaoInfantil.DTOs.ResponsavelDTO;
import com.SENAI.apiVacinacaoInfantil.Service.CriancaService;
import com.SENAI.apiVacinacaoInfantil.Service.ResponsavelService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
public class Sistema {

    public void LimparTela(){
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    private Scanner sc = new Scanner(System.in);

    public void IniciarMenu() {

        int op;

        System.out.println("Bem-vindo ao Criança Check-Out!");
        System.out.println("O sistema completo para acompanhar as vacinas de seu filho.\n");

        do {

            System.out.println("\nEscolha uma opção:");

            System.out.println("[1] - Cadastrar minha criança.");
            System.out.println("[2] - Verificar a carteira de vacinação.");
            System.out.println("[3] - Adicionar vacina na carteira.");
            System.out.println("[4] - Deletar vacina da carteira.");
            System.out.println("[5] - SAIR");

            System.out.print("Digite a opção: ");
            op = sc.nextInt();
            sc.nextLine();

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

        System.out.print("Data de nascimento da criança (usando o formato dd/MM/yyyy): ");

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        kidDTO.setDataNascimento(
                LocalDate.parse(sc.nextLine(), formatter)
        );

        // Associação dos DTOs
        kidDTO.setResponsavel(responsavelDTO);

        // Chamar o método do Service aqui, passando o DTO criado
        CriancaService criancaService = new CriancaService();

        // Lembrando que dentro do método cadastrarCrianca do Service, tem a lógica para verificar se o responsável já existe no banco de dados, e caso não exista, cadastrar o responsável, e depois cadastrar a criança com o id do responsável.
        criancaService.cadastrarCrianca(kidDTO);

        System.out.println("\nCriança e responsável cadastrados com sucesso!");
        System.out.println(  "Pressione ENTER para voltar ao menu.");

        sc.nextLine();
    }

    public void VerificarCarteiraDeVacinacao(){

    }

    public void AdicionarVacinaNaCarteira(){

    }

    public void DeletarVacinaNaCarteira(){

    }

}