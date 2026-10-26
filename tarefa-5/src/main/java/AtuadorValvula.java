/**
 * Subcomponente interno de uma {@link Maquina}: Atuador pneumático e válvula sanitária dosadora.
 * <p>
 * Representa uma relação de composição com a máquina que o contém.
 * Uma máquina possui múltiplos atuadores para dosagem de matéria-prima, controle de fluxo estéril,
 * compressão de blisters ou ejeção de unidades defeituosas na esteira.
 * Possui regras de contagem de ciclos mecânicos de abertura/fechamento, monitoramento de pressão
 * e risco de travamento de vedação sanitária.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class AtuadorValvula implements Auditavel {

    private final int idAtuador;
    private final String funcao;
    private final double pressaoTrabalhoBar;
    private final int ciclosMaximosSemManutencao;

    private int ciclosOperados;
    private boolean travado;
    private double integridadeVedacao; // De 0.0 a 100.0%

    /**
     * Construtor do atuador / válvula sanitária.
     *
     * @param idAtuador                   Identificador sequencial do atuador na máquina.
     * @param funcao                      Função do atuador (ex: "Válvula Sanitária Dosadora de Reagentes").
     * @param pressaoTrabalhoBar          Pressão pneumática de trabalho em bar.
     * @param ciclosMaximosSemManutencao  Limite de ciclos recomendados antes de revisão de vedação.
     */
    public AtuadorValvula(int idAtuador, String funcao, double pressaoTrabalhoBar, int ciclosMaximosSemManutencao) {
        this.idAtuador = idAtuador;
        this.funcao = funcao;
        this.pressaoTrabalhoBar = pressaoTrabalhoBar;
        this.ciclosMaximosSemManutencao = ciclosMaximosSemManutencao;
        this.ciclosOperados = 0;
        this.travado = false;
        this.integridadeVedacao = 100.0;
    }

    /**
     * Construtor simplificado com ciclos padrão de 500 ciclos.
     *
     * @param idAtuador          Identificador sequencial.
     * @param funcao             Função desempenhada pelo atuador.
     * @param pressaoTrabalhoBar Pressão de trabalho em bar.
     */
    public AtuadorValvula(int idAtuador, String funcao, double pressaoTrabalhoBar) {
        this(idAtuador, funcao, pressaoTrabalhoBar, 500);
    }

    /**
     * Aciona o atuador ou válvula em uma etapa de dosagem/movimentação.
     *
     * @param unidadesProcessadas Número de acionamentos no lote atual.
     * @return {@code true} se o acionamento foi perfeito; {@code false} se houve falha ou travamento.
     */
    public boolean acionar(int unidadesProcessadas) {
        this.ciclosOperados += unidadesProcessadas;
        this.integridadeVedacao = Math.max(0.0, this.integridadeVedacao - (0.05 * unidadesProcessadas));

        if (this.integridadeVedacao < 20.0 || this.ciclosOperados > this.ciclosMaximosSemManutencao * 2) {
            this.travado = true;
            return false;
        }
        return !travado;
    }

    /**
     * Realiza a higienização sanitária e substituição dos anéis de vedação do atuador,
     * destravando o mecanismo e restabelecendo a integridade a 100%.
     */
    public void liberarEManter() {
        this.travado = false;
        this.ciclosOperados = 0;
        this.integridadeVedacao = 100.0;
    }

    /**
     * Avalia se a válvula ou atuador está operacional.
     *
     * @return {@code true} se não estiver travado e com vedação adequada.
     */
    public boolean estaOperacional() {
        return !travado && integridadeVedacao >= 20.0;
    }

    /**
     * Retorna a contribuição de risco de falha introduzida por este atuador na máquina.
     *
     * @return Acréscimo percentual de risco de falha.
     */
    public double getImpactoRiscoFalha() {
        if (travado) {
            return 0.30;
        }
        if (integridadeVedacao < 40.0) {
            return 0.10;
        }
        return 0.0;
    }

    @Override
    public boolean precisaManutencao() {
        return travado || integridadeVedacao < 40.0 || ciclosOperados >= ciclosMaximosSemManutencao;
    }

    @Override
    public String gerarRelatorioDiagnostico() {
        String estado = travado ? "TRAVADO" : (precisaManutencao() ? "DESGASTE ELEVADO" : "OPERACIONAL");
        return String.format("Atuador #%d [%s | %.1f bar] -> Estado: %s | Ciclos: %d/%d | Vedação: %.1f%%",
                idAtuador, funcao, pressaoTrabalhoBar, estado, ciclosOperados, ciclosMaximosSemManutencao, integridadeVedacao);
    }
}
