public class Produtos {
    // Constantes para status
    public static final String STATUS_PROCESSADO = "PROCESSADO";


    // 1. Atributos (o que tenho?)
    private id;
    private nome;
    private status;
    private quantidadeMateriaPrimaNecessaria;

    // 2. Construtor + parâmetros de entrada (inicializa os campos)
    public Produtos(int id, String nome, String status, double quantidadeMateriaPrimaNecessaria) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.quantidadeMateriaPrimaNecessaria = quantidadeMateriaPrimaNecessaria;
    }


    //3. Metodos (o que faz?)
    public void processar() {
        // Contruo (inicializo) e imprimo que o produto foi processado
        this.status = STATUS_PROCESSADO;
        System.out.println("Produto " + nome + "'marcado como: " + status);

    }
}
