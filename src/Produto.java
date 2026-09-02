public class Produto {

    private int id;
    private String nome;
    private boolean status;
    private double quantidadeMateriaPrimaNecessaria;

    public Produto(int id, String nome, double quantidadeMateriaPrimaNecessaria) {
        this.id = id;
        this.nome = nome;
        this.quantidadeMateriaPrimaNecessaria = quantidadeMateriaPrimaNecessaria;
        this.status = false;
    }

    public void processar() {
        this.status = true;
    }
    public void definirDemandaMateriaPrima(double quantidade) {
        this.quantidadeMateriaPrimaNecessaria = quantidade;
    }

    public double getDemandaMateriaPrima() {
        return quantidadeMateriaPrimaNecessaria;
    }
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public boolean getStatus() {
        return status;
    }
}
