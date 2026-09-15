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
        this.id = id;
        this.nome = nome;
        this.status = "Aguardando";
        this.quantidadeMateriaPrimaPorUnidade = quantidadeMateriaPrimaPorUnidade;
        this.qualidade = qualidade;
        this.probabilidadeFalhaAcumulada = 0;
        totalProdutosFabricados++;

    }

    // Métodos Abstratos
    public abstract void processar();
    public abstract double calcularTempoProducao(int quantidade);
    public abstract String getTipo();

    // Métodos Concretos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getQuantidadeMateriaPrimaPorUnidade() {
        return quantidadeMateriaPrimaPorUnidade;
    }

    public void setQuantidadeMateriaPrimaPorUnidade(double quantidadeMateriaPrimaPorUnidade) {
        this.quantidadeMateriaPrimaPorUnidade = quantidadeMateriaPrimaPorUnidade;
    }

    public double getQualidade() {
        return qualidade;
    }

    public void setQualidade(double qualidade) {
        this.qualidade = qualidade;
    }

    public double getProbabilidadeFalhaAcumulada() {
        return probabilidadeFalhaAcumulada;
    }

    public void setProbabilidadeFalhaAcumulada(double probabilidadeFalhaAcumulada) {
        this.probabilidadeFalhaAcumulada = probabilidadeFalhaAcumulada;
    }

    public void aumentarProbabilidadeFalha(double incremento) {
        probabilidadeFalhaAcumulada += incremento;
    }

    public static int getTotalProdutosFabricados() {
        return totalProdutosFabricados;
    }

    public static void setTotalProdutosFabricados(int total) {
        totalProdutosFabricados = total;
    }
}