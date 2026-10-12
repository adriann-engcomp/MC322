/**
 * Enumeração que define os estados do ciclo de fabricação e controle de uma unidade de produto.
 *
 * @author Adriann e Alexsandro
 */
public enum StatusProduto {

    /** Unidade aguardando o início do processamento na linha. */
    AGUARDANDO("Aguardando"),

    /** Unidade que completou o reator de mistura/processamento. */
    PROCESSADO("Processado"),

    /** Unidade atualmente em processamento nas máquinas. */
    PROCESSANDO("Processando"),

    /** Unidade devidamente selada e embalada em blister. */
    EMBALADO("Embalado"),

    /** Unidade aprovada na inspeção de qualidade segundo os critérios da Anvisa. */
    APROVADO("Aprovado"),

    /** Unidade rejeitada no controle de qualidade por falhas ou defeitos. */
    REJEITADO("Rejeitado");

    private final String descricao;

    /**
     * Construtor da constante de status de produto.
     *
     * @param descricao Rótulo legível do status.
     */
    StatusProduto(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém a descrição textual legível do status do produto.
     *
     * @return Descrição do status.
     */
    public String getDescricao() {
        return descricao;
    }
}
