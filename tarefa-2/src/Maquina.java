import java.util.Random;

public abstract class Maquina {
    // Atributos
    private String nome;
    private boolean ligada;
    private int capacidadeMaxima;
    private double probabilidadeFalha;
    private double custoOperacao;

    // Construtor
    public Maquina(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao) {
        // todo: conteudo que preciso fazer (inicializar atributos, ligada inicia como false ou conforme desejado)
    }

    // Métodos Abstratos
    public abstract void processar(Produto produto);
    public abstract String getTipo();

    // Métodos Concretos
    public void ligar() {
        // todo: conteudo que preciso fazer
        this.ligada = true;
    }

    public void desligar() {
        // todo: conteudo que preciso fazer
        this.ligada = false;
    }

    public boolean estaLigada() {
        // todo: conteudo que preciso fazer
        return ligada;
    }

    public String getNome() {
        // todo: conteudo que preciso fazer
        return nome;
    }

    public void setNome(String nome) {
        // todo: conteudo que preciso fazer
        this.nome = nome;
    }

    public int getCapacidadeMaxima() {
        // todo: conteudo que preciso fazer
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        // todo: conteudo que preciso fazer
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public double getProbabilidadeFalha() {
        // todo: conteudo que preciso fazer
        return probabilidadeFalha;
    }

    public void setProbabilidadeFalha(double probabilidadeFalha) {
        // todo: conteudo que preciso fazer
        this.probabilidadeFalha = probabilidadeFalha;
    }

    public double getCustoOperacao() {
        // todo: conteudo que preciso fazer
        return custoOperacao;
    }

    public void setCustoOperacao(double custoOperacao) {
        // todo: conteudo que preciso fazer
        this.custoOperacao = custoOperacao;
    }

    // Método protegido com aleatoriedade
    protected boolean verificarFalha() {
        // todo: conteudo que preciso fazer (usar Random ou probabilidadeFalha para verificar se houve falha)
        return false;
    }
}
