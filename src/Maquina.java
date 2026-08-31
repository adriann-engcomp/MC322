public class Maquina {
    // 1. Atributos (o que tenho privado?)
    private String nome;
    private boolean ligada;
    private int capacidadeMaxima;

    // 2. Construtor + parâmetros de entrada (inicializa os atributos)
    public Maquina(String nome, boolean ligada, int capacidadeMaxima) {
        this.nome = nome;
        this.ligada = ligada;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    // 3. Metodos (o que faz?)
    public void Ligar() {
        this.ligada = true;
        System.out.println("Máquina " + nome + " ligada.");
    }
    public void Desligar() {
        this.ligada = false;
        System.out.println("Máquina " + nome + " desligada.");
    }
    public void processar(MateriaPrima materiaPrima, int quantidadeMinima) {
    }
}

