import java.util.Scanner;

public class Main {

    // Usamos o throws InterruptedException para termos acesso ao Thread.sleep() e assim simular tempo
    public static void main(String[] args) throws InterruptedException {

        MateriaPrima materiaPrima = new MateriaPrima(1, "Composto X", 100, "g", 20);
        Maquina maquina = new Maquina("Compressora de Comprimidos", 50);
        Esteira esteira = new Esteira(null, false, 50);
        EstacaoInspecao estacaoInspecao = new EstacaoInspecao();
        Produto[] produtosDisponiveis = new Produto[]{
            new Produto(1, "Paracetamol 500mg", 4),
            new Produto(2, "Ibuprofeno 400mg", 6),
            new Produto(3, "Dipirona 500mg", 5)
        };
        Produto[] produtosProcessados = new Produto[1000];
        int totalProcessados = 0;

        System.out.println("========================================");
        System.out.println("FÁBRICA DE MEDICAMENTOS");
        System.out.println("========================================");
        System.out.println("Bem-vindos à nossa fábrica farmacêutica!");
        System.out.println("Tipo de produto: Medicamentos e Comprimidos");
        System.out.println("Matéria-prima principal: " + materiaPrima.getNome() + " (" + materiaPrima.getUnidade() + ")");
        System.out.println("Desenvolvido por: Adriann e Alex");
        System.out.println("========================================\n");

        Scanner scanner = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            System.out.println("========================================");
            System.out.println("MENU PRINCIPAL");
            System.out.println("========================================");
            System.out.println("1 - Iniciar produção");
            System.out.println("2 - Consultar estoque");
            System.out.println("3 - Consultar medicamentos processados");
            System.out.println("4 - Gerenciar máquina");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção (1-5): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Opção inválida, continue.\n");
                scanner.next();
                continue;
            }

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("\n========================================");
                    System.out.println("INICIAR PRODUÇÃO");
                    System.out.println("========================================");
                    System.out.println("Remédios disponíveis para produção:");
                    for (int i = 0; i < produtosDisponiveis.length; i++) {
                        Produto p = produtosDisponiveis[i];
                        System.out.println((i + 1) + " - " + p.getNome() + " (demanda padrão: " + p.getDemandaMateriaPrima() + " " + materiaPrima.getUnidade() + ")");
                    }

                    System.out.print("\nSelecione o produto (1-" + produtosDisponiveis.length + "): ");
                    int escolhaProduto;
                    if (scanner.hasNextInt()) {
                        escolhaProduto = scanner.nextInt();
                    } else {
                        System.out.println("Seleção de produto cancelada.\n");
                        scanner.next();
                        break;
                    }

                    if (escolhaProduto < 1 || escolhaProduto > produtosDisponiveis.length) {
                        System.out.println("Produto inválido!\n");
                        break;
                    }

                    Produto produtoBase = produtosDisponiveis[escolhaProduto - 1];

                    System.out.print("Informe a quantidade de produtos a produzir: ");
                    int quantidadeAProduzir;
                    if (scanner.hasNextInt()) {
                        quantidadeAProduzir = scanner.nextInt();
                    } else {
                        System.out.println("Quantidade inválida. Operação cancelada.\n");
                        scanner.next();
                        break;
                    }

                    if (quantidadeAProduzir <= 0) {
                        System.out.println("A quantidade deve ser maior que zero!\n");
                        break;
                    }

                    double demandaPorUnidade = produtoBase.getDemandaMateriaPrima();
                    double demandaTotal = demandaPorUnidade * quantidadeAProduzir;

                    System.out.println("\n[OK] Verificando disponibilidade de " + materiaPrima.getNome() + " para produzir " + quantidadeAProduzir + " unidade(s)...");
                    Thread.sleep(600);

                    if (!materiaPrima.verificarDisponibilidade(demandaTotal)) {
                        System.out.println("[FALHA] Matéria-prima insuficiente em estoque!");
                        System.out.println("Estoque atual: " + materiaPrima.getQuantidade() + " " + materiaPrima.getUnidade() + ", Demanda total necessária: " + demandaTotal + " " + materiaPrima.getUnidade() + "\n");
                        break;
                    }

                    if (demandaPorUnidade > maquina.getCapacidadeMaxima()) {
                        System.out.println("[FALHA] A demanda por unidade (" + demandaPorUnidade + " " + materiaPrima.getUnidade() + ") excede a capacidade máxima da máquina (" + maquina.getCapacidadeMaxima() + ").\n");
                        break;
                    }

                    if (!esteira.verificarCapacidade(demandaPorUnidade)) {
                        System.out.println("[FALHA] A demanda por unidade (" + demandaPorUnidade + " " + materiaPrima.getUnidade() + ") excede a capacidade máxima da esteira (" + esteira.getCapacidadeMaxima() + ").\n");
                        break;
                    }

                    System.out.println("[OK] Demanda total de " + demandaTotal + " " + materiaPrima.getUnidade() + " (" + demandaPorUnidade + " " + materiaPrima.getUnidade() + " por unidade) pode ser atendida.");
                    Thread.sleep(500);

                    // Ligar os equipamentos necessários
                    esteira.ligar();
                    System.out.println("[OK] Esteira ligada.");
                    Thread.sleep(400);

                    //System.out.println("[OK] Máquina " + maquina.getNome() + " ligada.");
                    //Thread.sleep(400);

                    estacaoInspecao.ativar();
                    System.out.println("[OK] Estação de inspeção ativada.");
                    Thread.sleep(400);

                    int produtosProduzidos = 0;

                    for (int i = 1; i <= quantidadeAProduzir; i++) {
                        System.out.println("\n--- Processando unidade " + i + " de " + quantidadeAProduzir + " [" + produtoBase.getNome() + "] ---");

                        Produto produtoAProduzir = new Produto(produtoBase.getId(), produtoBase.getNome(), demandaPorUnidade);

                        // Colocar a matéria-prima na esteira
                        esteira.adicionarItem(materiaPrima);
                        System.out.println("[OK] Matéria-prima colocada na esteira.");
                        Thread.sleep(400);

                        // Retirar da esteira para a máquina
                        esteira.removerItem();
                        System.out.println("[OK] Matéria-prima transportada até a máquina.");
                        Thread.sleep(400);

                        // Processar a matéria-prima na máquina
                        System.out.println("[OK] Máquina processando " + demandaPorUnidade + " " + materiaPrima.getUnidade() + " de " + materiaPrima.getNome() + "...");
                        Thread.sleep(500);

                        boolean sucessoProcessamento = maquina.processar(produtoAProduzir, materiaPrima, demandaPorUnidade);
                        if (!sucessoProcessamento) {
                            System.out.println("[FALHA] Não foi possível concluir o processamento da unidade " + i + " na máquina.");
                            break;
                        }
                        System.out.println("[OK] Produto P00" + produtoAProduzir.getId() + " - " + produtoAProduzir.getNome() + " criado.");
                        Thread.sleep(400);

                        // Colocar o produto na esteira até a inspeção
                        esteira.adicionarItem(produtoAProduzir);
                        Thread.sleep(400);
                        esteira.removerItem();
                        System.out.println("[OK] Produto P00" + produtoAProduzir.getId() + " transportado para inspeção.");
                        Thread.sleep(400);

                        // Realizar a inspeção do produto
                        estacaoInspecao.inspecionar(produtoAProduzir);
                        System.out.println("[OK] Produto P00" + produtoAProduzir.getId() + " aprovado na inspeção.");
                        Thread.sleep(400);

                        // Guardar no array de produtos processados
                        if (totalProcessados < produtosProcessados.length) {
                            produtosProcessados[totalProcessados] = produtoAProduzir;
                            totalProcessados++;
                        }
                        produtosProduzidos++;
                    }

                    // Desligar equipamentos após ciclo concluído
                    esteira.desligar();
                    estacaoInspecao.desativar();
                    System.out.println("\n[OK] Equipamentos desligados.");
                    Thread.sleep(400);

                    System.out.println("\n========================================");
                    System.out.println("PRODUÇÃO CONCLUÍDA COM SUCESSO");
                    System.out.println("========================================");
                    System.out.println("Unidades produzidas nesta rodada: " + produtosProduzidos + " de " + quantidadeAProduzir);
                    System.out.println("Estoque restante de " + materiaPrima.getNome() + ": " + materiaPrima.getQuantidade() + " " + materiaPrima.getUnidade());
                    System.out.println("Total de produtos inspecionados até agora: " + estacaoInspecao.getTotalInspecionados());
                    System.out.println("========================================\n");
                    break;

                case 2:
                    System.out.println("\n========================================");
                    System.out.println("CONSULTA DE ESTOQUE");
                    System.out.println("========================================");
                    System.out.println("Matéria-Prima: MP00" + materiaPrima.getId() + " - " + materiaPrima.getNome());
                    System.out.println("Estoque atual: " + materiaPrima.getQuantidade() + " " + materiaPrima.getUnidade());
                    System.out.println("Estoque de segurança mínimo: " + materiaPrima.getQuantidadeMinima() + " " + materiaPrima.getUnidade());
                    if (materiaPrima.getQuantidade() <= materiaPrima.getQuantidadeMinima()) {
                        System.out.println("Status: [ALERTA] Estoque baixo! Necessário reabastecimento.");
                    } else {
                        System.out.println("Status: [OK] Estoque disponível.");
                    }
                    System.out.println("Total de produtos já inspecionados: " + estacaoInspecao.getTotalInspecionados());
                    System.out.println("========================================\n");
                    break;

                case 3:
                    System.out.println("\n========================================");
                    System.out.println("CONSULTA DE MEDICAMENTOS PROCESSADOS");
                    System.out.println("========================================");
                    if (totalProcessados == 0) {
                        System.out.println("Nenhum medicamento foi processado até o momento.");
                    } else {
                        for (int i = 0; i < totalProcessados; i++) {
                            Produto p = produtosProcessados[i];
                            System.out.println((i + 1) + " - " + p.getNome() + " (ID: P00" + p.getId() + " | Matéria-prima gasta: " + p.getDemandaMateriaPrima() + " " + materiaPrima.getUnidade() + " | Status: " + (p.getStatus() ? "Processado" : "Pendente") + ")");
                        }
                        System.out.println("\nTotal de medicamentos processados: " + totalProcessados);
                    }
                    System.out.println("========================================\n");
                    break;

                case 4:
                    System.out.println("\nGerenciar máquina, digite 1 para ligar e 0 para desligar:");

                    int escolhaMaquina = scanner.nextInt();
                    if (escolhaMaquina == 1) {
                        maquina.ligar();
                        Thread.sleep(400);
                        System.out.println("\n[OK] Máquina ligada.");
                    } else {
                        maquina.desligar();
                        Thread.sleep(400);
                        System.out.println("\n[OK] Máquina desligada.");
                    } 
                    break;

                case 5:
                    System.out.println("\nEncerrando o sistema da planta industrial Farmacêutica. Até logo!");
                    executando = false;
                    break;
                
                    

                default:
                    System.out.println("\nOpção inválida! Escolha entre 1 e 5.\n");
            }
        }

        scanner.close();
    }
}
