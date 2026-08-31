package model;

public class Esteira {
    private Object item;
    private boolean emMovimento;
    private int capacidadeMaxima;


    public Esteira(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
        this.emMovimento = false;
    }

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

    public Object getItem() {
        return this.item;
    }

    public boolean isEmMovimento() {
        return this.emMovimento;
    }

    public int getCapacidadeMaxima() {
        return this.capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }
}
