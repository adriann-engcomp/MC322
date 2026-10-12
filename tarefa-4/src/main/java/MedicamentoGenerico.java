/**
 * Representa um medicamento genérico de grande escala (por exemplo, analgésicos e antipiréticos como Dipirona e Paracetamol).
 * <p>
 * Apresenta custo de produção menor, tempo de produção rápido (5 minutos por unidade),
 * menor exigência de princípio ativo e critérios de aceitação mais flexíveis.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class MedicamentoGenerico extends Produto {

    /**
     * Construtor de medicamento genérico.
     *
     * @param nome Nome do medicamento (ex: "Dipirona 500mg").
     * @param lote Identificador do lote de produção.
     */
    public MedicamentoGenerico(String nome, String lote) {
        super(nome, lote, TipoMedicamento.GENERICO);
    }

    /**
     * Calcula o tempo de fabricação estimado (5.0 minutos por unidade).
     *
     * @param quantidade Quantidade de unidades a produzir.
     * @return Tempo total de produção em minutos.
     */
    @Override
    public double calcularTempoProducao(int quantidade) {
        return 5.0 * quantidade;
    }

    /**
     * Retorna o limiar de tolerância ao risco de falha para medicamentos genéricos (0.30).
     *
     * @return Limiar de tolerância de falha.
     */
    @Override 
    protected double getRiscoDeFalha() {
        return 0.30;
    }

    /**
     * Gera relatório diagnóstico específico indicando a dispensação flexível do medicamento.
     *
     * @return Diagnóstico formatado.
     */
    @Override
    public String gerarRelatorioDiagnostico() {
        return super.gerarRelatorioDiagnostico() + " | Ás vezes usa-se a receita (contínuo) ou não";
    }
}
