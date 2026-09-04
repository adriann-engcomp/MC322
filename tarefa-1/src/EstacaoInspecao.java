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

    public boolean inspecionar(Produto produto) {
        if (!this.ativa) {
            System.out.println("A estação de inspeção está desativada e não pode realizar inspeções.");
            return false;
        }
        this.produtosInspecionados++;
        return true;
    }
    public int getTotalInspecionados() {
        return produtosInspecionados;
    }
}
