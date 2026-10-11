// Qnt tenho de mp?

public class MateriaPrima {
    // Atributos
    private int id;
    private String nome;
    private double quantidade;
    private String unidade;
    private double custoPorUnidade;

    // Construtor
    public MateriaPrima(int id, String nome, double quantidade, String unidade, double custoPorUnidade) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.custoPorUnidade = custoPorUnidade;
    }

    // Métodos
    public boolean consumir(double qtd) {
        if (this.quantidade >= qtd) {
            this.quantidade -= qtd;
            return true;
        }
        return false;
    }

    public void adicionarEstoque(double qtd) {
        this.quantidade += qtd;
    }

    public boolean verificarDisponibilidade(double qtdNecessaria) {
        return this.quantidade >= qtdNecessaria;
    }

    // Getters e Setters
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

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public double getCustoPorUnidade() {
        return custoPorUnidade;
    }

    public void setCustoPorUnidade(double custoPorUnidade) {
        this.custoPorUnidade = custoPorUnidade;
    }
}
