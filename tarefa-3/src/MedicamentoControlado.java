// Ex: Ritalina

public class MedicamentoControlado extends Produto {
    // Construtor
    public MedicamentoControlado(String nome, String lote) {
        super(nome, lote, TipoMedicamento.CONTROLADO);
    }

    @Override
    public double calcularTempoProducao(int quantidade) {
        return 10.0 * quantidade; // tempo(min)*quantidade(unidades)
    }

    @Override 
    protected double getRiscoDeFalha(){
        return 0.10;
    }

    // Relatorio com base no texto da super classe
    @Override
    public String gerarRelatorioDiagnostico(){
        return super.gerarRelatorioDiagnostico() + " | Precisa de uma receita especial controlada";
    }
}
