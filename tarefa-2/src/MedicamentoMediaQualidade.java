public class MedicamentoMediaQualidade extends Produto {

    // Construtor
    public MedicamentoMediaQualidade(int id, String nome, String status, double quantidadeMateriaPrimaPorUnidade) {
        super(id, nome, status, quantidadeMateriaPrimaPorUnidade, 0.7);
    }

    @Override
    public void processar() {
        setStatus("Processando");
    }

    @Override
    public double calcularTempoProducao(int quantidade) {
        return 6.0 * quantidade; // tempo(min)*quantidade(unidades)
    }

    @Override
    public String getTipo() {
        // todo: conteudo que preciso fazer (retornar o tipo do produto, ex: "Medicamento de Média Qualidade")
        return "Medicamento de Média Qualidade";
    }
}
