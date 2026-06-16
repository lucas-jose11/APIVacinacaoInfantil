package com.SENAI.apiVacinacaoInfantil.ClassesAuxiliares;

import com.SENAI.apiVacinacaoInfantil.DTOs.*;
import com.SENAI.apiVacinacaoInfantil.Entities.Crianca;
import com.SENAI.apiVacinacaoInfantil.Service.CriancaService;
import com.SENAI.apiVacinacaoInfantil.Service.ResponsavelService;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
            System.out.println("[3] - Adicionar aplicação de vacina na carteira.");
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

    //Valida se a vacina foi aplicada, pega o número da matrícula
    // da certidão de nascimento da crianca para localizar ela

    public void VerificarCarteiraDeVacinacao() {

        // Exibe o título da funcionalidade
        System.out.println("Verifica carteira de vacinação");

        // Limpa a tela para melhorar a visualização
        LimparTela();

        // Solicita ao usuário a matrícula da certidão de nascimento
        System.out.println("Informe o número de matricula da certidão de nascimento...");
        String numMatriculaCert = sc.nextLine();

        // Instancia a camada de serviço responsável pela regra de negócio
        CriancaService criancaService = new CriancaService();

        // Busca a carteira de vacinação da criança pela matrícula informada
        CarteiraVacinacaoCriancaDTO carteiraEncontrada =
                criancaService.buscarCarteira(numMatriculaCert);

        // Verifica se a criança foi encontrada
        if (carteiraEncontrada != null) {

            System.out.println("Criança encontrada com sucesso! \n");

            // Exibe os dados básicos da criança
            System.out.println("--------Essa é a carteira de vacinação de sua criança-------- \n");
            System.out.println("Nome: " + carteiraEncontrada.getNome_crianca());
            System.out.println("Data de Nascimento: " + carteiraEncontrada.getData_nascimento());
            System.out.println("Número matricula: " + carteiraEncontrada.getMatricula_cert());

            System.out.println("\n");
            System.out.println("-------Vacinas-------");

            // Verifica se existem vacinas cadastradas na carteira
            if (carteiraEncontrada.getVacinas() == null ||
                    carteiraEncontrada.getVacinas().isEmpty()) {

                // Caso não existam vacinas cadastradas
                System.out.println("Nenhuma vacina cadastrada para esta criança.");
                System.out.println("Retorne ao menu e escolha a opção [3] - Adicionar vacina na carteira.");

            } else {

                // Percorre a lista de vacinas e imprime cada uma delas
                for (Aplicacao_VacinaDTO vacina : carteiraEncontrada.getVacinas()) {
                    System.out.println(vacina);
                }
            }

        } else {

            // Caso nenhuma criança seja encontrada com a matrícula informada
            System.out.println("Nenhuma criança cadastrada com a matrícula: "
                    + numMatriculaCert);
        }

        // Aguarda o usuário pressionar ENTER antes de retornar ao menu
        System.out.println("\nPressione ENTER para voltar ao menu.");
        sc.nextLine();
    }

    public void AdicionarVacinaNaCarteira(){
        System.out.println("=== ADICIONAR APLICAÇÃO DE VACINA NA CARTEIRA ===\n");
        LimparTela();

        System.out.println("Informe o número de matrícula da certidão de nascimento: ");
        String numMatriculaCert = sc.nextLine();

        CriancaService criancaService = new CriancaService();

        CarteiraVacinacaoCriancaDTO carteira = criancaService.buscarCarteira(numMatriculaCert);

        if (carteira == null) {
            System.out.println("\nNenhuma criança encontrada com a matrícula: " + numMatriculaCert);
            System.out.println("Cadastre a criança primeiro na opção [1] do menu anterior.");
            System.out.println("\nPressione ENTER para voltar ao menu.");
            sc.nextLine();
            return;
        }

        System.out.println("\nCriança encontrada: " + carteira.getNome_crianca());

        // Exibe a lista de vacinas disponíveis
        System.out.println("\n================ VACINAS DISPONÍVEIS ===============");
        List<VacinaDTO> vacinas = criancaService.listarVacinas();

        int indice = 1;
        for (VacinaDTO vacina : vacinas) {
            System.out.println("[" + indice + "] " + vacina);
            indice++;
        }
        System.out.println("======================================================\n");

        System.out.println("Informe os dados da vacina:\n");

        Aplicacao_VacinaDTO novaVacina = new Aplicacao_VacinaDTO();

        System.out.print("Nome da vacina: ");
        novaVacina.setNome_vacina(sc.nextLine());

        System.out.print("Número do lote: ");
        novaVacina.setNumero_lote(sc.nextInt());
        sc.nextLine(); // limpa o buffer

        System.out.print("Dose (ex: 1ª dose, 2ª dose...): ");
        novaVacina.setDose(sc.nextLine());

        System.out.print("Data de aplicação (dd/MM/yyyy): ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        novaVacina.setDt_aplicacao(LocalDate.parse(sc.nextLine(), formatter));

        criancaService.adicionarVacina(numMatriculaCert, novaVacina);

        System.out.println("\nVacina adicionada com sucesso na carteira de " + carteira.getNome_crianca() + "!");
        System.out.println("Pressione ENTER para voltar ao menu.");
        sc.nextLine();
    }

    public void DeletarVacinaNaCarteira(){

    }

}