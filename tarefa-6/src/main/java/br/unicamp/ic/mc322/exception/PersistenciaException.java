package br.unicamp.ic.mc322.exception;

/**
 * Erro ao salvar ou carregar o estado da planta.
 *
 * @author Adriann e Alexsandro
 */
public class PersistenciaException extends PlantaException {

    /** Causas de falha exigidas no enunciado. */
    public enum Motivo {
        /** O arquivo não existe. */
        ARQUIVO_INEXISTENTE,
        /** O arquivo está vazio. */
        ARQUIVO_VAZIO,
        /** O arquivo não é um XML válido ou falta alguma informação. */
        FORMATO_INVALIDO,
        /** Erro do sistema ao ler ou gravar o arquivo. */
        FALHA_LEITURA_ESCRITA,
        /** Algum valor do arquivo viola as regras da planta. */
        DADOS_INCOMPATIVEIS
    }

    /** Causa da falha. */
    private final Motivo motivo;

    /**
     * Cria a exceção.
     *
     * @param motivo   Causa da falha.
     * @param mensagem Explicação do problema.
     */
    public PersistenciaException(Motivo motivo, String mensagem) {
        super(mensagem);
        this.motivo = motivo;
    }

    /**
     * Cria a exceção guardando o erro original.
     *
     * @param motivo   Causa da falha.
     * @param mensagem Explicação do problema.
     * @param causa    Erro original (por exemplo, uma IOException).
     */
    public PersistenciaException(Motivo motivo, String mensagem, Throwable causa) {
        super(mensagem, causa);
        this.motivo = motivo;
    }

    /**
     * Obtém a causa da falha.
     *
     * @return Motivo da falha.
     */
    public Motivo getMotivo() {
        return motivo;
    }
}