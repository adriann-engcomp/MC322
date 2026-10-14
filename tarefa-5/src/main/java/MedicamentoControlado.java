/**
 * Representa um medicamento de controle especial / tarja preta ou vermelha (por exemplo, Ritalina).
 * <p>
 * Apresenta alto rigor de qualidade (0.9), maior tempo de produção (10 minutos por unidade),
 * maior consumo de princípio ativo e baixíssima tolerância a falhas na inspeção.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class MedicamentoControlado extends Produto {

    /**
     * Construtor de medicamento controlado.
     *
     * @param nome Nome do medicamento (ex: "Ritalina 40mg").
     * @param lote Identificador do lote de produção.
     */
    public MedicamentoControlado(String nome, String lote) {
        super(nome, lote, TipoMedicamento.CONTROLADO);
    }

    /**
     * Calcula o tempo de fabricação estimado (10.0 minutos por unidade).
     *
     * @param quantidade Quantidade de unidades a produzir.
     * @return Tempo total de produção em minutos.
     */
    @Override
    public double calcularTempoProducao(int quantidade) {
        return 10.0 * quantidade;
    }

    /**
     * Retorna o limiar estrito de tolerância ao risco de falha para medicamentos controlados (0.10).
     *
     * @return Limiar de tolerância de falha.
     */
    @Override 
    protected double getRiscoDeFalha() {
        return 0.10;
    }

    /**
     * Gera relatório diagnóstico específico indicando a exigência de receita especial controlada.
     *
     * @return Diagnóstico formatado.
     */
    @Override
    public String gerarRelatorioDiagnostico() {
        return super.gerarRelatorioDiagnostico() + " | Precisa de uma receita especial controlada";
    }
}
