import java.util.Random;

/**
 * Representa a etapa intermediária da esteira de fabricação farmacêutica: a embaladora de blister e selagem.
 * <p>
 * Responsável por selar hermeticamente os comprimidos. Se ocorrer falha na selagem térmica,
 * o medicamento sofre risco de contaminação ou vedação inadequada, aumentando sua chance
 * de falha acumulada em 0.05. Ao final, atualiza o status do produto para {@link StatusProduto#EMBALADO}.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class MaquinaEmbalagem extends Maquina {

    /**
     * Construtor da máquina de embalagem e blister.
     *
     * @param nome               Nome do equipamento (ex: "Embaladora Blister / Selagem").
     * @param capacidadeMaxima   Capacidade máxima de processamento de unidades.
     * @param probabilidadeFalha Probabilidade base de selagem defeituosa.
     * @param custoOperacao      Custo operacional por unidade embalada.
     * @param cenario            Cenário de operação da fábrica.
     * @param random             Gerador de números pseudoaleatórios.
     */
    public MaquinaEmbalagem(String nome, int capacidadeMaxima, 
        double probabilidadeFalha, double custoOperacao, 
        Cenario cenario, Random random) {
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao, cenario, random);
    }

    /**
     * Empacota uma unidade de produto em embalagem blister.
     * Se falhar na selagem, eleva a probabilidade de falha acumulada do produto em 0.05.
     * Atualiza o status do produto para {@link StatusProduto#EMBALADO}.
     *
     * @param produto Unidade a ser embalada.
     */
    @Override
    public void processar(Produto produto) {
        if (produto == null || !estaLigada()) {
            return;
        }
        if (verificarFalha()) {
            produto.aumentarProbabilidadeFalha(0.05);
        }
        produto.setStatus(StatusProduto.EMBALADO);
    }

    /**
     * Retorna a descrição do tipo de equipamento.
     *
     * @return Denominação do posto de embalagem.
     */
    @Override
    public String getTipo() {
        return "Máquina de Embalagem / Blister";
    }
}
