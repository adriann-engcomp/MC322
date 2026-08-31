package model;

public class Produto {

    // Atributos privados
    private int id;
    private String nome;
    private boolean status;
    private double quantidadeMateriaPrimaNecessaria;

    // Construtor
    public Produto(int id, String nome, double quantidadeMateriaPrimaNecessaria) {
        this.id = id;
        this.nome = nome;
        this.quantidadeMateriaPrimaNecessaria = quantidadeMateriaPrimaNecessaria;
        this.status = false;
    }

    // Métodos
    public void processar() {
        this.status = true;
        System.out.println("Produto " + nome + " marcado como: PROCESSADO");
    }
    public void definirDemandaMateriaPrima(double quantidade) {
        this.quantidadeMateriaPrimaNecessaria = quantidade;
    }

    // Getters
    public double getDemandaMateriaPrimaNecessaria() {
        return this.quantidadeMateriaPrimaNecessaria;
    }
    public int getId() {
        return this.id;
    }
    public String getNome() {
        return this.nome;
    }
    public boolean getStatus() {
        return this.status;
    }
}