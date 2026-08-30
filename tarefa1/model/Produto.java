package model;

public class Produto {
    private int id;
    private String nome;
    private boolean status;
    private int quantidadeMateriaPrimaNecessaria;

    public Produto(int id, String nome, int quantidadeMateriaPrimaNecessaria) {
        this.id = id;
        this.nome = nome;
        this.quantidadeMateriaPrimaNecessaria = quantidadeMateriaPrimaNecessaria;
        this.status = false;
    }

    public void processar() {
        // criar lógica
    }

    public void definirDemandaMateriaPrima(int quantidade) {
        this.quantidadeMateriaPrimaNecessaria = quantidade;
    }

    public int getDemandaMateriaPrima() {
        return this.quantidadeMateriaPrimaNecessaria;
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

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getQuantidadeMateriaPrimaNecessaria() {
        return this.quantidadeMateriaPrimaNecessaria;
    }

    public void setQuantidadeMateriaPrimaNecessaria(int quantidadeMateriaPrimaNecessaria) {
        this.quantidadeMateriaPrimaNecessaria = quantidadeMateriaPrimaNecessaria;
    }
}
