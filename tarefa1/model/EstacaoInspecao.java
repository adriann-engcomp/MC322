package model;

public class EstacaoInspecao {
    private boolean ativa;
    private int produtosInspecionados;

    public EstacaoInspecao() {
        this.ativa = false;
        this.produtosInspecionados = 0;
    }

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

    public boolean isAtiva() {
        return this.ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public int getProdutosInspecionados() {
        return this.produtosInspecionados;
    }

    public void setProdutosInspecionados(int produtosInspecionados) {
        this.produtosInspecionados = produtosInspecionados;
    }
}
