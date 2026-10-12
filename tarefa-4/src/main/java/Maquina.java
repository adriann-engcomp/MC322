import java.util.Random;

/**
 * Classe abstrata base que modela uma máquina da linha de produção farmacêutica.
 * <p>
 * Implementa a interface {@link Auditavel}, gerenciando ciclo de vida operacional (ligar/desligar),
 * capacidade produtiva, custos de operação, desgaste de saúde por ciclo de uso,
 * histórico de falhas e diagnósticos de manutenção preventiva e corretiva.
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

    /**
     * Construtor da máquina com parametrização ajustada pelo cenário de operação ativo.
     *
     * @param nome                Nome descritivo da máquina ou do posto de trabalho.
     * @param capacidadeMaxima    Capacidade máxima de processamento de unidades da máquina.
     * @param probabilidadeFalha  Probabilidade base de ocorrência de falha (entre 0.0 e 1.0).
     * @param custoOperacao       Custo financeiro operacional de processamento por unidade (em R$).
     * @param cenario             Cenário ativo na fábrica, modulando fatores de falha e desgaste.
     * @param random              Gerador de números aleatórios para simulação.
     */
    public Maquina(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao,
        Cenario cenario, Random random) {
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
    }

    /**
     * Executa a operação especializada da máquina sobre uma unidade de produto que passa pela esteira.
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
     * Desliga a máquina se ela estiver ligada, cessando seu consumo e operação.
     */
    public void desligar() {
        if (status == StatusMaquina.LIGADA) {
            this.status = StatusMaquina.DESLIGADA;
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
     * Avalia probabilisticamente se a máquina falha no ciclo atual com base na chance atual de falha.
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
     * dentro da faixa configurada pelo cenário. Se a saúde zerar, o status torna-se {@link StatusMaquina#QUEBRADA}.
     *
     * @return Quantidade de desgaste subtraída da saúde da máquina.
     */
    public double registrarCicloDeUso() {
        double desgaste = desgasteMinimo + random.nextDouble() * (desgasteMaximo - desgasteMinimo);
        saude = Math.max(0.0, saude - desgaste);
        if (saude == 0.0) {
            status = StatusMaquina.QUEBRADA;
        }
        return desgaste;
    }

    /**
     * Calcula a probabilidade de falha atual ajustada pelo nível de degradação da máquina.
     * Quanto menor a saúde em relação a {@link #SAUDE_MAX}, maior a propensão a falhar.
     *
     * @return Probabilidade dinâmica de falha ajustada (entre 0.0 e 1.0).
     */
    public double getProbabilidadeFalhaAtual() {
        if (saude == 0.0) {
            return 1.0;
        }
        return Math.min(1.0, probabilidadeFalha * (SAUDE_MAX / saude));
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
     * Realiza o reparo completo do equipamento, restaurando a saúde para o máximo e status para desligada.
     */
    public void reparar() {
        status = StatusMaquina.DESLIGADA;
        saude = SAUDE_MAX;
    }

    /**
     * Avalia se a máquina necessita de manutenção preventiva ou corretiva
     * (se estiver quebrada ou com saúde abaixo do patamar mínimo configurado).
     *
     * @return {@code true} se necessitar de manutenção; {@code false} caso contrário.
     */
    @Override
    public boolean precisaManutencao() {
        return estaQuebrada() || saude < saudeMinima;
    }

    /**
     * Gera o relatório diagnóstico técnico contendo nome, status, integridade física, chance de falha e falhas acumuladas.
     *
     * @return String contendo o relatório de auditoria da máquina.
     */
    @Override 
    public String gerarRelatorioDiagnostico() {
        return "Máquina " + nome + " | " + status.getDescricao()
            + String.format(" | saúde %.1f | chance de falha %.0f%%", saude, getProbabilidadeFalhaAtual() * 100)
            + "| falhas " + historicoDeFalhas;
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
     * @param saudeMinima Novo valor de saúde mínima (entre 0.0 e {@link #SAUDE_MAX}).
     */
    public void setsaudeMinima(double saudeMinima) {
        if (saudeMinima >= 0 && saudeMinima <= SAUDE_MAX) {
            this.saudeMinima = saudeMinima;
        }
    }
}
