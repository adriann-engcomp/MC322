/**
 * Classe abstrata base que representa uma unidade individual de produto farmacêutico fabricado.
 * <p>
 * Implementa a interface {@link Auditavel}, gerenciando identificador único, nome, lote,
 * tipo regulatório, nível de qualidade, probabilidade acumulada de falha/defeito e status do ciclo de vida.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public abstract class Produto implements Auditavel {

    private final int id;
    private final String nome;
    private StatusProduto status;
    private final double qualidade;
    private double probabilidadeFalhaAcumulada;
    private static int totalProdutosFabricados = 0;

    private final String lote;
    private final TipoMedicamento tipo;

    /**
     * Construtor protegido para inicialização de instâncias das subclasses de produto.
     * Atribui um identificador sequencial global e define o estado inicial como {@link StatusProduto#AGUARDANDO}.
     *
     * @param nome Nome do medicamento ou identificador da unidade.
     * @param lote Identificador do lote de produção ao qual pertence.
     * @param tipo Tipo e formulação farmacêutica do medicamento.
     */
    protected Produto(String nome, String lote, TipoMedicamento tipo) {
        totalProdutosFabricados++;
        this.id = totalProdutosFabricados;
        this.nome = nome;
        this.status = StatusProduto.AGUARDANDO;
        this.qualidade = limitar(tipo.getQualidadePedida());
        this.probabilidadeFalhaAcumulada = 0.0;
        this.lote = lote;
        this.tipo = tipo;
    }

    /**
     * Calcula o tempo total estimado para a fabricação da quantidade indicada do produto.
     *
     * @param quantidade Número de unidades a produzir.
     * @return Tempo total de produção em minutos.
     */
    public abstract double calcularTempoProducao(int quantidade);

    /**
     * Obtém o limiar de tolerância ao risco de falha específico desta categoria de medicamento.
     *
     * @return Limiar de probabilidade aceitável de falha (entre 0.0 e 1.0).
     */
    protected abstract double getRiscoDeFalha();

    /**
     * Obtém o identificador numérico único desta unidade de produto.
     *
     * @return ID do produto.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtém o nome ou denominação da unidade de medicamento.
     *
     * @return Nome do produto.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Obtém o status operacional atual da unidade.
     *
     * @return Status do produto.
     */
    public StatusProduto getStatus() {
        return status;
    }

    /**
     * Atualiza o status operacional da unidade.
     *
     * @param status Novo status do produto.
     */
    public void setStatus(StatusProduto status) {
        this.status = status;
    }

    /**
     * Obtém o índice de qualidade farmacêutica exigido pelo produto (0.0 a 1.0).
     *
     * @return Nível de qualidade.
     */
    public double getQualidade() {
        return qualidade;
    }

    /**
     * Obtém a probabilidade de falha ou defeito acumulada pelo produto ao longo das etapas na linha.
     *
     * @return Probabilidade de falha acumulada (entre 0.0 e 1.0).
     */
    public double getProbabilidadeFalhaAcumulada() {
        return probabilidadeFalhaAcumulada;
    }

    /**
     * Obtém o número total de produtos fabricados historicamente pela fábrica.
     *
     * @return Total geral de unidades de produtos criadas.
     */
    public static int getTotalProdutosFabricados() {
        return totalProdutosFabricados;
    }

    /**
     * Limita um valor numérico ao intervalo fechado [0.0, 1.0].
     *
     * @param valor Valor a limitar.
     * @return Valor ajustado entre 0.0 e 1.0.
     */
    private static double limitar(double valor) {
        return Math.max(0.0, Math.min(1.0, valor));
    }

    /**
     * Incrementa a probabilidade de falha acumulada da unidade quando uma máquina opera com imperfeição.
     *
     * @param incremento Valor a ser adicionado à chance de falha.
     */
    public void aumentarProbabilidadeFalha(double incremento) {
        if (incremento <= 0) {
            return;
        }
        probabilidadeFalhaAcumulada = limitar(probabilidadeFalhaAcumulada + incremento);
    }

    /**
     * Indica se a unidade foi aprovada no controle de qualidade da inspeção.
     *
     * @return {@code true} se o status for {@link StatusProduto#APROVADO}; {@code false} caso contrário.
     */
    public boolean foiAprovado() {
        return status == StatusProduto.APROVADO;
    }

    /**
     * Obtém o identificador do lote de fabricação da unidade.
     *
     * @return String representativa do lote.
     */
    public String getLote() {
        return lote;
    }

    /**
     * Obtém o tipo regulatório associado a esta unidade.
     *
     * @return Instância de {@link TipoMedicamento}.
     */
    public TipoMedicamento getTipo() {
        return tipo;
    }

    /**
     * Transiciona o status da unidade para {@link StatusProduto#PROCESSANDO}.
     */
    public void iniciarProcessamento() {
        status = StatusProduto.PROCESSANDO;
    }

    /**
     * Avalia se a unidade auditada apresenta risco inaceitável ou foi rejeitada pela Anvisa.
     *
     * @return {@code true} se o produto foi rejeitado ou excedeu o risco de falha aceitável; {@code false} caso contrário.
     */
    @Override 
    public boolean precisaManutencao() {
        return status == StatusProduto.REJEITADO ||
               probabilidadeFalhaAcumulada >= getRiscoDeFalha();
    }

    /**
     * Gera o relatório diagnóstico individual da unidade de medicamento.
     *
     * @return String formatada com ID, nome, lote, status e índice de risco.
     */
    @Override 
    public String gerarRelatorioDiagnostico() {
        return "Produto " + id + " | " + 
               nome + " | lote " + lote +
               " | " + status.getDescricao() +
               " | risco " + probabilidadeFalhaAcumulada;
    }
}