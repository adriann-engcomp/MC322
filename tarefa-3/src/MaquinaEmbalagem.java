public class MaquinaEmbalagem extends Maquina {
    
    private double chanceAumentarFalha; // Chance de Y% de aumentar a probabilidade de falha do produto

    // Construtor
    public MaquinaEmbalagem(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao, double chanceAumentarFalha) {
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao);
        this.chanceAumentarFalha = chanceAumentarFalha;
    }

    @Override
    public void processar(Produto produto) {
        if (produto == null || !estaLigada()){
            return;
        }
        // Não falha diretamente, mas tem chanceAumentarFalha (Y%) de aumentar a probabilidade de falha acumulada do produto
        if (random.nextDouble() < chanceAumentarFalha) {
            produto.aumentarProbabilidadeFalha(0.05);
        }
        produto.setStatus("Embalado");
    }

    @Override
    public String getTipo() {
        return "Máquina de Embalagem / Blister";
    }

    public double getChanceAumentarFalha() {
        return chanceAumentarFalha;
    }

    public void setChanceAumentarFalha(double chanceAumentarFalha) {
        this.chanceAumentarFalha = chanceAumentarFalha;
    }
}
