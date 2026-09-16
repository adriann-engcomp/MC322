public class MaquinaProcessamento extends Maquina {
    private double chanceAumentarFalha; // Chance de X% de aumentar a probabilidade de falha do produto

    // Construtor
    public MaquinaProcessamento(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao, double chanceAumentarFalha) {
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao);
        this.chanceAumentarFalha = chanceAumentarFalha;
    }

    @Override
    public void processar(Produto produto) {
        if (produto == null || !estaLigada()){
            return;
        }
        // Não falha diretamente, mas tem chanceAumentarFalha (X%) de aumentar a probabilidade de falha acumulada do produto
        if (random.nextDouble() < chanceAumentarFalha) {
            produto.aumentarProbabilidadeFalha(0.10);
        }
        produto.setStatus("Processado");
    }

    @Override
    public String getTipo() {
        return "Máquina de Processamento / Mistura Farmacêutica";
    }

    public double getChanceAumentarFalha() {
        return chanceAumentarFalha;
    }

    public void setChanceAumentarFalha(double chanceAumentarFalha) {
        this.chanceAumentarFalha = chanceAumentarFalha;
    }
}
