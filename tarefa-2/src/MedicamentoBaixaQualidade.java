public class MedicamentoBaixaQualidade extends Produto {

    // Construtor
    public MedicamentoBaixaQualidade(int id, String nome, String status, double quantidadeMateriaPrimaPorUnidade) {
        super(id, nome, status, quantidadeMateriaPrimaPorUnidade, 0.5);
        // todo: conteudo que preciso fazer (qualidade fixa em 0.5 e menor demanda de matéria-prima)
    }

    @Override
    public void processar() {
        // todo: conteudo que preciso fazer (define o processamento específico de produto de baixa qualidade)
    }

    @Override
    public double calcularTempoProducao() {
        // todo: conteudo que preciso fazer (tempo de produção para medicamento de baixa qualidade)
        return 0.0;
    }

    @Override
    public String getTipo() {
        // todo: conteudo que preciso fazer (retornar o tipo do produto, ex: "Medicamento de Baixa Qualidade")
        return "Medicamento de Baixa Qualidade";
    }
}
