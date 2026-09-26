import java.util.Random;

public class MaquinaEmbalagem extends Maquina {
    // Construtor
    public MaquinaEmbalagem(String nome, int capacidadeMaxima, 
        double probabilidadeFalha, double custoOperacao, 
        Cenario cenario, Random random) {
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao, cenario, random);
    }

    @Override
    public void processar(Produto produto) {
        if (produto == null || !estaLigada()){
            return;
        }
        // Se a maquina falhar (pouca saúde), então aumento o risco de produção
        if (verificarFalha()) {
            produto.aumentarProbabilidadeFalha(0.05);
        }
        produto.setStatus(StatusProduto.EMBALADO);
    }

    @Override
    public String getTipo() {
        return "Máquina de Embalagem / Blister";
    }
}
