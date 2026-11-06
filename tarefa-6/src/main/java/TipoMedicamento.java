/**
 * Enumeração que categoriza os tipos de medicamentos produzidos na planta farmacêutica.
 * <p>
 * Atua como Factory Method, onde cada constante é responsável por instanciar a subclasse
 * correta de {@link Produto} (Controlado, Contínuo ou Genérico), além de encapsular a sigla,
 * o consumo de matéria-prima por unidade e a exigência de qualidade requerida.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public enum TipoMedicamento {

    /**
     * Medicamento controlado: alto consumo de insumo (3 kg/unidade) e rigor extremo de qualidade (0.9).
     */
    CONTROLADO("Controlado", "CTR", 3.0, 0.9) {
        @Override 
        public Produto criarProduto(String nome, String lote) {
            return new MedicamentoControlado(nome, lote);
        }
    },

    /**
     * Medicamento contínuo: consumo médio de insumo (2 kg/unidade) e qualidade padrão intermediária (0.7).
     */
    CONTINUO("Contínuo", "CTN", 2.0, 0.7) {
        @Override 
        public Produto criarProduto(String nome, String lote) {
            return new MedicamentoContinuo(nome, lote);
        }
    },

    /**
     * Medicamento genérico: menor consumo de insumo (1 kg/unidade) e menor exigência de qualidade (0.5).
     */
    GENERICO("Genérico", "GEN", 1.0, 0.5) {
        @Override 
        public Produto criarProduto(String nome, String lote) {
            return new MedicamentoGenerico(nome, lote);
        }
    };

    private final String nome;
    private final String sigla;
    private final double consumoPorUnidade;
    private final double qualidadePedida;

    /**
     * Construtor da constante de tipo de medicamento.
     *
     * @param nome               Nome descritivo da categoria.
     * @param sigla              Sigla identificadora utilizada em rotulagens.
     * @param consumoPorUnidade  Consumo de matéria-prima em kg por unidade produzida.
     * @param qualidadePedida    Nível de qualidade farmacêutica requerida (de 0.0 a 1.0).
     */
    TipoMedicamento(String nome, String sigla, double consumoPorUnidade, double qualidadePedida) {
        this.nome = nome;
        this.sigla = sigla;
        this.consumoPorUnidade = consumoPorUnidade;
        this.qualidadePedida = qualidadePedida;
    }

    /**
     * Método fábrica polimórfico que cria a instância de {@link Produto} correspondente à constante.
     *
     * @param nome Nome do medicamento.
     * @param lote Identificador do lote de produção.
     * @return Objeto especializado concreto de {@link Produto}.
     */
    public abstract Produto criarProduto(String nome, String lote);

    /**
     * Obtém o nome descritivo da categoria.
     *
     * @return Nome do tipo de medicamento.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Obtém a sigla identificadora da categoria.
     *
     * @return Sigla do medicamento.
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * Obtém a quantidade de insumo consumida por unidade fabricada.
     *
     * @return Consumo unitário em kg.
     */
    public double getConsumoPorUnidade() {
        return consumoPorUnidade;
    }

    /**
     * Obtém a qualidade farmacêutica requerida pela categoria.
     *
     * @return Valor de qualidade requerida (entre 0.0 e 1.0).
     */
    public double getQualidadePedida() {
        return qualidadePedida;
    }
}
