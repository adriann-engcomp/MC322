// Ex: Dipirona

public class MedicamentoGenerico extends Produto {
    // Construtor
    public MedicamentoGenerico(String nome, String lote) {
        super(nome, lote, TipoMedicamento.GENERICO);
    }

    @Override
    public double calcularTempoProducao(int quantidade) {
        return 5.0 * quantidade; // tempo(min)*quantidade(unidades)
    }

    @Override 
    protected double getRiscoDeFalha(){
        return 0.30;
    }

    // Relatorio com base no texto da super classe
    @Override
    public String gerarRelatorioDiagnostico(){
        return super.gerarRelatorioDiagnostico() + " | Ás vezes usa-se a receita (contínuo) ou não";
    }
}
