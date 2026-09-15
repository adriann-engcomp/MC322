public class MaquinaProcessamento extends Maquina {
    private double chanceAumentarFalha; // Chance de X% de aumentar a probabilidade de falha do produto

    // Construtor
    public MaquinaProcessamento(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao, double chanceAumentarFalha) {
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao);
    }

    @Override
    public void processar(Produto produto) {
        // todo: conteudo que preciso fazer
        // Não falha diretamente, mas tem X% de chance de aumentar a probabilidade de falha acumulada do produto processado
    }

    @Override
    public String getTipo() {
        // todo: conteudo que preciso fazer
        return "Máquina de Processamento / Mistura Farmacêutica";
    }

    public double getChanceAumentarFalha() {
        // todo: conteudo que preciso fazer
        return chanceAumentarFalha;
    }

    public void setChanceAumentarFalha(double chanceAumentarFalha) {
        // todo: conteudo que preciso fazer
        this.chanceAumentarFalha = chanceAumentarFalha;
    }
}
