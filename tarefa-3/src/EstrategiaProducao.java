import java.util.ArrayList;
import java.util.List;

public interface EstrategiaProducao {

    // Retorno o pedido que será produzido ou null se não for possível produzir
    Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel);

    // Nome amigável que exibo para o usuário
    String getNomeEstrategia();


}
