// Ex: Insulina

public class MedicamentoContinuo extends Produto {
    // Construtor
    public MedicamentoContinuo(String nome, String lote) {
        super(nome, lote, TipoMedicamento.CONTINUO);
    }

    @Override
    public double calcularTempoProducao(int quantidade) {
        return 6.0 * quantidade; // tempo(min)*quantidade(unidades)
    }

    @Override 
    protected double getRiscoDeFalha(){
        return 0.20;
    }

    // Relatorio com base no texto da super classe
    @Override
    public String gerarRelatorioDiagnostico(){
        return super.gerarRelatorioDiagnostico() + " | Precisa de receita constante";
    }
}