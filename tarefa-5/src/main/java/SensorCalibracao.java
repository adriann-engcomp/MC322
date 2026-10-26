/**
 * Subcomponente interno de uma {@link Maquina}: Sensor analítico de monitoramento e calibração Anvisa.
 * <p>
 * Representa uma relação de composição estrita (1:1) com a máquina que o contém.
 * Responsável por medir e calibrar parâmetros críticos de processo químico e físico
 * (viscosidade, temperatura de selagem, homogeneidade ou leitura espectrométrica).
 * Possui regras próprias de descalibração progressiva e afere a conformidade regulatória.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class SensorCalibracao implements Auditavel {

    private final String tipoParametro;
    private final double limiarMinimoPrecisao;

    private double precisaoAtual; // De 0.0 a 100.0%
    private boolean calibrado;
    private int leiturasRealizadas;

    /**
     * Construtor do sensor de calibração.
     *
     * @param tipoParametro           Descrição do parâmetro monitorado (ex: "Calibração Óptica Anvisa RDC 658").
     * @param precisaoInicial         Percentual inicial de precisão analítica (ex: 99.8%).
     * @param limiarMinimoPrecisao    Limiar abaixo do qual o sensor entra em inconformidade.
     */
    public SensorCalibracao(String tipoParametro, double precisaoInicial, double limiarMinimoPrecisao) {
        this.tipoParametro = tipoParametro;
        this.precisaoAtual = precisaoInicial;
        this.limiarMinimoPrecisao = limiarMinimoPrecisao;
        this.calibrado = true;
        this.leiturasRealizadas = 0;
    }

    /**
     * Construtor padrão simplificado com parâmetros típicos farmacêuticos.
     *
     * @param tipoParametro Descrição do parâmetro monitorado.
     */
    public SensorCalibracao(String tipoParametro) {
        this(tipoParametro, 99.8, 90.0);
    }

    /**
     * Efetua a aferição analítica durante um lote de produção.
     * Sofre leve descalibração devido ao uso contínuo e variações térmicas.
     *
     * @param fatorPerturbacao Fator de perturbação do ambiente operacional.
     * @return {@code true} se a leitura estiver conforme os parâmetros Anvisa; {@code false} se houver desvio.
     */
    public boolean aferir(double fatorPerturbacao) {
        this.leiturasRealizadas++;
        double perda = (0.2 * fatorPerturbacao);
        this.precisaoAtual = Math.max(50.0, this.precisaoAtual - perda);

        if (this.precisaoAtual < this.limiarMinimoPrecisao) {
            this.calibrado = false;
            return false;
        }
        return true;
    }

    /**
     * Realiza o processo de calibração laboratorial do sensor,
     * restabelecendo sua precisão analítica para o padrão máximo regulatório.
     */
    public void calibrar() {
        this.precisaoAtual = 99.8;
        this.calibrado = true;
    }

    /**
     * Avalia o impacto do estado do sensor na probabilidade de falha da máquina.
     *
     * @return Acréscimo percentual na chance de erro operacional.
     */
    public double getImpactoRiscoFalha() {
        if (!calibrado) {
            return 0.15;
        }
        if (precisaoAtual < 95.0) {
            return 0.05;
        }
        return 0.0;
    }

    @Override
    public boolean precisaManutencao() {
        return !calibrado || precisaoAtual < limiarMinimoPrecisao;
    }

    @Override
    public String gerarRelatorioDiagnostico() {
        String statusCalibracao = calibrado ? "CALIBRADO (CONFORME RDC 658)" : "DESCALIBRADO (INCONFORME)";
        return String.format("Sensor de Monitoramento [%s] -> Status: %s | Precisão: %.1f%% (Mín: %.1f%%) | Leituras: %d",
                tipoParametro, statusCalibracao, precisaoAtual, limiarMinimoPrecisao, leiturasRealizadas);
    }

    /**
     * Informa se o sensor encontra-se com calibração válida segundo a Anvisa RDC 658.
     *
     * @return {@code true} se calibrado; {@code false} se descalibrado.
     */
    public boolean estaCalibrado() {
        return calibrado;
    }
}
