import java.util.ArrayList;

/**
 * Classe controladora central da planta farmacêutica (padrão Controller/Manager).
 * <p>
 * Gerencia todo o fluxo de produção, integrando o cadastro de demandas, o acionamento
 * sequencial das máquinas na linha fabril, o estoque de matérias-primas, o orçamento financeiro
 * e a política ativa de escalonamento baseada no padrão Strategy ({@link EstrategiaProducao}).
 * As operações de fabricação, compra e atualização validam todas as pré-condições antes de
 * modificar qualquer estado, assegurando atomicidade e consistência através do lançamento de
 * exceções de domínio especializadas ({@link PlantaException}).
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class GerenciadorProducao {

    private ArrayList<Demanda> demandas;
    private ArrayList<Produto> produtosFabricados;
    private ArrayList<Maquina> maquinas;
    private MateriaPrima materiaPrima;
    private double budget;
    private EstrategiaProducao estrategiaAtual;

    /**
     * Construtor do gerenciador da fábrica farmacêutica.
     *
     * @param materiaPrima      Instância que gerencia o insumo farmacêutico disponível.
     * @param budgetInicial     Valor inicial do orçamento financeiro (em R$).
     * @param estrategiaInicial Estratégia inicial adotada para seleção de demandas.
     */
    public GerenciadorProducao(MateriaPrima materiaPrima, double budgetInicial,
        EstrategiaProducao estrategiaInicial) {
        this.demandas = new ArrayList<>();
        this.produtosFabricados = new ArrayList<>();
        this.maquinas = new ArrayList<>();
        this.materiaPrima = materiaPrima;
        this.budget = budgetInicial;
        this.estrategiaAtual = estrategiaInicial;
    }

    /**
     * Define uma nova estratégia de seleção e atendimento de demandas.
     *
     * @param novaEstrategia Nova política concreta de {@link EstrategiaProducao}.
     */
    public void setEstrategia(EstrategiaProducao novaEstrategia) {
        this.estrategiaAtual = novaEstrategia;
    }

    /**
     * Obtém a estratégia de produção que está ativa no momento.
     *
     * @return Estratégia atualmente em vigor.
     */
    public EstrategiaProducao getEstrategiaAtual() {
        return estrategiaAtual;
    }

    /**
     * Executa a fabricação da próxima demanda selecionada pela estratégia de produção ativa.
     *
     * @throws DemandaInvalidaException     Se nenhuma estratégia estiver definida ou nenhuma demanda for elegível.
     * @throws MaquinaIndisponivelException Se alguma máquina da linha estiver quebrada.
     * @throws RecursoInsuficienteException Se faltar matéria-prima ou orçamento para a produção.
     */
    public void executarProximaProducao() {
        if (estrategiaAtual == null) {
            throw new DemandaInvalidaException("Nenhuma estratégia de produção está definida.");
        }
        Demanda selecionada = estrategiaAtual.selecionarDemanda(demandas, budget);
        if (selecionada == null) {
            throw new DemandaInvalidaException("Nenhuma demanda elegível encontrada pela estratégia [" + estrategiaAtual.getNomeEstrategia() + "].");
        }
        int indice = demandas.indexOf(selecionada);
        fabricarDemanda(indice);
    }

    /**
     * Registra uma nova demanda na lista de pedidos da fábrica.
     *
     * @param demanda Demanda a ser cadastrada.
     * @throws DemandaInvalidaException Se a demanda fornecida for nula.
     */
    public void registrarDemanda(Demanda demanda) {
        if (demanda == null) {
            throw new DemandaInvalidaException("Não é possível registrar uma demanda nula.");
        }
        demandas.add(demanda);
    }

    /**
     * Atualiza a quantidade solicitada de uma demanda existente pelo seu índice na lista.
     *
     * @param indice         Posição da demanda na lista (0-indexada).
     * @param novaQuantidade Nova quantidade desejada (estritamente positiva).
     * @throws DemandaInvalidaException Se o índice for inexistente ou a quantidade/estado for inválido.
     */
    public void atualizarDemanda(int indice, int novaQuantidade) {
        if (indice < 0 || indice >= demandas.size()) {
            throw new DemandaInvalidaException("Demanda não encontrada: índice " + (indice + 1) + " inexistente.");
        }
        demandas.get(indice).atualizarQuantidade(novaQuantidade);
        System.out.println("Demanda de \"" + demandas.get(indice).getMedicamento() + "\" atualizada para " + novaQuantidade + " unidades.");
    }

    /**
     * Adiciona uma máquina à esteira da linha de fabricação da fábrica.
     *
     * @param maquina Máquina a ser inserida na sequência de produção.
     */
    public void adicionarMaquina(Maquina maquina) {
        maquinas.add(maquina);
    }

    /**
     * Exibe no terminal a listagem de todas as demandas cadastradas e seus respectivos status.
     */
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
    
    /**
     * Processa a fabricação completa de uma demanda específica garantindo atomicidade e consistência.
     * <p>
     * Todas as condições necessárias (existência da demanda, compatibilidade de status, integridade
     * das máquinas, estoque físico de matéria-prima e saldo orçamentário) são verificadas antes de
     * qualquer mutação de estado. Se qualquer requisito não for atendido, uma exceção de domínio
     * é disparada sem que o estoque ou orçamento sofram qualquer alteração.
     * </p>
     *
     * @param indiceDemanda Posição da demanda na lista de pedidos (0-indexada).
     * @throws DemandaInvalidaException     Se o índice for inválido, ou a demanda estiver em estado incompatível.
     * @throws MaquinaIndisponivelException Se alguma máquina da linha estiver quebrada.
     * @throws RecursoInsuficienteException Se matéria-prima ou orçamento forem insuficientes.
     */
    public void fabricarDemanda(int indiceDemanda) {
        if (demandas.isEmpty()) {
            throw new DemandaInvalidaException("Não há demandas cadastradas para fabricação.");
        }
        if (indiceDemanda < 0 || indiceDemanda >= demandas.size()) {
            throw new DemandaInvalidaException("Demanda inexistente: índice selecionado (" + (indiceDemanda + 1) + ") não encontrado.");
        }

        Demanda demanda = demandas.get(indiceDemanda);

        // 1. Validação de regras da Demanda
        if (demanda.getStatus() == StatusDemanda.CONCLUIDA) {
            throw new DemandaInvalidaException("Operação incompatível: A demanda de '" + demanda.getMedicamento() + "' já foi concluída anteriormente.");
        }
        if (demanda.getStatus() == StatusDemanda.EM_PRODUCAO) {
            throw new DemandaInvalidaException("Operação incompatível: A demanda de '" + demanda.getMedicamento() + "' já está em produção.");
        }
        if (demanda.getStatus() == StatusDemanda.CANCELADA) {
            throw new DemandaInvalidaException("Operação incompatível: A demanda de '" + demanda.getMedicamento() + "' foi cancelada e não pode ser fabricada.");
        }

        int quantidade = demanda.getQuantidade();
        if (quantidade <= 0) {
            throw new DemandaInvalidaException("A quantidade da demanda é zero ou inválida. Atualize a demanda antes de fabricar.");
        }

        // 2. Validação da integridade das Máquinas
        for (Maquina maquina : maquinas) {
            if (maquina.estaQuebrada()) {
                throw new MaquinaIndisponivelException(String.format(
                    "Não foi possível iniciar a produção: A máquina '%s' está quebrada e necessita de manutenção antes de operar.",
                    maquina.getNome()));
            }
        }

        // 3. Validação de Matéria-Prima
        double materiaPrimaNecessaria = demanda.calcularConsumoPorDemanda();
        if (!materiaPrima.verificarDisponibilidade(materiaPrimaNecessaria)) {
            throw new RecursoInsuficienteException(String.format(
                "Matéria-prima insuficiente para fabricar o lote solicitado!\nNecessário: %.2f %s | Disponível: %.2f %s.\nCompre mais matéria-prima antes de iniciar a produção.",
                materiaPrimaNecessaria, materiaPrima.getUnidade(), materiaPrima.getQuantidade(), materiaPrima.getUnidade()));
        }

        // 4. Validação de Orçamento
        double custoOperacional = calcularCustoProducao(quantidade);
        if (budget < custoOperacional) {
            throw new RecursoInsuficienteException(String.format(
                "Orçamento insuficiente para custear a operação das máquinas!\nNecessário: R$ %.2f | Orçamento disponível: R$ %.2f.",
                custoOperacional, budget));
        }

        // --- Transição de estado atômica: Todas as validações foram aprovadas ---
        demanda.iniciarProducao();
        materiaPrima.consumir(materiaPrimaNecessaria);
        budget -= custoOperacional;

        for (Maquina maquina : maquinas) {
            maquina.ligar();
        }

        int aprovados = 0;
        int rejeitados = 0;
        String lote = "LOTE-" + demanda.getNumero();

        for (int i = 0; i < quantidade; i++) {
            Produto produto = demanda.getTipo().criarProduto(demanda.getMedicamento() + " #" + (i + 1), lote);
            produto.iniciarProcessamento();

            for (Maquina maquina : maquinas) {
                maquina.processar(produto);
            }

            produtosFabricados.add(produto);

            if (produto.foiAprovado()) {
                aprovados++;
            } else {
                rejeitados++;
            }
        }

        for (Maquina m : maquinas) {
            m.registrarCicloDeUso();
            m.desligar();
        }

        demanda.concluir();

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

    /**
     * Realiza a compra de matéria-prima, verificando a disponibilidade de orçamento
     * e atualizando o estoque de insumos.
     *
     * @param quantidade Quantidade de insumos a ser comprada (em kg, deve ser positiva).
     * @throws DemandaInvalidaException     Se a quantidade for menor ou igual a zero.
     * @throws RecursoInsuficienteException Se o orçamento disponível for insuficiente.
     */
    public void comprarMateriaPrima(double quantidade) {
        if (quantidade <= 0) {
            throw new DemandaInvalidaException("A quantidade de matéria-prima a ser comprada deve ser maior que zero.");
        }

        double custoTotal = quantidade * materiaPrima.getCustoPorUnidade();

        if (custoTotal > budget) {
            throw new RecursoInsuficienteException(String.format(
                "Orçamento insuficiente para realizar a compra!\nCusto da compra: R$ %.2f | Orçamento disponível: R$ %.2f.",
                custoTotal, budget));
        }

        budget -= custoTotal;
        materiaPrima.adicionarEstoque(quantidade);
        System.out.printf("\nCompra de %.2f %s realizada com sucesso! Custo: R$ %.2f | Novo Budget: R$ %.2f\n",
                quantidade, materiaPrima.getUnidade(), custoTotal, budget);
    }

    /**
     * Realiza a manutenção e o reparo de todas as máquinas da planta que estejam quebradas
     * ou com saúde inferior ao limiar mínimo operacional.
     *
     * @return Quantidade de máquinas reparadas com sucesso.
     */
    public int repararMaquinas() {
        int reparadas = 0;
        for (Maquina m : maquinas) {
            if (m.estaQuebrada() || m.precisaManutencao()) {
                m.reparar();
                reparadas++;
            }
        }
        return reparadas;
    }

    /**
     * Exibe o orçamento financeiro atualmente disponível na fábrica.
     */
    public void exibirBudget() {
        System.out.printf("BUDGET ATUAL: R$ %.2f\n", budget);
    }

    /**
     * Exibe a listagem de todos os produtos acabados armazenados no armazém da fábrica.
     */
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

    /**
     * Emite o relatório de auditoria geral integrando máquinas e produtos armazenados
     * por meio do contrato {@link Auditavel}.
     */
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

    /**
     * Exibe o relatório detalhado do estoque atual de matéria-prima.
     */
    public void exibirEstoqueMateriaPrima() {
        System.out.println("\n==========================================");
        System.out.println("        ESTOQUE DE MATÉRIA-PRIMA");
        System.out.println("==========================================");
        System.out.printf("ID: %d | Nome: %s\n", materiaPrima.getId(), materiaPrima.getNome());
        System.out.printf("Quantidade disponível: %.2f %s\n", materiaPrima.getQuantidade(), materiaPrima.getUnidade());
        System.out.printf("Custo por unidade: R$ %.2f / %s\n", materiaPrima.getCustoPorUnidade(), materiaPrima.getUnidade());
        System.out.println("==========================================\n");
    }

    /**
     * Calcula o custo operacional somado de todas as máquinas para a produção de uma dada quantidade de itens.
     *
     * @param quantidadeProdutos Quantidade de itens a processar.
     * @return Custo total somado em reais (R$).
     */
    private double calcularCustoProducao(int quantidadeProdutos) {
        double custoPorProduto = 0;
        for (Maquina maquina : maquinas) {
            custoPorProduto += maquina.getCustoOperacao();
        }
        return custoPorProduto * quantidadeProdutos;
    }

    /**
     * Obtém a lista de demandas registradas na fábrica.
     *
     * @return Lista de demandas.
     */
    public ArrayList<Demanda> getDemandas() {
        return demandas;
    }

    /**
     * Define a lista de demandas da fábrica.
     *
     * @param demandas Nova lista de demandas.
     */
    public void setDemandas(ArrayList<Demanda> demandas) {
        this.demandas = demandas;
    }

    /**
     * Obtém a lista de produtos já fabricados e guardados no armazém.
     *
     * @return Lista de produtos fabricados.
     */
    public ArrayList<Produto> getProdutosFabricados() {
        return produtosFabricados;
    }

    /**
     * Define a lista de produtos fabricados no armazém.
     *
     * @param produtosFabricados Nova lista de produtos.
     */
    public void setProdutosFabricados(ArrayList<Produto> produtosFabricados) {
        this.produtosFabricados = produtosFabricados;
    }

    /**
     * Obtém a lista de máquinas instaladas na esteira de produção.
     *
     * @return Lista de máquinas.
     */
    public ArrayList<Maquina> getMaquinas() {
        return maquinas;
    }

    /**
     * Define a lista de máquinas da linha de produção.
     *
     * @param maquinas Nova lista de máquinas.
     */
    public void setMaquinas(ArrayList<Maquina> maquinas) {
        this.maquinas = maquinas;
    }

    /**
     * Obtém a matéria-prima gerenciada pela fábrica.
     *
     * @return Objeto {@link MateriaPrima}.
     */
    public MateriaPrima getMateriaPrima() {
        return materiaPrima;
    }

    /**
     * Define a matéria-prima gerenciada pela fábrica.
     *
     * @param materiaPrima Novo objeto de matéria-prima.
     */
    public void setMateriaPrima(MateriaPrima materiaPrima) {
        this.materiaPrima = materiaPrima;
    }

    /**
     * Obtém o orçamento financeiro atual da fábrica.
     *
     * @return Orçamento em reais (R$).
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Define o orçamento financeiro da fábrica.
     *
     * @param budget Novo saldo de orçamento em reais (R$).
     */
    public void setBudget(double budget) {
        this.budget = budget;
    }
}
