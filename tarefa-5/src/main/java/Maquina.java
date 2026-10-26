import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Classe abstrata base que modela a unidade produtiva ("O Todo") da fábrica farmacêutica.
 * <p>
 * Implementa a interface {@link Auditavel} e encapsula a relação de COMPOSIÇÃO ESTRITA
 * com seus subcomponentes internos ("As Partes"):
 * </p>
 * <ul>
 *   <li>1:1 com {@link MotorInducao}: motor elétrico de acionamento mecânico.</li>
 *   <li>1:1 com {@link SensorCalibracao}: sensor analítico de controle de qualidade Anvisa RDC 658.</li>
 *   <li>1:N com {@link AtuadorValvula}: lista de válvulas sanitárias e atuadores pneumáticos.</li>
 * </ul>
 * <p>
 * O ciclo de vida dessas partes é estritamente controlado pela máquina, sendo instanciadas
 * internamente em seu construtor e reparadas conjuntamente. Detalhes internos não são expostos
 * (Lei de Deméter), provendo métodos de delegação e auditoria em cascata.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public abstract class Maquina implements Auditavel {

    private String nome;
    private StatusMaquina status;
    private int capacidadeMaxima;
    private double probabilidadeFalha;
    private double custoOperacao;

    /** Gerador de números pseudoaleatórios compartilhado para simulação probabilística. */
    protected Random random;

    /** Nível máximo de integridade/saúde física de uma máquina (100.0). */
    public static final double SAUDE_MAX = 100.0;

    private double saude;
    private double desgasteMinimo;
    private double desgasteMaximo;
    private double saudeMinima;
    private int historicoDeFalhas;

    // --- SUBCOMPONENTES INTERNOS (COMPOSIÇÃO ESTRITA) ---
    private final MotorInducao motor;
    private final SensorCalibracao sensorCalibracao;
    private final List<AtuadorValvula> atuadores;

    /**
     * Construtor padrão da máquina, instanciando internamente seus subcomponentes (Composição Estrita).
     *
     * @param nome                Nome descritivo da máquina.
     * @param capacidadeMaxima    Capacidade máxima de processamento.
     * @param probabilidadeFalha  Probabilidade base de falha (0.0 a 1.0).
     * @param custoOperacao       Custo financeiro operacional unitário (R$).
     * @param cenario             Cenário ativo na fábrica.
     * @param random              Gerador de números aleatórios.
     */
    public Maquina(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao,
        Cenario cenario, Random random) {
        this(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao, cenario, random, 2);
    }

    /**
     * Construtor com parametrização da quantidade de atuadores internos.
     * O ciclo de vida das partes é estritamente gerenciado aqui via operador {@code new}.
     *
     * @param nome                Nome descritivo da máquina.
     * @param capacidadeMaxima    Capacidade máxima de processamento.
     * @param probabilidadeFalha  Probabilidade base de falha.
     * @param custoOperacao       Custo operacional unitário.
     * @param cenario             Cenário ativo na fábrica.
     * @param random              Gerador de números aleatórios.
     * @param quantidadeAtuadores Quantidade de atuadores/válvulas internas a instanciar (cardinalidade 1:N).
     */
    public Maquina(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao,
        Cenario cenario, Random random, int quantidadeAtuadores) {
        this.nome = nome;
        this.status = StatusMaquina.DESLIGADA;
        this.capacidadeMaxima = capacidadeMaxima;
        this.probabilidadeFalha = Math.min(1.0, probabilidadeFalha * cenario.getFatorFalha());
        this.custoOperacao = custoOperacao;
        this.random = random;
        this.saude = SAUDE_MAX;
        this.desgasteMinimo = cenario.getDesgasteMinimo();
        this.desgasteMaximo = cenario.getDesgasteMaximo();
        this.saudeMinima = 30.0;
        this.historicoDeFalhas = 0;

        // FASE DE CONSTRUÇÃO SEGURA: O Todo instancia suas Partes internamente
        this.motor = new MotorInducao("Motor Principal de " + nome, 12.0, 1500, 80.0);
        this.sensorCalibracao = new SensorCalibracao("Sensor Regulatório Anvisa de " + nome, 99.5, 90.0);

        this.atuadores = new ArrayList<>();
        int qtd = Math.max(1, quantidadeAtuadores);
        for (int i = 1; i <= qtd; i++) {
            this.atuadores.add(new AtuadorValvula(i, "Válvula Sanitária #" + i + " de " + nome, 6.0));
        }
    }

    /**
     * Executa a operação especializada da máquina sobre uma unidade de produto.
     *
     * @param produto Unidade de produto a ser processada.
     */
    public abstract void processar(Produto produto);

    /**
     * Retorna a denominação do tipo de equipamento e sua função fabril.
     *
     * @return String representativa do tipo de máquina.
     */
    public abstract String getTipo();

    /**
     * Liga a máquina se ela não estiver quebrada, tornando-a apta a operar.
     */
    public void ligar() {
        if (status == StatusMaquina.QUEBRADA) {
            return;
        }
        this.status = StatusMaquina.LIGADA;
    }

    /**
     * Desliga a máquina se ela estiver ligada, resfriando os subcomponentes.
     */
    public void desligar() {
        if (status == StatusMaquina.LIGADA) {
            this.status = StatusMaquina.DESLIGADA;
            this.motor.resfriar();
        }
    }

    /**
     * Verifica se a máquina está atualmente ligada.
     *
     * @return {@code true} se estiver ligada; {@code false} caso contrário.
     */
    public boolean estaLigada() {
        return status == StatusMaquina.LIGADA;
    }

    /**
     * Avalia probabilisticamente se a máquina falha no ciclo atual, levando em conta
     * a degradação da saúde da máquina e os riscos cumulativos de seus subcomponentes.
     * Incrementa o contador de histórico de falhas caso ocorra falha.
     *
     * @return {@code true} se a máquina falhou; {@code false} caso a operação tenha sido perfeita.
     */
    protected boolean verificarFalha() {
        boolean falhou = random.nextDouble() < getProbabilidadeFalhaAtual();
        if (falhou) {
            historicoDeFalhas++;
        }
        return falhou;
    }

    /**
     * Registra um ciclo de uso concluído, aplicando um desgaste aleatório à saúde do equipamento
     * e acionando o desgaste em cascata sobre todos os seus subcomponentes internos.
     *
     * @return Quantidade de desgaste subtraída da saúde da máquina.
     */
    public double registrarCicloDeUso() {
        double desgaste = desgasteMinimo + random.nextDouble() * (desgasteMaximo - desgasteMinimo);
        saude = Math.max(0.0, saude - desgaste);
        if (saude == 0.0) {
            status = StatusMaquina.QUEBRADA;
        }

        // Propagação do ciclo operacional para os subcomponentes internos
        this.motor.acionar(1.0);
        this.sensorCalibracao.aferir(1.0);
        for (AtuadorValvula atuador : atuadores) {
            atuador.acionar(1);
        }

        return desgaste;
    }

    /**
     * Calcula a probabilidade de falha atual ajustada pelo nível de degradação da máquina
     * e amplificada pelos impactos dos subcomponentes (motor sobreaquecido, sensor descalibrado, atuadores travados).
     *
     * @return Probabilidade dinâmica de falha ajustada (entre 0.0 e 1.0).
     */
    public double getProbabilidadeFalhaAtual() {
        if (saude == 0.0) {
            return 1.0;
        }
        double baseAjustada = probabilidadeFalha * (SAUDE_MAX / saude);

        // Influência dos subcomponentes na confiabilidade do Todo
        double riscoExtraComponentes = motor.getImpactoRiscoFalha()
                + sensorCalibracao.getImpactoRiscoFalha();
        for (AtuadorValvula atuador : atuadores) {
            riscoExtraComponentes += atuador.getImpactoRiscoFalha();
        }

        return Math.min(1.0, baseAjustada + riscoExtraComponentes);
    }

    /**
     * Indica se a máquina está avariada / quebrada.
     *
     * @return {@code true} se estiver com status {@link StatusMaquina#QUEBRADA}; {@code false} caso contrário.
     */
    public boolean estaQuebrada() {
        return status == StatusMaquina.QUEBRADA;
    }

    /**
     * Realiza o reparo completo do equipamento, restaurando a saúde para o máximo,
     * status para desligada e revisando/calibrando todos os subcomponentes internos em cascata.
     */
    public void reparar() {
        status = StatusMaquina.DESLIGADA;
        saude = SAUDE_MAX;
        motor.revisar();
        sensorCalibracao.calibrar();
        for (AtuadorValvula atuador : atuadores) {
            atuador.liberarEManter();
        }
    }

    /**
     * Avalia se a máquina necessita de manutenção preventiva ou corretiva,
     * checando tanto seu estado geral quanto o de suas partes internas.
     *
     * @return {@code true} se necessitar de manutenção; {@code false} caso contrário.
     */
    @Override
    public boolean precisaManutencao() {
        boolean componentesPrecisam = motor.precisaManutencao()
                || sensorCalibracao.precisaManutencao();
        if (!componentesPrecisam) {
            for (AtuadorValvula atuador : atuadores) {
                if (atuador.precisaManutencao()) {
                    componentesPrecisam = true;
                    break;
                }
            }
        }
        return estaQuebrada() || saude < saudeMinima || componentesPrecisam;
    }

    /**
     * Gera o relatório diagnóstico técnico da máquina integrando seus subcomponentes.
     *
     * @return String contendo o relatório de auditoria da máquina.
     */
    @Override
    public String gerarRelatorioDiagnostico() {
        return gerarRelatorioDiagnosticoComIndentacao("    ");
    }

    /**
     * Gera o relatório diagnóstico em cascata formatado com indentação hierárquica.
     *
     * @param indentacao Espaçamento para alinhamento na árvore do relatório.
     * @return Diagnóstico estrutural completo da máquina e suas partes.
     */
    public String gerarRelatorioDiagnosticoComIndentacao(String indentacao) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Máquina [%s - %s] | Status: %s | Saúde: %.1f | Falha Atual: %.0f%% | Falhas Acumuladas: %d",
                nome, getTipo(), status.getDescricao(), saude, getProbabilidadeFalhaAtual() * 100, historicoDeFalhas));

        sb.append("\n").append(indentacao).append("├── ").append(motor.gerarRelatorioDiagnostico());
        sb.append("\n").append(indentacao).append("├── ").append(sensorCalibracao.gerarRelatorioDiagnostico());
        sb.append("\n").append(indentacao).append("└── Atuadores e Válvulas Sanitárias (").append(atuadores.size()).append(" unidades):");

        for (int i = 0; i < atuadores.size(); i++) {
            boolean isLast = (i == atuadores.size() - 1);
            String prefix = isLast ? "      └── " : "      ├── ";
            sb.append("\n").append(indentacao).append(prefix).append(atuadores.get(i).gerarRelatorioDiagnostico());
        }

        return sb.toString();
    }

    // --- MÉTODOS DE DELEGAÇÃO / LEI DE DEMÉTER (Tell, Don't Ask) ---

    /**
     * Informa se o motor interno está operacional, sem expor o objeto {@link MotorInducao}.
     *
     * @return {@code true} se o motor estiver operacional.
     */
    public boolean verificaMotorOperacional() {
        return motor.estaOperacional();
    }

    /**
     * Informa se o sensor interno está calibrado segundo a Anvisa, sem expor o objeto {@link SensorCalibracao}.
     *
     * @return {@code true} se o sensor estiver calibrado.
     */
    public boolean verificaSensorCalibrado() {
        return sensorCalibracao.estaCalibrado();
    }

    /**
     * Retorna a quantidade de atuadores instalados nesta máquina.
     *
     * @return Quantidade de atuadores (cardinalidade 1:N).
     */
    public int getQuantidadeAtuadores() {
        return atuadores.size();
    }

    /**
     * Retorna se todos os atuadores e válvulas internas estão operacionais.
     *
     * @return {@code true} se nenhum atuador estiver travado ou com vedação rompida.
     */
    public boolean verificaAtuadoresOperacionais() {
        for (AtuadorValvula atuador : atuadores) {
            if (!atuador.estaOperacional()) {
                return false;
            }
        }
        return true;
    }

    // --- GETTERS E SETTERS TRADICIONAIS ---

    /**
     * Obtém o nome da máquina.
     *
     * @return Nome da máquina.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da máquina.
     *
     * @param nome Novo nome.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a capacidade máxima de produção da máquina.
     *
     * @return Capacidade máxima em unidades.
     */
    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    /**
     * Define a capacidade máxima de produção da máquina.
     *
     * @param capacidadeMaxima Nova capacidade máxima.
     */
    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    /**
     * Obtém a probabilidade base de falha da máquina.
     *
     * @return Probabilidade base (entre 0.0 e 1.0).
     */
    public double getProbabilidadeFalha() {
        return probabilidadeFalha;
    }

    /**
     * Define a probabilidade base de falha da máquina.
     *
     * @param probabilidadeFalha Nova probabilidade base.
     */
    public void setProbabilidadeFalha(double probabilidadeFalha) {
        this.probabilidadeFalha = probabilidadeFalha;
    }

    /**
     * Obtém o custo operacional cobrado por unidade processada.
     *
     * @return Custo de operação unitário em reais (R$).
     */
    public double getCustoOperacao() {
        return custoOperacao;
    }

    /**
     * Define o custo operacional cobrado por unidade processada.
     *
     * @param custoOperacao Novo custo de operação unitário.
     */
    public void setCustoOperacao(double custoOperacao) {
        this.custoOperacao = custoOperacao;
    }

    /**
     * Obtém o status operacional atual da máquina.
     *
     * @return Status da máquina.
     */
    public StatusMaquina getStatus() {
        return status;
    }

    /**
     * Obtém o nível atual de saúde da máquina (de 0.0 a 100.0).
     *
     * @return Nível de saúde.
     */
    public double getSaude() {
        return saude;
    }

    /**
     * Obtém a quantidade acumulada de falhas apresentadas pela máquina ao longo de sua vida útil.
     *
     * @return Total histórico de falhas.
     */
    public int getHistoricoDeFalhas() {
        return historicoDeFalhas;
    }

    /**
     * Obtém o limite mínimo de saúde antes de a máquina exigir manutenção preventiva obrigatória.
     *
     * @return Nível mínimo de saúde tolerado.
     */
    public double getsaudeMinima() {
        return saudeMinima;
    }

    /**
     * Define o limite mínimo de saúde aceitável para operação sem alerta de manutenção.
     *
     * @param saudeMinima Novo valor de saúde mínima (entre 0.0 e 100.0).
     */
    public void setSaudeMinima(double saudeMinima) {
        if (saudeMinima >= 0 && saudeMinima <= SAUDE_MAX) {
            this.saudeMinima = saudeMinima;
        }
    }
}
