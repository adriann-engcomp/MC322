/* Por que preciso do TipoMedicamento?
Precisamos do TipoMedicamento, pois, na tarefa 2, o Gerenciador de produção
decidia o que fazer baseado no ÍNDICE da demanda "(if indiceDemanda == 0)..".
Diante disso, mediante leitura da teoria de Strategy definimos que
faz mais sentido deixar cada pedido carregar somente o seu nome e lote
e o disso o enum cria o Produto certo.
*/

public enum TipoMedicamento {
    CONTROLADO("Controlado", "CTR", 3.0, 0.9){
        @Override 
        public Produto criarProduto(String nome, String lote){
            return new MedicamentoControlado(nome, lote);
        }
    },
    CONTINUO("Contínuo", "CTN", 2.0, 0.7){
        @Override 
        public Produto criarProduto(String nome, String lote){
            return new MedicamentoContinuo(nome, lote);
        }
    },
    GENERICO("Genérico", "GEN", 1.0, 0.5){
        @Override 
        public Produto criarProduto(String nome, String lote){
            return new MedicamentoGenerico(nome, lote);
        }
    };

    private final String nome;
    private final String sigla;
    private final double consumoPorUnidade; // kg/und 
    private final double qualidadePedida; // 0.0 (nenhuma) até 1.0 (perfeita)

    TipoMedicamento(String nome, String sigla, double consumoPorUnidade, double qualidadePedida){
        this.nome = nome;
        this.sigla = sigla;
        this.consumoPorUnidade = consumoPorUnidade;
        this.qualidadePedida = qualidadePedida;
    }

    // Cada tipo cria o seu próprio medicamento usando nome e lote
    public abstract Produto criarProduto(String nome, String lote);

    // Gets
    public String getNome(){
        return nome;
    }
    public String getSigla(){
        return sigla;
    }
    public double getConsumoPorUnidade(){
        return consumoPorUnidade;
    }
    public double getQualidadePedida(){
        return qualidadePedida;
    }
    

}
