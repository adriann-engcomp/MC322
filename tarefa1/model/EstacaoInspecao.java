package model;

public class EstacaoInspecao {
    // Atributos privados
    private boolean ativa;
    private int produtosInspecionados;

    // Construtor (incializo)
    public EstacaoInspecao(boolean ativa, int produtosInspecionados) {
        this.ativa = ativa;
        this.produtosInspecionados = produtosInspecionados;
    }

    // Métodos
    public void ativar() {
        this.ativa = true;
    }

    public void desativar() {
        this.ativa = false;
    }

    public void inspecionar(Produto produto) {
        if (this.ativa) {
            this.produtosInspecionados++;
        }
    }

    public int getTotalInspecionados() {
        return this.produtosInspecionados;
    }

    // Getters
    public boolean getAtivar() {
        return this.ativa;
    }
    public boolean getDesativar() {
        return !this.ativa;
    }
    public void getInspecionar(Produto produto) {
        if (this.ativa) {
            this.produtosInspecionados++;
        }
    }
    public int getProdutosInspecionados() {
        return this.produtosInspecionados;
    }
}
