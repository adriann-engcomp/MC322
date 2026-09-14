public class MedicamentoAltaQualidade extends Produto {

    // Construtor
    public MedicamentoAltaQualidade(int id, String nome, String status, double quantidadeMateriaPrimaPorUnidade) {
        super(id, nome, status, quantidadeMateriaPrimaPorUnidade, 0.9);
        // todo: conteudo que preciso fazer (qualidade fixa em 0.9 e maior demanda de matéria-prima)
    }

    @Override
    public void processar() {
        // todo: conteudo que preciso fazer (define o processamento específico de produto de alta qualidade)
        setStatus("Processando");
    }

    @Override
    public double calcularTempoProducao(int quantidade) {
        // todo: conteudo que preciso fazer (tempo de produção para medicamento de alta qualidade)
        return 10.0 * quantidade; // tempo(min)*quantidade(unidades)
    }

    @Override
    public String getTipo() {
        // todo: conteudo que preciso fazer (retornar o tipo do produto, ex: "Medicamento de Alta Qualidade")
        return "Medicamento de Alta Qualidade";
    }
}
