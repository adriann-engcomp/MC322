public class MedicamentoMediaQualidade extends Produto {

    // Construtor
    public MedicamentoMediaQualidade(int id, String nome, String status, double quantidadeMateriaPrimaPorUnidade) {
        super(id, nome, status, quantidadeMateriaPrimaPorUnidade, 0.7);
        // todo: conteudo que preciso fazer (qualidade fixa em 0.7 e demanda média de matéria-prima)
    }

    @Override
    public void processar() {
        // todo: conteudo que preciso fazer (define o processamento específico de produto de média qualidade)
    }

    @Override
    public double calcularTempoProducao() {
        // todo: conteudo que preciso fazer (tempo de produção para medicamento de média qualidade)
        return 0.0;
    }

    @Override
    public String getTipo() {
        // todo: conteudo que preciso fazer (retornar o tipo do produto, ex: "Medicamento de Média Qualidade")
        return "Medicamento de Média Qualidade";
    }
}
