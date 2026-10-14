/**
 * Representa um insumo farmacêutico / matéria-prima estocado na fábrica.
 * <p>
 * Controla os níveis de estoque físico, a unidade de medida utilizada,
 * o custo financeiro unitário de aquisição e as operações de abastecimento e consumo.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class MateriaPrima {

    private int id;
    private String nome;
    private double quantidade;
    private String unidade;
    private double custoPorUnidade;

    /**
     * Construtor da matéria-prima.
     *
     * @param id              Identificador único do insumo.
     * @param nome            Nome descritivo da matéria-prima / princípio ativo.
     * @param quantidade      Quantidade inicial em estoque.
     * @param unidade         Unidade de medida (ex.: "kg", "g", "L").
     * @param custoPorUnidade Custo monetário de compra por unidade de medida (em R$).
     */
    public MateriaPrima(int id, String nome, double quantidade, String unidade, double custoPorUnidade) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.custoPorUnidade = custoPorUnidade;
    }

    /**
     * Tenta debitar uma quantidade especificada do estoque de matéria-prima.
     *
     * @param qtd Quantidade a ser consumida.
     * @return {@code true} se o estoque for suficiente e o débito for realizado; {@code false} caso contrário.
     */
    public boolean consumir(double qtd) {
        if (this.quantidade >= qtd) {
            this.quantidade -= qtd;
            return true;
        }
        return false;
    }

    /**
     * Adiciona uma quantidade de insumo recém-adquirida ao estoque atual.
     *
     * @param qtd Quantidade a ser acrescentada ao estoque.
     */
    public void adicionarEstoque(double qtd) {
        this.quantidade += qtd;
    }

    /**
     * Verifica se o estoque atual é suficiente para atender a uma necessidade de consumo sem debitá-lo.
     *
     * @param qtdNecessaria Quantidade necessária para a operação.
     * @return {@code true} se houver quantidade disponível suficiente; {@code false} caso contrário.
     */
    public boolean verificarDisponibilidade(double qtdNecessaria) {
        return this.quantidade >= qtdNecessaria;
    }

    /**
     * Obtém o identificador da matéria-prima.
     *
     * @return Identificador numérico.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador da matéria-prima.
     *
     * @param id Novo identificador.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém o nome da matéria-prima.
     *
     * @return Nome do insumo.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da matéria-prima.
     *
     * @param nome Novo nome do insumo.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a quantidade atualmente disponível em estoque.
     *
     * @return Quantidade em estoque.
     */
    public double getQuantidade() {
        return quantidade;
    }

    /**
     * Define diretamente a quantidade disponível em estoque.
     *
     * @param quantidade Nova quantidade.
     */
    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Obtém a unidade de medida do insumo.
     *
     * @return Unidade de medida (ex: "kg").
     */
    public String getUnidade() {
        return unidade;
    }

    /**
     * Define a unidade de medida do insumo.
     *
     * @param unidade Nova unidade de medida.
     */
    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    /**
     * Obtém o custo de aquisição por unidade de medida.
     *
     * @return Custo unitário em reais (R$).
     */
    public double getCustoPorUnidade() {
        return custoPorUnidade;
    }

    /**
     * Define o custo de aquisição por unidade de medida.
     *
     * @param custoPorUnidade Novo custo unitário.
     */
    public void setCustoPorUnidade(double custoPorUnidade) {
        this.custoPorUnidade = custoPorUnidade;
    }
}
