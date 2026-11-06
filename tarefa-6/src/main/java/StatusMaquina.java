/**
 * Enumeração que define os estados operacionais possíveis de uma {@link Maquina}.
 *
 * @author Adriann e Alexsandro
 */
public enum StatusMaquina {

    /** Máquina desligada e inativa na linha de produção. */
    DESLIGADA("Desligada"),

    /** Máquina ligada, energizada e pronta para processar unidades de produto. */
    LIGADA("Ligada"),

    /** Máquina inoperante por esgotamento de saúde ou avaria crítica. */
    QUEBRADA("Quebrada"),

    /** Máquina em procedimento de manutenção preventiva ou corretiva. */
    MANUNTENCAO("Em manuntenção");

    private final String descricao;

    /**
     * Construtor da constante de status de máquina.
     *
     * @param descricao Descrição legível do estado da máquina.
     */
    StatusMaquina(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém a descrição legível do estado da máquina.
     *
     * @return Descrição do status.
     */
    public String getDescricao() {
        return descricao;
    }
}
