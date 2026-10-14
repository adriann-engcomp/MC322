import java.util.Random;

/**
 * Representa a máquina inicial da esteira de fabricação farmacêutica: o reator de processamento e mistura.
 * <p>
 * Responsável pela homogeneização físico-química dos princípios ativos.
 * Caso a máquina falhe na mistura (probabilidade variável conforme sua saúde), ela introduz
 * uma imperfeição no produto, elevando sua chance de rejeição posterior em 0.10.
 * Ao final do processamento, atualiza o status do produto para {@link StatusProduto#PROCESSADO}.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class MaquinaProcessamento extends Maquina {

    /**
     * Construtor da máquina de processamento e mistura.
     *
     * @param nome               Nome do equipamento (ex: "Reator Farmacêutico / Mistura").
     * @param capacidadeMaxima   Capacidade máxima de processamento de unidades.
     * @param probabilidadeFalha Probabilidade base de mistura irregular.
     * @param custoOperacao      Custo operacional por unidade processada.
     * @param cenario            Cenário de operação da fábrica.
     * @param random             Gerador de números pseudoaleatórios.
     */
    public MaquinaProcessamento(String nome, int capacidadeMaxima,
        double probabilidadeFalha, double custoOperacao, 
        Cenario cenario, Random random) {
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao, cenario, random);
    }

    /**
     * Processa uma unidade de medicamento no reator de mistura.
     * Se a máquina estiver ligada e falhar na homogeneização, incrementa o risco de falha do produto em 0.10.
     * Atualiza o status do produto para {@link StatusProduto#PROCESSADO}.
     *
     * @param produto Unidade a ser processada.
     */
    @Override
    public void processar(Produto produto) {
        if (produto == null || !estaLigada()) {
            return;
        }
        if (verificarFalha()) {
            produto.aumentarProbabilidadeFalha(0.10);
        }
        produto.setStatus(StatusProduto.PROCESSADO);
    }

    /**
     * Retorna a descrição do tipo de equipamento.
     *
     * @return Denominação do posto de processamento.
     */
    @Override
    public String getTipo() {
        return "Máquina de Processamento / Mistura Farmacêutica";
    }
}
