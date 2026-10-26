import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa o Agrupador da planta fabril farmacêutica (relação de Agregação com as Máquinas).
 * <p>
 * Uma Linha de Produção agrega uma sequência ordenada de unidades produtivas ({@link Maquina})
 * que formam a esteira contínua de fabricação (ex: Reator -> Embaladora -> Espectrômetro).
 * Implementa {@link Auditavel}, propagando a auditoria estrutural em cascata (Linha -> Máquinas -> Subcomponentes).
 * Protege sua estrutura interna contra manipulações externas devolvendo coleções imutáveis
 * (princípio da cópia defensiva e encapsulamento estrito).
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class LinhaDeProducao implements Auditavel {

    private final String idLinha;
    private final String nome;
    private final List<Maquina> maquinas;

    /**
     * Construtor da Linha de Produção.
     *
     * @param idLinha Identificador único da linha fabril.
     * @param nome    Nome descritivo da linha de produção.
     */
    public LinhaDeProducao(String idLinha, String nome) {
        this.idLinha = idLinha;
        this.nome = nome;
        this.maquinas = new ArrayList<>();
    }

    /**
     * Adiciona uma máquina à esteira desta linha de produção.
     *
     * @param maquina Máquina a ser agregada à linha.
     * @throws IllegalArgumentException Se a máquina for nula.
     */
    public void adicionarMaquina(Maquina maquina) {
        if (maquina == null) {
            throw new IllegalArgumentException("Não é possível adicionar uma máquina nula à linha de produção.");
        }
        this.maquinas.add(maquina);
    }

    /**
     * Remove uma máquina desta linha de produção, demonstrando que o ciclo de vida
     * da máquina é desacoplado da linha.
     *
     * @param maquina Máquina a ser desvinculada.
     * @return {@code true} se a máquina foi removida; {@code false} caso contrário.
     */
    public boolean removerMaquina(Maquina maquina) {
        return this.maquinas.remove(maquina);
    }

    /**
     * Retorna a lista de máquinas que compõem esta linha de produção de forma segura.
     * Aplica cópia defensiva / coleção imutável impedindo que código externo modifique
     * a estrutura interna da esteira da fábrica diretamente.
     *
     * @return Coleção imutável de {@link Maquina}.
     */
    public List<Maquina> getMaquinas() {
        return Collections.unmodifiableList(this.maquinas);
    }

    /**
     * Liga todas as máquinas da linha de produção prontas para operar.
     */
    public void ligarTodas() {
        for (Maquina maquina : maquinas) {
            maquina.ligar();
        }
    }

    /**
     * Desliga todas as máquinas da linha de produção após a conclusão do lote.
     */
    public void desligarTodas() {
        for (Maquina maquina : maquinas) {
            maquina.desligar();
        }
    }

    /**
     * Registra a conclusão de um ciclo de uso em todas as máquinas da esteira,
     * aplicando o desgaste proporcional às máquinas e aos seus subcomponentes internos.
     */
    public void registrarCicloDeUso() {
        for (Maquina maquina : maquinas) {
            maquina.registrarCicloDeUso();
        }
    }

    /**
     * Processa um produto sequencialmente por todas as máquinas que compõem a linha.
     *
     * @param produto Unidade farmacêutica a percorrer a esteira.
     */
    public void processar(Produto produto) {
        for (Maquina maquina : maquinas) {
            maquina.processar(produto);
        }
    }

    /**
     * Verifica se existe alguma máquina quebrada na linha de produção que impeça o início dos trabalhos.
     *
     * @return {@code true} se houver máquina inoperante; {@code false} se todas estiverem aptas.
     */
    public boolean possuiMaquinasQuebradas() {
        for (Maquina maquina : maquinas) {
            if (maquina.estaQuebrada()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtém a primeira máquina quebrada encontrada na linha, útil para mensagens diagnósticas.
     *
     * @return Primeira máquina quebrada, ou {@code null} se todas estiverem operacionais.
     */
    public Maquina obterPrimeiraMaquinaQuebrada() {
        for (Maquina maquina : maquinas) {
            if (maquina.estaQuebrada()) {
                return maquina;
            }
        }
        return null;
    }

    /**
     * Executa a manutenção e reparo de todas as máquinas agregadas à linha.
     *
     * @return Quantidade de máquinas reparadas.
     */
    public int repararTodasMaquinas() {
        int reparadas = 0;
        for (Maquina maquina : maquinas) {
            if (maquina.estaQuebrada() || maquina.precisaManutencao()) {
                maquina.reparar();
                reparadas++;
            }
        }
        return reparadas;
    }

    /**
     * Calcula o somatório dos custos operacionais unitários de todas as máquinas da linha.
     *
     * @param quantidade Unidades a serem produzidas.
     * @return Custo total da operação na linha (R$).
     */
    public double calcularCustoProducao(int quantidade) {
        double custoUnitarioTotal = 0.0;
        for (Maquina maquina : maquinas) {
            custoUnitarioTotal += maquina.getCustoOperacao();
        }
        return custoUnitarioTotal * quantidade;
    }

    /**
     * Avalia se a linha como um todo necessita de manutenção preventiva ou corretiva
     * (avaliado em cascata pelas máquinas e seus componentes).
     *
     * @return {@code true} se qualquer máquina ou subcomponente requerer manutenção.
     */
    @Override
    public boolean precisaManutencao() {
        for (Maquina maquina : maquinas) {
            if (maquina.precisaManutencao()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Emite o relatório de auditoria e diagnóstico estrutural em cascata.
     * Formata uma árvore hierárquica e indentada no padrão:
     * Agrupador (Linha) -> Todo (Máquina) -> Partes (Motor, Sensor, Atuadores).
     *
     * @return String formatada com a árvore diagnóstica completa da linha.
     */
    @Override
    public String gerarRelatorioDiagnostico() {
        StringBuilder sb = new StringBuilder();
        String statusLinha = precisaManutencao() ? "ATENÇÃO (REQUER MANUTENÇÃO)" : "TOTALMENTE OPERACIONAL";
        sb.append(String.format("========================================================================================\n"));
        sb.append(String.format("LINHA DE PRODUÇÃO [%s - %s] -> Status Geral: %s | Total de Máquinas: %d\n",
                idLinha, nome, statusLinha, maquinas.size()));
        sb.append(String.format("========================================================================================"));

        if (maquinas.isEmpty()) {
            sb.append("\n  (Nenhuma máquina alocada nesta linha de produção)");
            return sb.toString();
        }

        for (int i = 0; i < maquinas.size(); i++) {
            Maquina maquina = maquinas.get(i);
            boolean isLast = (i == maquinas.size() - 1);
            String prefixo = isLast ? "  └── " : "  ├── ";
            String indentacaoFilhos = isLast ? "        " : "  │     ";

            sb.append("\n").append(prefixo).append(maquina.gerarRelatorioDiagnosticoComIndentacao(indentacaoFilhos));
        }

        return sb.toString();
    }

    /**
     * Obtém o identificador único da linha de produção.
     *
     * @return Identificador da linha.
     */
    public String getIdLinha() {
        return idLinha;
    }

    /**
     * Obtém o nome descritivo da linha fabril.
     *
     * @return Nome da linha de produção.
     */
    public String getNome() {
        return nome;
    }
}
