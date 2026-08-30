package model;

public class MateriaPrima {
    private int id;
    private String nome;
    private int quantidade;
    private String unidade;
    private int quantidadeMinima;

    public MateriaPrima(int id, String nome, int quantidade, String unidade, int quantidadeMinima) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.quantidadeMinima = quantidadeMinima;
    }

    public void consumir(int quantidadeDemandada) {
    }

    public void adicionarEstoque(int quantidadeAdicionada) {
    }

    public boolean verificarDisponibilidade(int demanda) {
        return this.quantidade >= demanda;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getUnidade() {
        return this.unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public int getQuantidadeMinima() {
        return this.quantidadeMinima;
    }

    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }
}
