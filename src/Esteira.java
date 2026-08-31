public class Esteira {
    // 1. Atributos (o que tenho privado?)
    private String item;
    private boolean emMovimento;
    private double capacidadeMaxima;

    // 2. Construtor(parâmetros de entrada) que inicializa os atributos
    public Esteira(String item, boolean emMovimento, double capacidadeMaxima) {
        this.item = item;
        this.emMovimento = emMovimento;
        this.capacidadeMaxima = capacidadeMaxima; 
    }

    // 3. Metodos (o que faz?)
    public void ligar() {
        this.emMovimento = true;
        System.out.println("Esteira ligada.");
    }
    public void desligar() {
        this.emMovimento = false;
        System.out.println("Esteira desligada.");
    }
    

}