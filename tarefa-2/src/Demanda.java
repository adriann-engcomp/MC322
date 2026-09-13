public class Demanda {
    // Atributos
    private String tipoProduto;
    private int quantidadeProdutos;
    private boolean atendida;

    // Construtor
    public Demanda(String tipoProduto, int quantidadeProdutos) {
        // todo: conteudo que preciso fazer (inicializar tipoProduto, quantidadeProdutos e atendida = false)
    }

    // Métodos
    public void atualizarQuantidade(int novaQuantidade) {
        // todo: conteudo que preciso fazer (atualizar quantidadeProdutos)
    }

    public double calcularMateriaPrimaNecessaria(double materiaPrimaPorUnidade) {
        // todo: conteudo que preciso fazer (calcular materiaPrimaPorUnidade * quantidadeProdutos)
        return 0.0;
    }

    public void atender() {
        // todo: conteudo que preciso fazer (marcar atendida = true)
        this.atendida = true;
    }

    // Getters e Setters
    public String getTipoProduto() {
        // todo: conteudo que preciso fazer
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        // todo: conteudo que preciso fazer
        this.tipoProduto = tipoProduto;
    }

    public int getQuantidadeProdutos() {
        // todo: conteudo que preciso fazer
        return quantidadeProdutos;
    }

    public void setQuantidadeProdutos(int quantidadeProdutos) {
        // todo: conteudo que preciso fazer
        this.quantidadeProdutos = quantidadeProdutos;
    }

    public boolean isAtendida() {
        // todo: conteudo que preciso fazer
        return atendida;
    }

    public void setAtendida(boolean atendida) {
        // todo: conteudo que preciso fazer
        this.atendida = atendida;
    }
}
