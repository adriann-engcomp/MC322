import java.util.ArrayList;
import java.util.List;

public interface EstrategiaProducao {

    // Retorno o pedido que será produzido ou null se não for possível produzir
    Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel);

    // Nome amigável que exibo para o usuário
    String getNomeEstrategia();

    // Trato os pedidos PENDENTES e com qtd > 0 (o default ajuda a generalizar para as outras estratégias)
    default List<Demanda> separoElegiveis(List<Demanda> demandas){
        List<Demanda> elegiveis = new ArrayList<>();
        for (Demanda demanda : demandas){
            if (demanda.estaElegivel()){
                elegiveis.add(demanda);
            }
        }
        return elegiveis;
    }


}
