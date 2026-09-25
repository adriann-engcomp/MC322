// Ex: Ritalina

public class MedicamentoControlado extends Produto {
    // Construtor
    public MedicamentoControlado(String nome, String lote) {
        super(nome, lote, TipoMedicamento.CONTROLADO);
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
        return "Medicamento Controlado";
    }
}
