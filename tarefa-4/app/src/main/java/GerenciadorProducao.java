
import java.util.ArrayList;

public class GerenciadorProducao {

    // Atributos
    private ArrayList<Demanda> demandas;
    private ArrayList<Produto> produtosFabricados;
    private ArrayList<Maquina> maquinas;
    private MateriaPrima materiaPrima;
    private double budget;
    private EstrategiaProducao estrategiaAtual;

    // Construtor
    public GerenciadorProducao(MateriaPrima materiaPrima, double budgetInicial,
        EstrategiaProducao estrategiaInicial) {
        this.demandas = new ArrayList<>();
        this.produtosFabricados = new ArrayList<>();
        this.maquinas = new ArrayList<>();
        this.materiaPrima = materiaPrima;
        this.budget = budgetInicial;
        this.estrategiaAtual = estrategiaInicial;
    }

    // Métodos do Strategy
    public void setEstrategia(EstrategiaProducao novaEstrategia) {
        this.estrategiaAtual = novaEstrategia;
    }

    public EstrategiaProducao getEstrategiaAtual() {
        return estrategiaAtual;
    }

    public void executarProximaProducao() {
        if (estrategiaAtual == null) {
            System.out.println("Nenhuma estratégia definida.");
            return;
        }
        Demanda selecionada = estrategiaAtual.selecionarDemanda(demandas, budget);
        if (selecionada != null) {
            int indice = demandas.indexOf(selecionada);
            fabricarDemanda(indice);
        } else {
            System.out.println("Nenhuma demanda elegível encontrada pela estratégia " + estrategiaAtual.getNomeEstrategia());
        }
    }

    // Métodos de Demanda
    public void registrarDemanda(Demanda demanda) {
        demandas.add(demanda);
    }

    public void atualizarDemanda(int indice, int novaQuantidade) {
        if (novaQuantidade < 0) {
            System.out.println("Erro: A quantidade da demanda não pode ser negativa.");
            return;
        }
        demandas.get(indice).atualizarQuantidade(novaQuantidade);
        System.out.println("Demanda de \"" + demandas.get(indice).getMedicamento() + "\" atualizada para " + novaQuantidade + " unidades.");
    }

    // Métodos de Máquinas
    public void adicionarMaquina(Maquina maquina) {
        maquinas.add(maquina);
    }

    // Produção
    public void exibirDemandas() {
        System.out.println("\n==========================================");
        System.out.println("          DEMANDAS REGISTRADAS");
        System.out.println("==========================================");
        
        if (demandas.isEmpty()) {
            System.out.println("Nenhuma demanda registrada no momento.");
            return;
        }

        for (int i = 0; i < demandas.size(); i++) {
            Demanda demanda = demandas.get(i);
            System.out.printf("Demanda %d: Tipo: %s | Quantidade: %d | Status: %s\n",
                    i + 1, demanda.getMedicamento(), demanda.getQuantidade(), demanda.getStatus().getDescricao());
        }
    }
    
    public void fabricarDemanda(int indiceDemanda) {
        Demanda demanda = demandas.get(indiceDemanda);

        // Verificar se a demanda já foi atendida
        if (demanda.getStatus() == StatusDemanda.CONCLUIDA) {
            System.out.println("Aviso: Esta demanda já foi atendida anteriormente.");
            return;
        }

        int quantidade = demanda.getQuantidade();
        if (quantidade <= 0) {
            System.out.println("Aviso: A quantidade da demanda é zero. Atualize a demanda antes de fabricar.");
            return;
        }

        // Verificar disponibilidade de matéria-prima necessária
        double materiaPrimaNecessaria = demanda.calcularConsumoPorDemanda();
        if (!materiaPrima.verificarDisponibilidade(materiaPrimaNecessaria)) {
            System.out.printf("Erro: Matéria-prima insuficiente! Necessário: %.2f %s | Disponível: %.2f %s\n",
                    materiaPrimaNecessaria, materiaPrima.getUnidade(), materiaPrima.getQuantidade(), materiaPrima.getUnidade());
            System.out.println("Compre mais matéria-prima antes de iniciar a produção.");
            return;
        }

        // Verificar budget para debitar custo de operação das máquinas
        double custoOperacional = calcularCustoProducao(quantidade);
        if (budget < custoOperacional) {
            System.out.printf("Erro: Budget insuficiente para pagar a operação das máquinas! Necessário: R$ %.2f | Disponível: R$ %.2f\n",
                    custoOperacional, budget);
            return;
        }

        demanda.iniciarProducao();

        // Consumir matéria-prima e debitar budget
        materiaPrima.consumir(materiaPrimaNecessaria);
        budget -= custoOperacional;

        // Ligar máquinas para a produção
        for (Maquina maquina : maquinas) {
            maquina.ligar();
        }

        int aprovados = 0;
        int rejeitados = 0;
        String lote = "LOTE-" + demanda.getNumero();

        // (Processamento -> Embalagem -> Inspeção)
        for (int i = 0; i < quantidade; i++) {
            Produto produto = demanda.getTipo().criarProduto(demanda.getMedicamento() + " #" + (i + 1), lote);
            produto.iniciarProcessamento();

            // Passagem pelas máquinas da linha de produção
            for (Maquina maquina : maquinas) {
                maquina.processar(produto);
            }

            // Adicionar ao armazém
            produtosFabricados.add(produto);

            if (produto.foiAprovado()) {
                aprovados++;
            } else {
                rejeitados++;
            }
        }

        // Registrar ciclo de uso e desligar máquinas após a produção
        for (Maquina m : maquinas) {
            m.registrarCicloDeUso();
            m.desligar();
        }

        // Marcar demanda como concluída
        demanda.concluir();

        // Exibir relatório da fabricação
        System.out.println("\n==========================================");
        System.out.println("       RELATÓRIO DE FABRICAÇÃO");
        System.out.println("==========================================");
        System.out.println("Demanda atendida: " + demanda.getMedicamento());
        System.out.println("Total produzido: " + quantidade + " unidades");
        System.out.println(" - Aprovados na Inspeção (Anvisa): " + aprovados + " unidades");
        System.out.println(" - Rejeitados no Controle: " + rejeitados + " unidades");
        System.out.printf("Matéria-prima consumida: %.2f %s (Restante: %.2f %s)\n",
                materiaPrimaNecessaria, materiaPrima.getUnidade(), materiaPrima.getQuantidade(), materiaPrima.getUnidade());
        System.out.printf("Custo operacional debitado: R$ %.2f (Budget atual: R$ %.2f)\n",
                custoOperacional, budget);
        System.out.println("==========================================\n");
    }

    // Matéria-Prima
    public void comprarMateriaPrima(double quantidade) {
        if (!(quantidade > 0)) {
            System.out.println("Erro: A quantidade a ser comprada deve ser maior que zero.");
            return;
        }

        // Calcular custo total da matéria-prima (quantidade * custoPorUnidade)
        double custoTotal = quantidade * materiaPrima.getCustoPorUnidade();

        // Validar budget disponível
        if (custoTotal > budget) {
            System.out.printf("Erro: Budget insuficiente! Custo da compra: R$ %.2f | Budget disponível: R$ %.2f\n",
                    custoTotal, budget);
            return;
        }

        // Debitar do budget e adicionar ao estoque
        budget -= custoTotal;
        materiaPrima.adicionarEstoque(quantidade);
        System.out.printf("Compra de %.2f %s realizada com sucesso! Custo: R$ %.2f | Novo Budget: R$ %.2f\n",
                quantidade, materiaPrima.getUnidade(), custoTotal, budget);
    }

    // Prints e Consultas
    public void exibirBudget() {
        System.out.printf("BUDGET ATUAL: R$ %.2f\n", budget);
    }

    public void exibirArmazem() {
        System.out.println("\n==========================================");
        System.out.println("          PRODUTOS NO ARMAZÉM");
        System.out.println("==========================================");
        if (produtosFabricados.isEmpty()) {
            System.out.println("O armazém está vazio no momento.");
        } else {
            for (Produto produto : produtosFabricados) {
                System.out.printf("ID: %-3d | Nome: %-25s | Tipo: %-10s | Lote: %-8s | Qualidade: %.1f | Status: %-10s | Precisa Manutenção: %s\n",
                        produto.getId(),
                        produto.getNome(),
                        produto.getTipo().getNome(),
                        produto.getLote(),
                        produto.getQualidade(),
                        produto.getStatus().getDescricao(),
                        produto.precisaManutencao() ? "Sim" : "Não");
            }
            System.out.println("Total no armazém: " + produtosFabricados.size() + " itens (Total geral fabricado: " + Produto.getTotalProdutosFabricados() + ")");
        }
        System.out.println("==========================================\n");
    }

    public void gerarAuditoriaGeral() {
        System.out.println("\n==========================================");
        System.out.println("       RELATÓRIO DE AUDITORIA GERAL");
        System.out.println("==========================================");
        System.out.println("[MÁQUINAS]");
        for (Maquina maquina : maquinas) {
            System.out.println(maquina.gerarRelatorioDiagnostico());
        }
        System.out.println("\n[PRODUTOS]");
        if (produtosFabricados.isEmpty()) {
            System.out.println("Nenhum produto fabricado no momento.");
        } else {
            for (Produto produto : produtosFabricados) {
                System.out.println(produto.gerarRelatorioDiagnostico());
            }
        }
        System.out.println("==========================================\n");
    }

    public void exibirEstoqueMateriaPrima() {
        System.out.println("\n==========================================");
        System.out.println("        ESTOQUE DE MATÉRIA-PRIMA");
        System.out.println("==========================================");
        System.out.printf("ID: %d | Nome: %s\n", materiaPrima.getId(), materiaPrima.getNome());
        System.out.printf("Quantidade disponível: %.2f %s\n", materiaPrima.getQuantidade(), materiaPrima.getUnidade());
        System.out.printf("Custo por unidade: R$ %.2f / %s\n", materiaPrima.getCustoPorUnidade(), materiaPrima.getUnidade());
        System.out.println("==========================================\n");
    }

    // Método privado
    private double calcularCustoProducao(int quantidadeProdutos) {
        double custoPorProduto = 0;
        for (Maquina maquina : maquinas) {
            custoPorProduto += maquina.getCustoOperacao();
        }
        return custoPorProduto * quantidadeProdutos;
    }

    // Getters e Setters
    public ArrayList<Demanda> getDemandas() {
        return demandas;
    }

    public void setDemandas(ArrayList<Demanda> demandas) {
        this.demandas = demandas;
    }

    public ArrayList<Produto> getProdutosFabricados() {
        return produtosFabricados;
    }

    public void setProdutosFabricados(ArrayList<Produto> produtosFabricados) {
        this.produtosFabricados = produtosFabricados;
    }

    public ArrayList<Maquina> getMaquinas() {
        return maquinas;
    }

    public void setMaquinas(ArrayList<Maquina> maquinas) {
        this.maquinas = maquinas;
    }

    public MateriaPrima getMateriaPrima() {
        return materiaPrima;
    }

    public void setMateriaPrima(MateriaPrima materiaPrima) {
        this.materiaPrima = materiaPrima;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }
}
