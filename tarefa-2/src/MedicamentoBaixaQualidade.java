public class MedicamentoBaixaQualidade extends Produto {

    // Construtor
    public MedicamentoBaixaQualidade(int id, String nome, String status, double quantidadeMateriaPrimaPorUnidade) {
        super(id, nome, status, quantidadeMateriaPrimaPorUnidade, 0.5);
        // todo: conteudo que preciso fazer (qualidade fixa em 0.5 e menor demanda de matéria-prima)
    }

    @Override
    public void processar() {
        setStatus("Processando");
    }

    @Override
    public double calcularTempoProducao() {
        return 5.0; // unidades/tempo
    }

    @Override
    public String getTipo() {
        // todo: conteudo que preciso fazer (retornar o tipo do produto, ex: "Medicamento de Baixa Qualidade")
        return "Medicamento de Baixa Qualidade";
    }
}
