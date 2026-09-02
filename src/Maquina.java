public class Maquina {

    private String nome;
    private boolean ligada;
    private double capacidadeMaxima;

    public Maquina(String nome, double capacidadeMaxima) {
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        // A maquina inicia ligada
        this.ligada = true;
    }

    public void ligar() {
        this.ligada = true;
    }
    public void desligar() {
        this.ligada = false;
    }
    public boolean processar(MateriaPrima mp, double demanda) {
        if (!this.ligada) {
            System.out.println("A máquina " + this.nome + " está desligada e não pode processar.");
            return false;
        }
        if (demanda > this.capacidadeMaxima) {
            System.out.println("A demanda de " + demanda + " excede a capacidade máxima da máquina " + this.nome + " (" + this.capacidadeMaxima + ").");
            return false;
        }
        if (!mp.verificarDisponibilidade(demanda)) {
            System.out.println("Matéria-prima insuficiente para seguir com a produção na máquina " + this.nome + ".");
            return false;
        }
        mp.consumir(demanda);
        return true;
    }
    public boolean processar(Produto produto, MateriaPrima mp, double demanda) {
        if (processar(mp, demanda)) {
            produto.processar();
            return true;
        }
        return false;
    }

    public String getNome() {
        return nome;
    }
    public double getCapacidadeMaxima() {
        return capacidadeMaxima;
    }
}
