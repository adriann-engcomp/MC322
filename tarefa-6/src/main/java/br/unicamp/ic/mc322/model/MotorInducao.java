package br.unicamp.ic.mc322.model;

/**
 * Subcomponente interno de uma {@link Maquina}: Motor elétrico de acionamento mecânico.
 * <p>
 * Representa uma relação de composição estrita (1:1) com a máquina que o contém.
 * Responsável por prover torque e força motriz aos módulos mecânicos da unidade fabril
 * (como as pás misturadoras no reator, esteiras na embalagem e posicionador na inspeção).
 * Possui regras próprias de aquecimento térmico, rotação e desgaste acumulado por uso.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class MotorInducao implements Auditavel {

    private final String identificador;
    private final double potenciaHP;
    private final int rpm;
    private final double temperaturaMaxima;

    private double temperaturaAtual;
    private double desgasteMecanico; // De 0.0 a 100.0%
    private boolean sobreaquecido;

    /**
     * Construtor do motor de indução.
     *
     * @param identificador      Identificação descritiva do motor.
     * @param potenciaHP         Potência nominal em cavalos-vapor (HP).
     * @param rpm                Rotações por minuto nominais de trabalho.
     * @param temperaturaMaxima  Temperatura limite de tolerância antes de entrar em sobreaquecimento (°C).
     */
    public MotorInducao(String identificador, double potenciaHP, int rpm, double temperaturaMaxima) {
        this.identificador = identificador;
        this.potenciaHP = potenciaHP;
        this.rpm = rpm;
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaAtual = 25.0; // Temperatura ambiente de sala limpa
        this.desgasteMecanico = 0.0;
        this.sobreaquecido = false;
    }

    /**
     * Aciona o motor durante um ciclo de produção, elevando a temperatura de acordo com a carga
     * e acumulando desgaste físico nos rolamentos e bobinamento.
     *
     * @param cargaFator Fator de intensidade da operação (ex: 1.0 para carga normal).
     */
    public void acionar(double cargaFator) {
        double elevacaoTermica = (2.5 * cargaFator);
        this.temperaturaAtual += elevacaoTermica;
        this.desgasteMecanico = Math.min(100.0, this.desgasteMecanico + (0.4 * cargaFator));

        if (this.temperaturaAtual >= this.temperaturaMaxima) {
            this.sobreaquecido = true;
        }
    }

    /**
     * Resfria o motor em períodos de descanso ou desligamento da máquina.
     */
    public void resfriar() {
        this.temperaturaAtual = Math.max(25.0, this.temperaturaAtual - 15.0);
        if (this.temperaturaAtual < this.temperaturaMaxima) {
            this.sobreaquecido = false;
        }
    }

    /**
     * Realiza a revisão técnica e manutenção corretiva/preventiva do motor,
     * restaurando a lubrificação, zerando o desgaste mecânico e normalizando a temperatura.
     */
    public void revisar() {
        this.temperaturaAtual = 25.0;
        this.desgasteMecanico = 0.0;
        this.sobreaquecido = false;
    }

    /**
     * Avalia se o motor está apto a operar normalmente.
     *
     * @return {@code true} se o motor não estiver sobreaquecido e com desgaste tolerável.
     */
    public boolean estaOperacional() {
        return !sobreaquecido && desgasteMecanico < 90.0;
    }

    /**
     * Retorna a penalidade de falha gerada pela degradação do motor para a máquina.
     *
     * @return Taxa adicional de risco (entre 0.0 e 0.30).
     */
    public double getImpactoRiscoFalha() {
        if (sobreaquecido) {
            return 0.25;
        }
        if (desgasteMecanico > 70.0) {
            return 0.10;
        }
        return 0.0;
    }

    /**
     * Indica se o motor precisa de manutenção.
     *
     * @return {@code true} se estiver sobreaquecido ou com desgaste de 60% ou mais.
     */
    @Override
    public boolean precisaManutencao() {
        return sobreaquecido || desgasteMecanico >= 60.0;
    }

    /**
     * Gera o relatório de diagnóstico do motor.
     *
     * @return Texto com estado, temperatura e desgaste.
     */
    @Override
    public String gerarRelatorioDiagnostico() {
        String estado = sobreaquecido ? "SOBREAQUECIDO" : (desgasteMecanico >= 60.0 ? "ALERTA DESGASTE" : "OPERACIONAL");
        return String.format("Motor de Indução [%s | %.1f HP, %d RPM] -> Estado: %s | Temp: %.1f°C (Máx: %.1f°C) | Desgaste: %.1f%%",
                identificador, potenciaHP, rpm, estado, temperaturaAtual, temperaturaMaxima, desgasteMecanico);
    }

    /**
     * Cópia imutável do estado do motor, usada para salvar e recuperar seus dados (padrão Memento).
     *
     * @param identificador     Identificação do motor.
     * @param potenciaHP        Potência nominal (HP).
     * @param rpm               Rotações por minuto.
     * @param temperaturaMaxima Temperatura limite (°C).
     * @param temperaturaAtual  Temperatura atual (°C).
     * @param desgasteMecanico  Desgaste mecânico (0 a 100%).
     * @param sobreaquecido     Se o motor está sobreaquecido.
     */
    public record Estado(String identificador, double potenciaHP, int rpm, double temperaturaMaxima,
                         double temperaturaAtual, double desgasteMecanico, boolean sobreaquecido) {
    }

    /**
     * Exporta o estado atual do motor.
     *
     * @return Cópia imutável do estado.
     */
    public Estado exportarEstado() {
        return new Estado(identificador, potenciaHP, rpm, temperaturaMaxima,
                temperaturaAtual, desgasteMecanico, sobreaquecido);
    }

    /**
     * Verifica se um estado pode ser aplicado a este motor, sem alterar nada.
     *
     * @param e Estado a verificar.
     * @throws IllegalArgumentException Se o estado for nulo, de outra configuração ou com valores inválidos.
     */
    void validarEstado(Estado e) {
        Validacao.exigir(e != null, "O estado do motor é obrigatório.");
        Validacao.exigir(identificador.equals(e.identificador())
                && potenciaHP == e.potenciaHP()
                && rpm == e.rpm()
                && temperaturaMaxima == e.temperaturaMaxima(),
                "Configuração do motor incompatível com a máquina.");
        Validacao.exigirIntervalo(e.temperaturaAtual(), 25.0, 1000.0, "Temperatura do motor");
        Validacao.exigirIntervalo(e.desgasteMecanico(), 0.0, 100.0, "Desgaste do motor");
    }

    /**
     * Restaura o motor a partir de um estado salvo. Valida antes de copiar os valores.
     *
     * @param e Estado a restaurar.
     * @throws IllegalArgumentException Se o estado for inválido; nesse caso nada é alterado.
     */
    public void restaurarEstado(Estado e) {
        validarEstado(e);
        temperaturaAtual = e.temperaturaAtual();
        desgasteMecanico = e.desgasteMecanico();
        sobreaquecido = e.sobreaquecido();
    }
}