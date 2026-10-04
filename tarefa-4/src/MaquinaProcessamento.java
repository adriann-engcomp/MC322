import java.util.Random;

public class MaquinaProcessamento extends Maquina {
    // Construtor
    public MaquinaProcessamento(String nome, int capacidadeMaxima,
        double probabilidadeFalha, double custoOperacao, 
        Cenario cenario, Random random) {
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao, cenario, random);
    }

    @Override
    public void processar(Produto produto) {
        if (produto == null || !estaLigada()){
            return;
        }
        // Não falha diretamente, mas tem chanceAumentarFalha (X%) de aumentar a probabilidade de falha acumulada do produto
        if (verificarFalha()) {
            produto.aumentarProbabilidadeFalha(0.10);
        }
        produto.setStatus(StatusProduto.PROCESSADO);
    }

    @Override
    public String getTipo() {
        return "Máquina de Processamento / Mistura Farmacêutica";
    }
}
