import java.util.ArrayList;
import java.util.List;

/**
 * Interface que define o contrato do padrão Strategy para a seleção de demandas a serem produzidas.
 * <p>
 * Permite que a fábrica alterne dinamicamente entre diferentes políticas de escalonamento
 * de produção (como ordem de chegada, emergência sanitária ou atendimento prioritário ao SUS)
 * sem necessidade de modificar a lógica interna de execução do {@link GerenciadorProducao}.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public interface EstrategiaProducao {

    /**
     * Seleciona a próxima demanda a ser atendida a partir de uma lista de demandas registradas,
     * considerando o orçamento financeiro disponível e os critérios particulares da estratégia.
     *
     * @param demandas            Lista de todas as demandas cadastradas na fábrica.
     * @param orcamentoDisponivel Orçamento financeiro atualmente disponível para arcar com os custos de produção.
     * @return A {@link Demanda} selecionada para produção imediata, ou {@code null} caso nenhuma seja elegível.
     */
    Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel);

    /**
     * Retorna o nome amigável e legível da estratégia para exibição em menus e relatórios.
     *
     * @return Nome da estratégia de produção.
     */
    String getNomeEstrategia();

    /**
     * Filtra e retorna apenas as demandas elegíveis para produção a partir da lista fornecida
     * (demandas com status pendente e quantidade maior que zero).
     *
     * @param demandas Lista completa de demandas cadastradas.
     * @return Lista contendo somente as demandas em estado elegível.
     */
    default List<Demanda> separoElegiveis(List<Demanda> demandas) {
        List<Demanda> elegiveis = new ArrayList<>();
        for (Demanda demanda : demandas) {
            if (demanda.estaElegivel()) {
                elegiveis.add(demanda);
            }
        }
        return elegiveis;
    }
}
