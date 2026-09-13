public class MateriaPrima {
    // Atributos
    private int id;
    private String nome;
    private double quantidade;
    private String unidade;
    private double custoPorUnidade;

    // Construtor
    public MateriaPrima(int id, String nome, double quantidade, String unidade, double custoPorUnidade) {
        // todo: conteudo que preciso fazer (inicializar atributos)
    }

    // Métodos
    public boolean consumir(double qtd) {
        // todo: conteudo que preciso fazer (verificar se há estoque disponível e subtrair se possível)
        return false;
    }

    public void adicionarEstoque(double qtd) {
        // todo: conteudo que preciso fazer (somar a quantidade ao estoque)
    }

    public boolean verificarDisponibilidade(double qtdNecessaria) {
        // todo: conteudo que preciso fazer (retornar se a quantidade atual atende à necessária)
        return false;
    }

    // Getters e Setters
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

    public double getQuantidade() {
        // todo: conteudo que preciso fazer
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        // todo: conteudo que preciso fazer
        this.quantidade = quantidade;
    }

    public String getUnidade() {
        // todo: conteudo que preciso fazer
        return unidade;
    }

    public void setUnidade(String unidade) {
        // todo: conteudo que preciso fazer
        this.unidade = unidade;
    }

    public double getCustoPorUnidade() {
        // todo: conteudo que preciso fazer
        return custoPorUnidade;
    }

    public void setCustoPorUnidade(double custoPorUnidade) {
        // todo: conteudo que preciso fazer
        this.custoPorUnidade = custoPorUnidade;
    }
}
