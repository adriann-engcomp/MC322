/**
 * Representa um medicamento de uso contínuo (por exemplo, anti-hipertensivos como Losartana ou hipoglicemiantes).
 * <p>
 * Caracteriza-se por qualidade intermediária (0.7), tempo de produção padrão de 6 minutos por unidade,
 * consumo médio de insumos e tolerância moderada ao risco de falha.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class MedicamentoContinuo extends Produto {

    /**
     * Construtor de medicamento de uso contínuo.
     *
     * @param nome Nome do medicamento (ex: "Losartana 50mg").
     * @param lote Identificador do lote de produção.
     */
    public MedicamentoContinuo(String nome, String lote) {
        super(nome, lote, TipoMedicamento.CONTINUO);
    }

    /**
     * Calcula o tempo de fabricação estimado (6.0 minutos por unidade).
     *
     * @param quantidade Quantidade de unidades a produzir.
     * @return Tempo total de produção em minutos.
     */
    @Override
    public double calcularTempoProducao(int quantidade) {
        return 6.0 * quantidade;
    }

    /**
     * Retorna o limiar de tolerância ao risco de falha para medicamentos contínuos (0.20).
     *
     * @return Limiar de tolerância de falha.
     */
    @Override 
    protected double getRiscoDeFalha() {
        return 0.20;
    }

    /**
     * Gera relatório diagnóstico específico indicando a necessidade de receita de uso contínuo.
     *
     * @return Diagnóstico formatado.
     */
    @Override
    public String gerarRelatorioDiagnostico() {
        return super.gerarRelatorioDiagnostico() + " | Precisa de receita constante";
    }
}