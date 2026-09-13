public abstract class Produto {
    // Atributos
    private int id;
    private String nome;
    private String status;
    private double quantidadeMateriaPrimaPorUnidade;
    private double qualidade; // 0.0 a 1.0
    private double probabilidadeFalhaAcumulada;
    private static int totalProdutosFabricados = 0;

    // Construtor
    public Produto(int id, String nome, String status, double quantidadeMateriaPrimaPorUnidade, double qualidade) {
        // todo: conteudo que preciso fazer (inicializar atributos e incrementar totalProdutosFabricados)
    }

    // Métodos Abstratos
    public abstract void processar();
    public abstract double calcularTempoProducao();
    public abstract String getTipo();

    // Métodos Concretos
    public int getId() {
        // todo: conteudo que preciso fazer
        return id;
    }

    public void setId(int id) {
        // todo: conteudo que preciso fazer
        this.id = id;
    }

    public String getNome() {
        // todo: conteudo que preciso fazer
        return nome;
    }

    public void setNome(String nome) {
        // todo: conteudo que preciso fazer
        this.nome = nome;
    }

    public String getStatus() {
        // todo: conteudo que preciso fazer
        return status;
    }

    public void setStatus(String status) {
        // todo: conteudo que preciso fazer
        this.status = status;
    }

    public double getQuantidadeMateriaPrimaPorUnidade() {
        // todo: conteudo que preciso fazer
        return quantidadeMateriaPrimaPorUnidade;
    }

    public void setQuantidadeMateriaPrimaPorUnidade(double quantidadeMateriaPrimaPorUnidade) {
        // todo: conteudo que preciso fazer
        this.quantidadeMateriaPrimaPorUnidade = quantidadeMateriaPrimaPorUnidade;
    }

    public double getQualidade() {
        // todo: conteudo que preciso fazer
        return qualidade;
    }

    public void setQualidade(double qualidade) {
        // todo: conteudo que preciso fazer
        this.qualidade = qualidade;
    }

    public double getProbabilidadeFalhaAcumulada() {
        // todo: conteudo que preciso fazer
        return probabilidadeFalhaAcumulada;
    }

    public void setProbabilidadeFalhaAcumulada(double probabilidadeFalhaAcumulada) {
        // todo: conteudo que preciso fazer
        this.probabilidadeFalhaAcumulada = probabilidadeFalhaAcumulada;
    }

    public void aumentarProbabilidadeFalha(double incremento) {
        // todo: conteudo que preciso fazer (somar o incremento à probabilidadeFalhaAcumulada)
    }

    public static int getTotalProdutosFabricados() {
        // todo: conteudo que preciso fazer
        return totalProdutosFabricados;
    }

    public static void setTotalProdutosFabricados(int total) {
        // todo: conteudo que preciso fazer
        totalProdutosFabricados = total;
    }
}