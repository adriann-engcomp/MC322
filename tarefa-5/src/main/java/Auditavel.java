/**
 * Interface que padroniza o diagnóstico e a manutenção de elementos operacionais da fábrica.
 * <p>
 * Permite que componentes como máquinas e lotes de medicamentos sejam auditados conjuntamente,
 * fornecendo relatórios diagnósticos de seu estado atual e indicando a necessidade de intervenção.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public interface Auditavel {

    /**
     * Gera um relatório diagnóstico textual contendo informações detalhadas do estado do item auditado.
     *
     * @return String formatada contendo o diagnóstico operacional do item.
     */
    String gerarRelatorioDiagnostico();

    /**
     * Avalia se o item necessita de manutenção ou intervenção corretiva com base em critérios operacionais.
     *
     * @return {@code true} se o item requerer manutenção; {@code false} caso contrário.
     */
    boolean precisaManutencao();
}
