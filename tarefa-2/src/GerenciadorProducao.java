
import java.util.ArrayList;

public class GerenciadorProducao {

    // Atributos
    private ArrayList<Demanda> demandas;
    private ArrayList<Produto> produtosFabricados;
    private ArrayList<Maquina> maquinas;
    private MateriaPrima materiaPrima;
    private double budget;

    // Construtor
    public GerenciadorProducao(MateriaPrima materiaPrima, double budgetInicial) {
        this.demandas = new ArrayList<>();
        this.produtosFabricados = new ArrayList<>();
        this.maquinas = new ArrayList<>();
        this.materiaPrima = materiaPrima;
        this.budget = budgetInicial;
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
        System.out.println("Demanda de \"" + demandas.get(indice).getTipoProduto() + "\" atualizada para " + novaQuantidade + " unidades.");
    }

    // Métodos de Máquinas
    public void adicionarMaquina(Maquina maquina) {
        maquinas.add(maquina);
    }

    // Produção
    public void fabricarDemanda(int indiceDemanda) {
        Demanda demanda = demandas.get(indiceDemanda);

        // Verificar se a demanda já foi atendida
        if (demanda.isAtendida()) {
            System.out.println("Aviso: Esta demanda já foi atendida anteriormente.");
            return;
        }

        int quantidade = demanda.getQuantidadeProdutos();
        if (quantidade <= 0) {
            System.out.println("Aviso: A quantidade da demanda é zero. Atualize a demanda antes de fabricar.");
            return;
        }

        // Definir consumo de matéria-prima por unidade e tipo de produto
        double consumoPorUnidade;
        if (indiceDemanda == 0 || demanda.getTipoProduto().toLowerCase().contains("alta")) {
            consumoPorUnidade = 3.0;
        } else if (indiceDemanda == 1 || demanda.getTipoProduto().toLowerCase().contains("méd") || demanda.getTipoProduto().toLowerCase().contains("med")) {
            consumoPorUnidade = 2.0;
        } else {
            consumoPorUnidade = 1.0;
        }

        // 2. Verificar disponibilidade de matéria-prima necessária
        double materiaPrimaNecessaria = demanda.calcularMateriaPrimaNecessaria(consumoPorUnidade);
        if (!materiaPrima.verificarDisponibilidade(materiaPrimaNecessaria)) {
            System.out.printf("Erro: Matéria-prima insuficiente! Necessário: %.2f %s | Disponível: %.2f %s\n",
                    materiaPrimaNecessaria, materiaPrima.getUnidade(), materiaPrima.getQuantidade(), materiaPrima.getUnidade());
            System.out.println("Compre mais matéria-prima antes de iniciar a produção.");
            return;
        }

        // 3. Verificar budget para debitar custo de operação das máquinas
        double custoOperacional = calcularCustoProducao(quantidade);
        if (budget < custoOperacional) {
            System.out.printf("Erro: Budget insuficiente para pagar a operação das máquinas! Necessário: R$ %.2f | Disponível: R$ %.2f\n",
                    custoOperacional, budget);
            return;
        }

        // Consumir matéria-prima e debitar budget
        materiaPrima.consumir(materiaPrimaNecessaria);
        budget -= custoOperacional;

        // Ligar máquinas para a produção
        for (Maquina maquina : maquinas) {
            maquina.ligar();
        }

        int aprovados = 0;
        int rejeitados = 0;

        // (Processamento -> Embalagem -> Inspeção)
        for (int i = 0; i < quantidade; i++) {
            Produto produto;
            int idProduto = Produto.getTotalProdutosFabricados() + 1;

            if (indiceDemanda == 0 || demanda.getTipoProduto().toLowerCase().contains("alta")) {
                produto = new MedicamentoAltaQualidade(idProduto, "Medicamento Alta Qualidade #" + idProduto, "Aguardando", consumoPorUnidade);
            } else if (indiceDemanda == 1 || demanda.getTipoProduto().toLowerCase().contains("méd") || demanda.getTipoProduto().toLowerCase().contains("med")) {
                produto = new MedicamentoMediaQualidade(idProduto, "Medicamento Média Qualidade #" + idProduto, "Aguardando", consumoPorUnidade);
            } else {
                produto = new MedicamentoBaixaQualidade(idProduto, "Medicamento Baixa Qualidade #" + idProduto, "Aguardando", consumoPorUnidade);
            }

            produto.processar();

            // Passagem pelas máquinas da linha de produção
            for (Maquina maquina : maquinas) {
                maquina.processar(produto);
            }

            // 5. Adicionar ao armazém
            produtosFabricados.add(produto);

            if ("Aprovado".equalsIgnoreCase(produto.getStatus())) {
                aprovados++;
            } else {
                rejeitados++;
            }
        }

        // Desligar máquinas após a produção
        for (Maquina m : maquinas) {
            m.desligar();
        }

        // 6. Marcar demanda como atendida
        demanda.atender();

        // Exibir relatório da fabricação
        System.out.println("\n==========================================");
        System.out.println("       RELATÓRIO DE FABRICAÇÃO");
        System.out.println("==========================================");
        System.out.println("Demanda atendida: " + demanda.getTipoProduto());
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
        if (quantidade <= 0) {
            System.out.println("Erro: A quantidade a ser comprada deve ser maior que zero.");
            return;
        }

        // 1. Calcular custo total da matéria-prima (quantidade * custoPorUnidade)
        double custoTotal = quantidade * materiaPrima.getCustoPorUnidade();

        // 2. Validar budget disponível
        if (custoTotal > budget) {
            System.out.printf("Erro: Budget insuficiente! Custo da compra: R$ %.2f | Budget disponível: R$ %.2f\n",
                    custoTotal, budget);
            return;
        }

        // 3. Debitar do budget e adicionar ao estoque
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
                System.out.printf("ID: %-3d | Nome: %-32s | Tipo: %-32s | Qualidade: %.1f | Falha Acumulada: %.2f | Status: %s\n",
                        produto.getId(),
                        produto.getNome(),
                        produto.getTipo(),
                        produto.getQualidade(),
                        produto.getProbabilidadeFalhaAcumulada(),
                        produto.getStatus());
            }
            System.out.println("Total no armazém: " + produtosFabricados.size() + " itens (Total geral fabricado: " + Produto.getTotalProdutosFabricados() + ")");
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
