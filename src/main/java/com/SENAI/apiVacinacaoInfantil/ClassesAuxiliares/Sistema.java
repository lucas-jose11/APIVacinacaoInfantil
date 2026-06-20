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

    //Valida se a vacina foi aplicada, pega o número da matrícula da certidão de nascimento da crianca para localizar ela

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


    // Metodo para validar se pode adicionar a dose sequencialmente
    private boolean podeAdicionarDose(String dose, java.util.List<String> dosesJaCadastradas) {

        if (dose.equalsIgnoreCase("Dose Única")) {
            return dosesJaCadastradas.isEmpty();
        }

        if (dose.equalsIgnoreCase("1ª dose")) {
            return dosesJaCadastradas.isEmpty();
        }

        if (dose.equalsIgnoreCase("2ª dose")) {
            return dosesJaCadastradas.contains("1ª dose");
        }

        if (dose.equalsIgnoreCase("3ª dose")) {
            return dosesJaCadastradas.contains("2ª dose");
        }

        if (dose.equalsIgnoreCase("4ª dose")) {
            return dosesJaCadastradas.contains("3ª dose");
        }

        return false;
    }

    public void AdicionarVacinaNaCarteira(){
        System.out.println("================  ADICIONAR APLICAÇÃO DE VACINA NA CARTEIRA ================ \n");
        LimparTela();

        // Solicita matrícula da criança
        System.out.println("Informe o número de matrícula da certidão de nascimento: ");
        String numMatriculaCert = sc.nextLine();

        CriancaService criancaService = new CriancaService();

        // Busca a carteira de vacinação pelo número de matrícula
        CarteiraVacinacaoCriancaDTO carteira = criancaService.buscarCarteira(numMatriculaCert);

        // Caso não encontre a criança cadastrada
        if (carteira == null) {
            System.out.println("\nNenhuma criança encontrada com a matrícula: " + numMatriculaCert);
            System.out.println("Cadastre a criança primeiro na opção [1] do menu anterior.");
            System.out.println("\nPressione ENTER para voltar ao menu.");
            sc.nextLine();
            return;
        }

        // Exibe nome da criança encontrada
        System.out.println("\nCriança encontrada: " + carteira.getNome_crianca());

        // Exibe a lista de vacinas disponíveis
        System.out.println("\n================ VACINAS DISPONÍVEIS ================");
        List<VacinaDTO> vacinas = criancaService.listarVacinas();

        int indice = 1;
        for (VacinaDTO vacina : vacinas) {
            System.out.println("[" + indice + "] " + vacina);
            indice++;
        }
        System.out.println("======================================================\n");

        System.out.println("Informe os dados da vacina a ser adicionada:\n");
        // ESCOLHA DA VACINA POR OPÇÃO
        VacinaDTO vacinaEscolhida = null;
        int opcaoVacina = -1;

        while (true) {
            System.out.print("Digite o número da vacina a ser adicionada: ");
            try {
                opcaoVacina = Integer.parseInt(sc.nextLine());

                // Verifica se a opção está dentro do intervalo válido
                if (opcaoVacina >= 1 && opcaoVacina <= vacinas.size()) {
                    vacinaEscolhida = vacinas.get(opcaoVacina - 1); // pega direto da lista
                    break; // sai do loop
                } else {
                    System.out.println("Opção inválida! Digite um número entre 1 e " + vacinas.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite apenas números.");
            }
        }

        // Guarda o nome da vacina escolhida
        String nomeVacina = vacinaEscolhida.getNome_vacina();
        // VALIDAÇÃO DA DOSE
        String[] todasAsDoses = {"Dose Única", "1ª dose", "2ª dose", "3ª dose", "4ª dose"};
        String dose = "";
        boolean doseValida = false;

        // Busca as doses já cadastradas dessa vacina
        java.util.List<String> dosesJaCadastradas = new java.util.ArrayList<>();
        for (Aplicacao_VacinaDTO vacinaCadastrada : carteira.getVacinas()) {
            if (vacinaCadastrada.getNome_vacina().equalsIgnoreCase(nomeVacina)) {
                dosesJaCadastradas.add(vacinaCadastrada.getDose());
            }
        }

        // Filtra apenas as doses permitidas
        int dosesPermitidas = vacinaEscolhida.getDoses_previstas();
        java.util.List<String> dosesDisponiveis = new java.util.ArrayList<>();

        if (dosesPermitidas == 1) {
            dosesDisponiveis.add("Dose Única");
        } else {
            dosesDisponiveis.add("1ª dose");
            if (dosesPermitidas >= 2) dosesDisponiveis.add("2ª dose");
            if (dosesPermitidas >= 3) dosesDisponiveis.add("3ª dose");
            if (dosesPermitidas >= 4) dosesDisponiveis.add("4ª dose");
        }

        // Exibe doses disponíveis e marca as já cadastradas
        System.out.println("\nDoses disponíveis:");
        int contador = 1;
        for (String doseOpcao : dosesDisponiveis) {
            String status = dosesJaCadastradas.contains(doseOpcao) ? " ✓ (já cadastrada)" : "";
            System.out.println("[" + contador + "] " + doseOpcao + status);
            contador++;
        }

        // Loop até escolher uma dose válida
        while (!doseValida) {
            System.out.print("\nEscolha a dose: ");
            String escolha = sc.nextLine();

            try {
                int opcaoDose = Integer.parseInt(escolha);

                if (opcaoDose < 1 || opcaoDose > dosesDisponiveis.size()) {
                    System.out.println(" Opção inválida!");
                    continue;
                }

                dose = dosesDisponiveis.get(opcaoDose - 1);

                if (dosesJaCadastradas.contains(dose)) {
                    System.out.println(" Essa dose já foi aplicada para esta criança!");
                    continue;
                }

                if (!podeAdicionarDose(dose, dosesJaCadastradas)) {
                    System.out.println(" Você deve adicionar as doses anteriores primeiro!");
                    continue;
                }

                doseValida = true;

            } catch (NumberFormatException e) {
                System.out.println(" Digite um número válido!");
            }
        }
        // VALIDAÇÃO DA DATA
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataAplicacao = null;
        boolean dataValida = false;

        // Busca a data da dose anterior (se existir)
        LocalDate dataAnterior = null;
        String doseAnterior = null;

        if (dose.equalsIgnoreCase("2ª dose")) doseAnterior = "1ª dose";
        else if (dose.equalsIgnoreCase("3ª dose")) doseAnterior = "2ª dose";
        else if (dose.equalsIgnoreCase("4ª dose")) doseAnterior = "3ª dose";

        if (doseAnterior != null) {
            for (Aplicacao_VacinaDTO vacinaCadastrada : carteira.getVacinas()) {
                if (vacinaCadastrada.getNome_vacina().equalsIgnoreCase(nomeVacina) &&
                        vacinaCadastrada.getDose().equalsIgnoreCase(doseAnterior)) {
                    dataAnterior = vacinaCadastrada.getDt_aplicacao();
                    break;
                }
            }
        }

        // Loop até informar uma data válida
        while (!dataValida) {
            System.out.print("Data de aplicação (dd/MM/yyyy): ");
            String data = sc.nextLine();

            try {
                dataAplicacao = LocalDate.parse(data, formatter);

                // Verifica se é posterior à dose anterior
                if (dataAnterior != null && dataAplicacao.isBefore(dataAnterior)) {
                    System.out.println(" A data deve ser posterior à " + doseAnterior +
                            " (" + dataAnterior.format(formatter) + ")");
                    continue;
                }

                // Verifica se já existe aplicação da mesma vacina no mesmo dia
                boolean mesmaVacinaMesmodia = false;
                for (Aplicacao_VacinaDTO vacinaCadastrada : carteira.getVacinas()) {
                    if (vacinaCadastrada.getNome_vacina().equalsIgnoreCase(nomeVacina) &&
                            vacinaCadastrada.getDt_aplicacao().isEqual(dataAplicacao)) {
                        mesmaVacinaMesmodia = true;
                        break;
                    }
                }

                if (mesmaVacinaMesmodia) {
                    System.out.println(" Esta vacina já foi aplicada em " +
                            dataAplicacao.format(formatter) + "!");
                    continue;
                }

                dataValida = true;

            } catch (Exception e) {
                System.out.println(" Data inválida! Use o formato dd/MM/yyyy");
            }
        }
        // ADICIONA A VACINA NA CARTEIRA
        Aplicacao_VacinaDTO novaVacina = new Aplicacao_VacinaDTO();
        novaVacina.setNome_vacina(nomeVacina);
        novaVacina.setDose(dose);
        novaVacina.setDt_aplicacao(dataAplicacao);

        criancaService.adicionarVacina(numMatriculaCert, novaVacina);

        System.out.println("\nVacina adicionada com sucesso na carteira de " + carteira.getNome_crianca() + "!");
        // Pergunta se deseja adicionar outra vacina
        System.out.println("\nDeseja adicionar outra vacina?");
        System.out.println("[1] - Sim");
        System.out.println("[2] - Não");
        System.out.print("Digite a opção: ");

        String escolha = sc.nextLine();

        if (escolha.equals("1")) {
            // Chama novamente o metodo para adicionar outra vacina
            AdicionarVacinaNaCarteira();
        } else if (escolha.equals("2")) {
            System.out.println("\nPressione ENTER para voltar ao menu.");
            sc.nextLine();
        } else {
            System.out.println("Opção inválida! Pressione ENTER para voltar ao menu.");
            sc.nextLine();
        }
        sc.nextLine();
    }


    public void DeletarVacinaNaCarteira() {
        System.out.println("================ DELETAR APLICAÇÃO DE VACINA NA CARTEIRA ================\n");
        LimparTela();

        // Solicita matrícula da criança
        System.out.println("Informe o número de matrícula da certidão de nascimento: ");
        String numMatriculaCert = sc.nextLine();

        CriancaService criancaService = new CriancaService();
        // Busca a carteira de vacinação pelo número de matrícula
        CarteiraVacinacaoCriancaDTO carteira = criancaService.buscarCarteira(numMatriculaCert);

        // Caso não encontre a criança
        if (carteira == null) {
            System.out.println("\nNenhuma criança encontrada com a matrícula: " + numMatriculaCert);
            System.out.println("\nPressione ENTER para voltar ao menu.");
            sc.nextLine();
            return;
        }

        // Exibe nome da criança encontrada
        System.out.println("\nCriança encontrada: " + carteira.getNome_crianca());

        // Caso não tenha vacinas aplicadas
        if (carteira.getVacinas() == null || carteira.getVacinas().isEmpty()) {
            System.out.println("\nNenhuma vacina aplicada para deletar.");
            System.out.println("\nPressione ENTER para voltar ao menu.");
            sc.nextLine();
            return;
        }

        // Lista todas as vacinas aplicadas com índice numérico
        System.out.println("\n================ VACINAS APLICADAS ================");
        int indice = 1;
        for (Aplicacao_VacinaDTO vacina : carteira.getVacinas()) {
            String dataFormatada = vacina.getDt_aplicacao().format(
                    java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            System.out.println("[" + indice + "] " + vacina.getNome_vacina() +
                    " | " + vacina.getDose() + " | " + dataFormatada);
            indice++;
        }
        System.out.println("======================================================\n");

        // Loop para garantir que o usuário digite uma opção válida
        int opcao = -1;
        while (true) {
            System.out.print("Digite o número da vacina a ser deletada: ");
            try {
                opcao = sc.nextInt();
                sc.nextLine(); // consome quebra de linha

                // Verifica se a opção está dentro do intervalo válido
                if (opcao >= 1 && opcao <= carteira.getVacinas().size()) {
                    break; // sai do loop se for válido
                } else {
                    System.out.println("Opção inválida! Digite um número entre 1 e " + carteira.getVacinas().size());
                }
            } catch (Exception e) {
                // Caso o usuário digite algo que não seja número
                System.out.println("Entrada inválida! Digite apenas números.");
                sc.nextLine(); // limpa entrada incorreta
            }
        }

        // Recupera a vacina escolhida pelo índice válido
        Aplicacao_VacinaDTO vacinaSelecionada = carteira.getVacinas().get(opcao - 1);

        // Chama o service para excluir a vacina
        criancaService.deletarVacina(
                numMatriculaCert,
                vacinaSelecionada.getNome_vacina(),
                vacinaSelecionada.getDt_aplicacao().toString()
        );

        System.out.println("Vacina deletada com sucesso!");
        // Pergunta se deseja deletar outra vacina
        System.out.println("\nDeseja deletar outra vacina?");
        System.out.println("[1] - Sim");
        System.out.println("[2] - Não");
        System.out.print("Digite a opção: ");

        String escolha = sc.nextLine();

        if (escolha.equals("1")) {
            // Chama novamente o metodo para adicionar outra vacina
            DeletarVacinaNaCarteira();
        } else if (escolha.equals("2")) {
            System.out.println("Pressione ENTER para voltar ao menu.");
            sc.nextLine();
        } else {
            System.out.println("Opção inválida! Pressione ENTER para voltar ao menu.");
            sc.nextLine();
        }

    }

}