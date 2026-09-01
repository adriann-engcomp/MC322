package model;

public class Maquina {
    // Atributos privados
    private String nome;
    private boolean ligada;
    private int capacidadeMaxima;

    // Construtor
    public Maquina(String nome, boolean ligada, int capacidadeMaxima) {
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.ligada = false;
    }

    // Métodos
    public void ligar() {
        this.ligada = true;
    }
    public void desligar() {
        this.ligada = false;
    }
    public void processar(MateriaPrima mp, double demanda) {
        if (!this.ligada){
            System.out.println("A máquina " + this.nome + " está desligada.");
            return;
        }
        if (demanda > this.capacidadeMaxima) {
            System.out.println("A demanda de " + demanda + " excede a capacidade máxima da máquina " + this.nome + " (" + this.capacidadeMaxima + ").");
            return;
        }
        // if (!mp.verificarDisponibilidade(demanda)) {
        //     System.out.println("Materia prima insuficiente para seguir com a produção na máquina'" + produto.getNome() + "'.'");
        //     return;
        // }

        mp.consumir(demanda);
        // Produto.processar();
        // System.out.println("Produção de " + Produto.getNome() + " concluída");
    }

    // Getters
    public String getNome() {
        return this.nome;
    }
    public boolean estaLigada() {
        return this.ligada;
    }
}
