public class Demanda {
    // Atributos
    private String tipoProduto;
    private int quantidadeProdutos;
    private boolean atendida;

    // Construtor
    public Demanda(String tipoProduto, int quantidadeProdutos) {
        // todo: conteudo que preciso fazer (inicializar tipoProduto, quantidadeProdutos e atendida = false)
        this.tipoProduto = tipoProduto;
        this.quantidadeProdutos = quantidadeProdutos;
        this.atendida = false;
    }

    // Métodos
    public void atualizarQuantidade(int novaQuantidade) {
        // todo: conteudo que preciso fazer (atualizar quantidadeProdutos)
        novaQuantidade += quantidadeProdutos;
    }
    public double calcularMateriaPrimaNecessaria(double materiaPrimaPorUnidade) {
        // todo: conteudo que preciso fazer (calcular materiaPrimaPorUnidade * quantidadeProdutos)
        return materiaPrimaPorUnidade *= quantidadeProdutos;
    }

    public void atender() {
        this.atendida = true;
    }

    // Getters e Setters
    // TipoProduto
    public String getTipoProduto() {
        return tipoProduto;
    }
    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    // QuantidadeProdutos
    public int getQuantidadeProdutos() {
        return quantidadeProdutos;
    }
    public void setQuantidadeProdutos(int quantidadeProdutos) {
        this.quantidadeProdutos = quantidadeProdutos;
    }

    // Atendida
    public boolean isAtendida() {
        return atendida;
    }
    public void setAtendida(boolean atendida) {
        this.atendida = atendida;
    }
}
