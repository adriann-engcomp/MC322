/**
 * Enumeração que define os cenários de operação da fábrica farmacêutica.
 * <p>
 * Cada cenário modula parâmetros críticos de produção, tais como orçamento inicial,
 * estoque inicial de insumos, taxa de falha das máquinas, faixas de desgaste de equipamentos
 * e a semente aleatória para reprodução de simulações.
 * </p>
 *
 * @author Adriann e Alexsandro
 */

public enum Cenario {
    
    /**
     * Cenário padrão de mercado: operação regular, máquinas com desgaste moderado e orçamento estável.
     */
    FABRICA_NORMAL(
        "Fabrica em estado normal",
        "Produto novo no mercado, já auditado pela Anvisa e com perspectiva de altas vendas",
        1000.0,
        100.0,
        0.5,
        0.0, 
        3.0,
        42L),

    /**
     * Cenário crítico / emergência sanitária: alta demanda, insumos reduzidos,
     * maior taxa de falhas e desgaste acelerado das máquinas.
     */
    FABRICA_PANDEMIA(
        "Fabrica em estado Apocalíptico",
        "Demanda nas alturas, falta de matéria prima, maquinas no limite e estoque de remédios no vermelho",
        500.0,
        10.0,
        2.0,
        5.0, 
        15.0,
        2026L);

    private final String nome;
    private final String descricao;
    private final double budgetInicial;
    private final double estoqueInicialMateriaPrima;
    private final double fatorFalha;
    private final double desgasteMinimo;
    private final double desgasteMaximo;
    private final long sementeAleatoria;

    /**
     * Construtor da enumeração de cenários de operação.
     *
     * @param nome                       Nome legível do cenário.
     * @param descricao                  Descrição contextual das condições do cenário.
     * @param budgetInicial              Orçamento financeiro disponível no início da operação (em R$).
     * @param estoqueInicialMateriaPrima Quantidade inicial de matéria-prima em estoque (em kg).
     * @param fatorFalha                 Multiplicador aplicado sobre as chances basais de falha do maquinário.
     * @param desgasteMinimo             Desgaste mínimo de saúde sofrido pelas máquinas a cada ciclo.
     * @param desgasteMaximo             Desgaste máximo de saúde sofrido pelas máquinas a cada ciclo.
     * @param sementeAleatoria           Semente para geração de números pseudoaleatórios da simulação.
     */
    Cenario(String nome, String descricao, double budgetInicial,
        double estoqueInicialMateriaPrima, double fatorFalha, 
        double desgasteMinimo, double desgasteMaximo, long sementeAleatoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.budgetInicial = budgetInicial;
        this.estoqueInicialMateriaPrima = estoqueInicialMateriaPrima;
        this.fatorFalha = fatorFalha;
        this.desgasteMinimo = desgasteMinimo;
        this.desgasteMaximo = desgasteMaximo;
        this.sementeAleatoria = sementeAleatoria;
    }

    /**
     * Obtém o nome amigável do cenário.
     *
     * @return Nome do cenário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Obtém a descrição contextual do cenário.
     *
     * @return Descrição do cenário.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Obtém o orçamento financeiro inicial alocado para a fábrica.
     *
     * @return Orçamento inicial em reais (R$).
     */
    public double getBudgetInicial() {
        return budgetInicial;
    }

    /**
     * Obtém a quantidade inicial de matéria-prima alocada para a fábrica.
     *
     * @return Estoque inicial em quilogramas (kg).
     */
    public double getEstoqueInicialMateriaPrima() {
        return estoqueInicialMateriaPrima;
    }

    /**
     * Obtém o fator multiplicador de probabilidade de falha das máquinas.
     *
     * @return Fator de falha.
     */
    public double getFatorFalha() {
        return fatorFalha;
    }

    /**
     * Obtém o desgaste mínimo de saúde de máquina por ciclo de produção.
     *
     * @return Valor mínimo de desgaste.
     */
    public double getDesgasteMinimo() {
        return desgasteMinimo;
    }

    /**
     * Obtém o desgaste máximo de saúde de máquina por ciclo de produção.
     *
     * @return Valor máximo de desgaste.
     */
    public double getDesgasteMaximo() {
        return desgasteMaximo;
    }

    /**
     * Obtém a semente pseudoaleatória do cenário.
     *
     * @return Semente aleatória (seed).
     */
    public long getSementeAleatoria() {
        return sementeAleatoria;
    }
}
