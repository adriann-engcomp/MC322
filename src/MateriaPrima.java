public class MateriaPrima {
    // 1. Atributos (o que tenho?)
    private int id;
    private String nome;
    private double quantidade;
    private String unidade;
    private double quantidadeMinima;

    // 2. Construtor + parâmetros de entrada (inicializa os campos)
    public MateriaPrima(int id, String nome, double quantidade, String unidade, double quantidadeMinima) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.quantidadeMinima = quantidadeMinima;
    }

    //3. Metodos (o que faz?)
    public void consumir(int quantidadeDemandada){
    }

    public void adicionarEstoque(int quantidadeAdicionada){
    }

    public boolean verificarDisponibilidade(int demanda){
        return false;
    }

    public int getId(){
        return 0;
    }

    public String getNome(){
        return null;
    }

    public int getQuantidade(){
        return 0;
    }

    public String getUnidade() {
        return null;
    }


}
