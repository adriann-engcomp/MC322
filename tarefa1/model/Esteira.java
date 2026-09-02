package model;

public class Esteira {
    // Atributos privados
    private Object item;
    private boolean emMovimento;
    private int capacidadeMaxima;

    // Construtor
    public Esteira(Object item, boolean emMovimento, int capacidadeMaxima) {
        this.item = item;
        this.emMovimento = emMovimento;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    // Métodos
    public void ligar() {
        this.emMovimento = true;
    }

    public void desligar() {
        this.emMovimento = false;
    }

    public void adicionarItem(Object item) {
        this.item = item;
    }

    public Object removerItem() {
        Object itemRemovido = this.item;
        this.item = null;
        return itemRemovido;
    }

    public boolean verificarCapacidade(Object item) {
        return false;
    }

    // Getters
    public Object getItem() {
        return this.item;
    }
    public boolean getemMovimento() {
        return this.emMovimento;
    }
    public int getCapacidadeMaxima() {
        return this.capacidadeMaxima;
    }
}
