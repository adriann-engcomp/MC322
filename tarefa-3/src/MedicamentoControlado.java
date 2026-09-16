public class MedicamentoControlado extends Produto {

    // Construtor
    public MedicamentoControlado(int id, String nome, String status, double quantidadeMateriaPrimaPorUnidade) {
        super(id, nome, status, quantidadeMateriaPrimaPorUnidade, 0.9);
    }

    @Override
    public void processar() {
        setStatus("Processando");
    }

    @Override
    public double calcularTempoProducao(int quantidade) {
        return 10.0 * quantidade; // tempo(min)*quantidade(unidades)
    }

    @Override
    public String getTipo() {
        return "Medicamento de Alta Qualidade";
    }
}
