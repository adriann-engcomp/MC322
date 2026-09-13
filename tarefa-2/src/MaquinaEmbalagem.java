public class MaquinaEmbalagem extends Maquina {
    private double chanceAumentarFalha; // Chance de Y% de aumentar a probabilidade de falha do produto

    // Construtor
    public MaquinaEmbalagem(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao, double chanceAumentarFalha) {
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao);
        // todo: conteudo que preciso fazer (inicializar chanceAumentarFalha)
    }

    @Override
    public void processar(Produto produto) {
        // todo: conteudo que preciso fazer
        // Não falha diretamente, mas tem Y% de chance de aumentar a probabilidade de falha acumulada do produto embalado
    }

    @Override
    public String getTipo() {
        // todo: conteudo que preciso fazer
        return "Máquina de Embalagem / Blister";
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
