/**
 * Enumeração que controla o ciclo de vida e a máquina de estados de uma {@link Demanda}.
 * <p>
 * Define as transições válidas de estados para evitar inconsistências no fluxo de atendimento
 * de pedidos (por exemplo, impedir cancelamento de pedidos já concluídos ou produção de pedidos cancelados).
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public enum StatusDemanda {

    /** Pedido registrado no sistema, aguardando alocação e início de fabricação. */
    PENDENTE("Pendente", "Aguardando a produção"),

    /** Pedido cujas unidades estão ativamente em execução na esteira de produção. */
    EM_PRODUCAO("Em produção", "Lote na esteira de fabricação"),

    /** Pedido cujas unidades foram integralmente processadas e armazenadas. */
    CONCLUIDA("Concluída", "Todas as unidades foram fabricadas"),

    /** Pedido cancelado devido à indisponibilidade de insumos, recursos ou solicitação do operador. */
    CANCELADA("Cancelada", "Não tem matéria prima ou orçamento disponível");

    private final String rotulo;
    private final String detalhe;

    /**
     * Construtor da constante de status de demanda.
     *
     * @param rotulo  Rótulo curto descritivo do estado.
     * @param detalhe Detalhes explicativos do estado da demanda.
     */
    StatusDemanda(String rotulo, String detalhe) {
        this.rotulo = rotulo;
        this.detalhe = detalhe;
    }

    /**
     * Obtém o rótulo descritivo curto do status.
     *
     * @return Rótulo do status.
     */
    public String getDescricao() {
        return rotulo;
    }

    /**
     * Obtém a descrição detalhada do estado da demanda.
     *
     * @return Detalhes do status.
     */
    public String getDetalhe() {
        return detalhe;
    }

    /**
     * Valida se a transição a partir do estado atual para um novo estado é permitida pelas regras de negócio.
     * <ul>
     *   <li>PENDENTE pode ir para EM_PRODUCAO ou CANCELADA.</li>
     *   <li>EM_PRODUCAO só pode ir para CONCLUIDA.</li>
     *   <li>CONCLUIDA pode retornar para PENDENTE (caso o cliente renove ou amplie o pedido).</li>
     *   <li>CANCELADA é um estado terminal (não transiciona).</li>
     * </ul>
     *
     * @param novo Novo status pretendido.
     * @return {@code true} se a transição for válida; {@code false} caso contrário.
     */
    public boolean mudaPara(StatusDemanda novo) {
        switch (this) {
            case PENDENTE:
                return novo == EM_PRODUCAO || novo == CANCELADA;
            case EM_PRODUCAO:
                return novo == CONCLUIDA;
            case CONCLUIDA:
                return novo == PENDENTE;
            default:
                return false;
        }
    }
}
