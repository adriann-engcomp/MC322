/*
Padrão Strategy: cada regra de escolha de pedido fica numa classe própria,
e o gerenciador troca uma pela outra sem mudar o próprio código.
Ref.: Freeman & Robson, Head First Design Patterns, cap. 1.
*/

import java.util.ArrayList;
import java.util.List;

public interface EstrategiaProducao {

    // Retorno o pedido que será produzido ou null se não for possível produzir
    Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel);

    // Nome amigável que exibo para o usuário
    String getNomeEstrategia();

    // Método default: Filtro comum às 3 estratégias fica num lugar só.
    // Usei com Ref: Bloch, Java Efetivo, Item 21.
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
