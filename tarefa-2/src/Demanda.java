public class Demanda {

    // Atributos
    private String tipoProduto;
    private int quantidadeProdutos;
    private boolean atendida;

    // Construtor
    public Demanda(String tipoProduto, int quantidadeProdutos) {
        this.tipoProduto = tipoProduto;
        this.quantidadeProdutos = quantidadeProdutos;
        this.atendida = false;
    }

    // Métodos
    public void atualizarQuantidade(int novaQuantidade) {
        this.quantidadeProdutos = novaQuantidade;
        this.atendida = false;
    }

    public double calcularMateriaPrimaNecessaria(double materiaPrimaPorUnidade) {
        return materiaPrimaPorUnidade * quantidadeProdutos;
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
