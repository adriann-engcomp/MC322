import java.util.Random;

/**
 * Representa a etapa final da esteira de produção farmacêutica: o espectrômetro de inspeção e controle de qualidade Anvisa.
 * <p>
 * Avalia se o medicamento produzido atende aos rigorosos critérios regulatórios de liberação.
 * A decisão de aprovação ou rejeição leva em consideração as falhas acumuladas nas etapas anteriores
 * e o índice de qualidade farmacêutica da formulação. Além disso, a própria máquina de inspeção pode
 * sofrer falha de calibração espectrométrica, invertendo o diagnóstico (rejeitando um bom lote ou aprovando um defeituoso).
 * Define o status final como {@link StatusProduto#APROVADO} ou {@link StatusProduto#REJEITADO}.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class MaquinaInspecao extends Maquina {

    /**
     * Construtor da máquina de inspeção e controle de qualidade.
     *
     * @param nome               Nome do equipamento (ex: "Espectrômetro de Inspeção / Controle Anvisa").
     * @param capacidadeMaxima   Capacidade máxima de inspeção de unidades.
     * @param probabilidadeFalha Probabilidade base de falha no diagnóstico espectrométrico.
     * @param custoOperacao      Custo operacional por unidade inspecionada.
     * @param cenario            Cenário de operação da fábrica.
     * @param random             Gerador de números pseudoaleatórios.
     */
    public MaquinaInspecao(String nome, int capacidadeMaxima, 
        double probabilidadeFalha, double custoOperacao,
        Cenario cenario, Random random) {
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao, cenario, random);
    }

    /**
     * Inspeciona uma unidade de produto avaliando seus defeitos acumulados e exigências de qualidade.
     * Atualiza o status do produto para {@link StatusProduto#APROVADO} ou {@link StatusProduto#REJEITADO}.
     *
     * @param produto Unidade a ser inspecionada.
     */
    @Override
    public void processar(Produto produto) {
        if (produto == null || !estaLigada()) {
            return;
        }

        // Chance de rejeição proporcional à falha acumulada e ao rigor de qualidade
        double chanceRejeicao = produto.getProbabilidadeFalhaAcumulada() * (produto.getQualidade() / 0.5);
        boolean defeituoso = random.nextDouble() < chanceRejeicao;

        // Se a máquina de inspeção falhar, inverte a decisão
        boolean maquinaFalhou = verificarFalha();
        boolean aprovado = maquinaFalhou ? defeituoso : !defeituoso;

        if (aprovado) {
            produto.setStatus(StatusProduto.APROVADO);
        } else {
            produto.setStatus(StatusProduto.REJEITADO);
        }
    }

    /**
     * Retorna a descrição do tipo de equipamento.
     *
     * @return Denominação do posto de inspeção.
     */
    @Override
    public String getTipo() {
        return "Máquina de Inspeção / Controle de Qualidade";
    }
}
