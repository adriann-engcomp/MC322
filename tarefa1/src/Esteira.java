package src;

public class Esteira {

    // Atributos privados
    private Object item;
    private boolean emMovimento;
    private double capacidadeMaxima;

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

    public boolean adicionarItem(Object item) {
        if (!this.emMovimento) {
            System.out.println("A esteira não está em movimento. Ligue a esteira.");
            return false;
        }

        if (this.item != null) {
            System.out.println("A esteira já contém um item e não pode aceitar outro.");
            return false;
        }
        this.item = item;
        return true;
    }

    public Object removerItem() {
        Object itemRemovido = this.item;
        this.item = null;
        return itemRemovido;
    }

    //???
    public boolean verificarCapacidade(double peso) {
        return peso <= this.capacidadeMaxima;
    }

    // Getters
    public Object getItem() {
        return item;
    }

    public double getCapacidadeMaxima() {
        return this.capacidadeMaxima;
    }
}
