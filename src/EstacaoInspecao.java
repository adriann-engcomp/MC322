public class EstacaoInspecao {
    // 1. Atributos (o que tenho privado?)
    private boolean ativa;
    private int produtosInspecionados;

    // 2. Construtor (parâmetros de entrada) que inicializa os atributos
    public EstacaoInspecao(boolean ativa, int produtosInspecionados) {
        this.ativa = ativa;
        this.produtosInspecionados = produtosInspecionados;
    }

    //3. Metodos (o que faz?)
    public void ativar(){
        this.ativa = true;
        System.out.println("Estação de inspeção ativada.");
    }
    public void desativar(){
        this.ativa = false;
        System.out.println("Estação de inspeção desativada.");
    }
    public void inspecionar(){
        this.produtosInspecionados++;
        System.out.println("Produto inspecionado com sucesso!");
    }
    public int getTotalInspecionados(){
        return this.produtosInspecionados;
    }
}
