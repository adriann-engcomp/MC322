public class MedicamentoBaixaQualidade extends Produto {

    // Construtor
    public MedicamentoBaixaQualidade(int id, String nome, String status, double quantidadeMateriaPrimaPorUnidade) {
        super(id, nome, status, quantidadeMateriaPrimaPorUnidade, 0.5);
    }

    @Override
    public void processar() {
        setStatus("Processando");
    }

    @Override
    public double calcularTempoProducao(int quantidade) {
        return 5.0 * quantidade; // tempo(min)*quantidade(unidades)
    }

    @Override
    public String getTipo() {
        return "Medicamento de Baixa Qualidade";
    }
}
