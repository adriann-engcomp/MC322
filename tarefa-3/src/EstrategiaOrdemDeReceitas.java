/*
EstrategiaOrdemDeRecitas: FIFO
1. Lógica: Atendo quem chegou primeiro com a receita na nossa fármacia.

2. Pseudocódigo:
Implemento EstrategiaOrdemDeReceitas com base em EstrategiaProducao e chamo a demanda com seus parametros
Inicializo: receitaEscolhida = null
para cada pedido elegível:
    se receita não existir (null) ou pedido.numero < receitaEscolhida.numero : receitaEscolhida = pedido
    devolvo a receitaEscolhida

Em seguida, get retrona a estratégia no menu.
*/

import java.util.List;

public class EstrategiaOrdemDeReceitas implements EstrategiaProducao{
    @Override 
    public Demanda selecionarDemanda(Lista<Demanda> demandas, double orcamentoDisponivel){
        Demanda receitaEscolhida = null;
        for (Demanda demanda : separoElegiveis(demandas)){
            // Busco quem chegou logo (menor numero)
            if (receitaEscolhida == null || demanda.getNumero() < receitaEscolhida.getNumero()){
                receitaEscolhida = demanda;
            }
        }
        return receitaEscolhida;
    }

    
}

