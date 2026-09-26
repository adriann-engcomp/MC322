import java.util.Random;

public abstract class Maquina implements Auditavel {
    // Atributos
    private String nome;
    private StatusMaquina status;
    private int capacidadeMaxima;
    private double probabilidadeFalha;
    private double custoOperacao;
    protected Random random;

    // Tarefa3
    public static final double SAUDE_MAX = 100.0;
    private double saude;
    private double desgasteMinimo;
    private double desgasteMaximo;
    private double saudeMinima;
    private int historicoDeFalhas;


    // Construtor (inicializo)
    public Maquina(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao,
        Cenario cenario, Random random) {
        this.nome = nome;
        this.status = StatusMaquina.DESLIGADA;
        this.capacidadeMaxima = capacidadeMaxima;
        this.probabilidadeFalha = Math.min(1.0, probabilidadeFalha * cenario.getFatorFalha());
        this.custoOperacao = custoOperacao;
        this.random = random;
        // Tarefa3
        this.saude = SAUDE_MAX;
        this.desgasteMinimo = cenario.getDesgasteMinimo();
        this.desgasteMaximo = cenario.getDesgasteMaximo();
        this.saudeMinima = 30.0;
        this.historicoDeFalhas = 0;

    }

    // Métodos Abstratos
    public abstract void processar(Produto produto);
    public abstract String getTipo();

    // Métodos Concretos
    public void ligar() {
        if (status == StatusMaquina.QUEBRADA){
            return;
        }
        this.status = StatusMaquina.LIGADA;
    }

    public void desligar() {
        if (status == StatusMaquina.LIGADA){
            this.status = StatusMaquina.DESLIGADA;
        }
    }

    public boolean estaLigada() {
        return status == StatusMaquina.LIGADA;
    }

    // Getters e setters antigos
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public double getProbabilidadeFalha() {
        return probabilidadeFalha;
    }

    public void setProbabilidadeFalha(double probabilidadeFalha) {
        this.probabilidadeFalha = probabilidadeFalha;
    }

    public double getCustoOperacao() {
        return custoOperacao;
    }

    public void setCustoOperacao(double custoOperacao) {
        this.custoOperacao = custoOperacao;
    }

    // Método protegido com aleatoriedade
    protected boolean verificarFalha() {
        boolean falhou = random.nextDouble() < getProbabilidadeFalhaAtual();
        if (falhou){
            historicoDeFalhas++;
        }
        return falhou;
    }

    // Tarefa3: Desgaste
    // Se o lote foi fabricado, defino o desgaste da máquina, sua saúde e retorno a saúde atual da máquina
    public double registrarCicloDeUso(){
        double desgaste = desgasteMinimo + random.nextDouble() * (desgasteMaximo - desgasteMinimo);
        saude = Math.max(0.0, saude - desgaste);
        if (saude == 0.0){
            status = StatusMaquina.QUEBRADA;
        }
        return desgaste;
    }

    // Menor saúde da máquina -> maior probabiblidade de falha no processo atual
    public double getProbabilidadeFalhaAtual(){
        if (saude == 0.0){
            return 1.0;
        }
        return Math.min(1.0, probabilidadeFalha * (SAUDE_MAX / saude));
    }

    public boolean estaQuebrada(){
        return status == StatusMaquina.QUEBRADA;
    }

    public void reparar(){
        status = StatusMaquina.DESLIGADA;
        saude = SAUDE_MAX;
    }

    // Tarefa3: Métodos para a interface Auditável
    @Override
    public boolean precisaManutencao(){
        return estaQuebrada() || saude < saudeMinima;
    }

    @Override 
    public String gerarRelatorioDiagnostico(){
        return "Máquina " + nome + " | " + status.getDescricao()
        + String.format(" | saúde %.1f | chance de falha %.of%%", saude, getProbabilidadeFalhaAtual() * 100)
        + "| falhas " + historicoDeFalhas;
    }

    // Getters e setters novos
    public StatusMaquina getStatus(){
        return status;
    }

    public double getSaude(){
        return saude;
    }

    public int getHistoricoDeFalhas(){
        return historicoDeFalhas;
    }

    public double getsaudeMinima(){
        return saudeMinima;
    }

    public void setsaudeMinima(double saudeMinima){
        if (saudeMinima >= 0 && saudeMinima <= SAUDE_MAX){
            this.saudeMinima = saudeMinima;
        }
    }
    
}
