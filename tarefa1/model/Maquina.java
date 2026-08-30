package model;

public class Maquina {
    private int id;
    private boolean ligada;
    private String nome;
    private int capacidadeMaxima;

    public Maquina() {
    }

    public Maquina(int id, String nome, int capacidadeMaxima) {
        this.id = id;
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.ligada = false;
    }

    public void ligar() {
        this.ligada = true;
    }

    public void desligar() {
        this.ligada = false;
    }

    public void processar(MateriaPrima materiaPrima, int demanda) {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean estaLigada() {
        return this.ligada;
    }

    public void setLigada(boolean ligada) {
        this.ligada = ligada;
    }

    public int getCapacidadeMaxima() {
        return this.capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }
}
