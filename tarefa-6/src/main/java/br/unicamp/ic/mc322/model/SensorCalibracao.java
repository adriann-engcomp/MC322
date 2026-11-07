package br.unicamp.ic.mc322.model;

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

    /**
     * Indica se o sensor precisa de manutenção.
     *
     * @return {@code true} se estiver descalibrado ou com precisão abaixo do limiar.
     */
    @Override
    public boolean precisaManutencao() {
        return !calibrado || precisaoAtual < limiarMinimoPrecisao;
    }

    /**
     * Gera o relatório de diagnóstico do sensor.
     *
     * @return Texto com calibração, precisão e número de leituras.
     */
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

    /**
     * Cópia imutável do estado do sensor, usada para salvar e recuperar seus dados (padrão Memento).
     *
     * @param tipoParametro        Parâmetro monitorado.
     * @param limiarMinimoPrecisao Precisão mínima aceitável.
     * @param precisaoAtual        Precisão atual (50 a 100%).
     * @param calibrado            Se o sensor está calibrado.
     * @param leiturasRealizadas   Quantidade de leituras feitas.
     */
    public record Estado(String tipoParametro, double limiarMinimoPrecisao,
                         double precisaoAtual, boolean calibrado, int leiturasRealizadas) {
    }

    /**
     * Exporta o estado atual do sensor.
     *
     * @return Cópia imutável do estado.
     */
    public Estado exportarEstado() {
        return new Estado(tipoParametro, limiarMinimoPrecisao, precisaoAtual, calibrado, leiturasRealizadas);
    }

    /**
     * Verifica se um estado pode ser aplicado a este sensor, sem alterar nada.
     *
     * @param e Estado a verificar.
     * @throws IllegalArgumentException Se o estado for nulo, de outra configuração ou com valores inválidos.
     */
    void validarEstado(Estado e) {
        Validacao.exigir(e != null, "O estado do sensor é obrigatório.");
        Validacao.exigir(tipoParametro.equals(e.tipoParametro())
                && limiarMinimoPrecisao == e.limiarMinimoPrecisao(),
                "Configuração do sensor incompatível com a máquina.");
        Validacao.exigirIntervalo(e.precisaoAtual(), 50.0, 100.0, "Precisão do sensor");
        Validacao.exigir(e.leiturasRealizadas() >= 0, "As leituras do sensor não podem ser negativas.");
    }

    /**
     * Restaura o sensor a partir de um estado salvo. Valida antes de copiar os valores.
     *
     * @param e Estado a restaurar.
     * @throws IllegalArgumentException Se o estado for inválido; nesse caso nada é alterado.
     */
    public void restaurarEstado(Estado e) {
        validarEstado(e);
        precisaoAtual = e.precisaoAtual();
        calibrado = e.calibrado();
        leiturasRealizadas = e.leiturasRealizadas();
    }
}