import java.util.Random;

public abstract class Maquina {
    // Atributos
    private String nome;
    private boolean ligada;
    private int capacidadeMaxima;
    private double probabilidadeFalha;
    private double custoOperacao;
    private Random random;

    // Construtor (inicializo)
    public Maquina(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao) {
        this.nome = nome;
        this.ligada = false;
        this.capacidadeMaxima = capacidadeMaxima;
        this.probabilidadeFalha = probabilidadeFalha;
        this.custoOperacao = custoOperacao;
        this.random = new Random();
    }

    // Métodos Abstratos
    public abstract void processar(Produto produto);
    public abstract String getTipo();

    // Métodos Concretos
    public void ligar() {
        this.ligada = true;
    }

    public void desligar() {
        this.ligada = false;
    }

    public boolean estaLigada() {
        return ligada;
    }

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
        // todo: conteudo que preciso fazer (usar Random ou probabilidadeFalha para verificar se houve falha)
        return random.nextDouble() < probabilidadeFalha;
    }
}
