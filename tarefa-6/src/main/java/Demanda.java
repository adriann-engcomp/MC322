/**
 * Representa um pedido de produção de medicamentos na fábrica farmacêutica.
 * <p>
 * Cada demanda possui identificador sequencial único, tipo de medicamento, quantidade solicitada,
 * custo operacional estimado por unidade e um estado controlado pelo ciclo de vida
 * ({@link StatusDemanda}).
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class Demanda {

    /** Contador estático para geração sequencial do identificador das demandas. */
    private static int proximoNumero = 1;

    private final int numero;
    private final TipoMedicamento tipo;
    private final String medicamento;
    private final double custoOperacionalPorUnidade;
    private int quantidade;
    private StatusDemanda status;

    /**
     * Cria uma nova demanda de produção de medicamento no estado {@link StatusDemanda#PENDENTE}.
     *
     * @param tipo                       Tipo regulatório e formulário do medicamento.
     * @param medicamento                Nome comercial ou princípio ativo do medicamento.
     * @param quantidade                 Quantidade de unidades a serem produzidas (estritamente positiva).
     * @param custoOperacionalPorUnidade Custo estimado de operação das máquinas por unidade.
     * @throws DemandaInvalidaException Se o tipo for nulo ou se a quantidade for menor ou igual a zero.
     */
    public Demanda(TipoMedicamento tipo, String medicamento, int quantidade, double custoOperacionalPorUnidade) {
        if (tipo == null || quantidade <= 0) {
            throw new DemandaInvalidaException("É obrigatório definir o tipo do medicamento e a quantidade deve ser maior que zero.");
        }
        this.numero = proximoNumero++;
        this.tipo = tipo;
        this.medicamento = medicamento;
        this.quantidade = quantidade;
        this.custoOperacionalPorUnidade = custoOperacionalPorUnidade;
        this.status = StatusDemanda.PENDENTE;
    }

    /**
     * Altera o status atual da demanda respeitando as regras de transição de estado.
     *
     * @param novo Novo status pretendido para a demanda.
     * @throws DemandaInvalidaException Se a transição para o novo status não for permitida.
     */
    private void mudarStatus(StatusDemanda novo) {
        if (!status.mudaPara(novo)) {
            throw new DemandaInvalidaException(String.format(
                "Transição de status inválida no pedido #%d: %s -> %s",
                numero, status.getDescricao(), novo.getDescricao()));
        }
        this.status = novo;
    }

    /**
     * Transiciona o status da demanda para {@link StatusDemanda#EM_PRODUCAO}.
     *
     * @throws DemandaInvalidaException Se o estado atual não permitir iniciar produção.
     */
    public void iniciarProducao() {
        mudarStatus(StatusDemanda.EM_PRODUCAO);
    }

    /**
     * Transiciona o status da demanda para {@link StatusDemanda#CONCLUIDA}.
     *
     * @throws DemandaInvalidaException Se o estado atual não permitir a conclusão.
     */
    public void concluir() {
        mudarStatus(StatusDemanda.CONCLUIDA);
    }

    /**
     * Transiciona o status da demanda para {@link StatusDemanda#CANCELADA}.
     *
     * @throws DemandaInvalidaException Se o estado atual não permitir o cancelamento.
     */
    public void cancelar() {
        mudarStatus(StatusDemanda.CANCELADA);
    }

    /**
     * Verifica se a demanda está em um estado passível de alteração de quantidade.
     * Apenas demandas pendentes ou já concluídas (reabertura de pedido) podem ser atualizadas.
     *
     * @return {@code true} se puder ser atualizada; {@code false} caso contrário.
     */
    public boolean podeSerAtualizada() {
        return status == StatusDemanda.PENDENTE || status == StatusDemanda.CONCLUIDA;
    }

    /**
     * Atualiza a quantidade de unidades da demanda. Se a demanda já estava concluída,
     * ela retorna automaticamente ao status {@link StatusDemanda#PENDENTE}.
     *
     * @param novaQuantidade Nova quantidade desejada (deve ser estritamente positiva).
     * @throws DemandaInvalidaException Se a quantidade for menor ou igual a zero ou se o estado atual não permitir alteração.
     */
    public void atualizarQuantidade(int novaQuantidade) {
        if (novaQuantidade <= 0) {
            throw new DemandaInvalidaException("A quantidade da demanda deve ser estritamente positiva.");
        }
        if (!podeSerAtualizada()) {
            throw new DemandaInvalidaException("Pedido #" + numero + " está com status " + status.getDescricao() + " e não pode ter a quantidade alterada.");
        }
        this.quantidade = novaQuantidade;
        if (status == StatusDemanda.CONCLUIDA) {
            mudarStatus(StatusDemanda.PENDENTE);
        }
    }

    /**
     * Avalia se a demanda está apta para seleção e processamento imediato
     * (deve estar com status {@link StatusDemanda#PENDENTE} e quantidade maior que zero).
     *
     * @return {@code true} se estiver elegível; {@code false} caso contrário.
     */
    public boolean estaElegivel() {
        return status == StatusDemanda.PENDENTE && quantidade > 0;
    }

    /**
     * Calcula o consumo total estimado de insumo / matéria-prima em kg necessário para produzir a demanda.
     *
     * @return Quantidade total de matéria-prima consumida (em kg).
     */
    public double calcularConsumoPorDemanda() {
        return quantidade * tipo.getConsumoPorUnidade();
    }

    /**
     * Calcula o custo financeiro operacional total estimado para fabricar todas as unidades da demanda.
     *
     * @return Custo total de processamento em reais (R$).
     */
    public double calcularCustoPorDemanda() {
        return quantidade * custoOperacionalPorUnidade;
    }

    /**
     * Verifica se o custo de operação da demanda cabe dentro do orçamento financeiro disponível.
     *
     * @param orcamentoDisponivel Valor do orçamento atual da fábrica.
     * @return {@code true} se o custo total for menor ou igual ao orçamento; {@code false} caso contrário.
     */
    public boolean cabeNoOrcamento(double orcamentoDisponivel) {
        return calcularCustoPorDemanda() <= orcamentoDisponivel;
    }

    /**
     * Obtém o número identificador sequencial da demanda.
     *
     * @return Identificador da demanda.
     */
    public int getNumero() {
        return numero;
    }
    
    /**
     * Obtém a classificação / tipo regulatório do medicamento solicitado.
     *
     * @return Tipo do medicamento.
     */
    public TipoMedicamento getTipo() {
        return tipo;
    }

    /**
     * Obtém o nome do medicamento da demanda.
     *
     * @return Nome do medicamento.
     */
    public String getMedicamento() {
        return medicamento;
    }

    /**
     * Obtém a quantidade de unidades requisitadas.
     *
     * @return Quantidade de unidades.
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Obtém o status do ciclo de vida atual da demanda.
     *
     * @return Status da demanda.
     */
    public StatusDemanda getStatus() {
        return status;
    }

    /**
     * Obtém o custo operacional das máquinas estipulado por unidade.
     *
     * @return Custo operacional unitário em reais (R$).
     */
    public double getCustoOperacionalPorUnidade() {
        return custoOperacionalPorUnidade;
    }

    /**
     * Retorna a representação textual formatada dos dados da demanda.
     *
     * @return String representativa da demanda.
     */
    @Override
    public String toString() {
        return String.format("Pedido #%d - %s (%s) x%d [%s]",
            numero, medicamento, tipo.getNome(), quantidade, status.getDescricao());
    }
}
