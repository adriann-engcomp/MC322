public class MateriaPrima {

    private int id;
    private String nome;
    private double quantidade;
    private String unidade;
    private double quantidadeMinima;

    public MateriaPrima(int id, String nome, double quantidade, String unidade, double quantidadeMinima) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.quantidadeMinima = quantidadeMinima;
    }

    public boolean verificarDisponibilidade(double demanda) {
        return this.quantidade >= demanda;
    }
    public void consumir(double demanda) {
        // Tem o suficiente para atender a demanda?
        if (verificarDisponibilidade(demanda)) {
            // desconto
            this.quantidade -= demanda;
            System.out.println("Consumidos " + demanda + " " + this.unidade + " de " + this.nome + ". Quantidade restante: " + this.quantidade + " " + this.unidade);
        } else {
            System.out.println("Estoque insuficiente.");
        }
    }
    public void adicionarEstoque(double quantidadeExtra) {
        this.quantidade += quantidadeExtra;
        System.out.println("Adicionados " + quantidadeExtra + " " + this.unidade + ". Total: " + this.quantidade);
    }


    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public double getQuantidade() {
        return quantidade;
    }
    public String getUnidade() {
        return unidade;
    }
    public double getQuantidadeMinima() {
        return quantidadeMinima;
    }
}
