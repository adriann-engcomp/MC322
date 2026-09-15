import java.util.ArrayList;
// Eu sei se consigo fabricar essa demanda.

public class GerenciadorProducao {

    // Atributos
    private ArrayList<Demanda> demandas;
    private ArrayList<Produto> produtosFabricados;
    private ArrayList<Maquina> maquinas;
    private MateriaPrima materiaPrima;
    private double budget;

    // Construtor
    public GerenciadorProducao(MateriaPrima materiaPrima, double budgetInicial) {
        this.demandas = new ArrayList<>();
        this.produtosFabricados = new ArrayList<>();
        this.maquinas = new ArrayList<>();
        this.materiaPrima = materiaPrima;
        this.budget = budgetInicial;
    }

    // Métodos
    public void registrarDemanda(Demanda demanda) {
        demandas.add(demanda);
    }

    public void atualizarDemanda(int indice, int novaQuantidade) {
        demandas.get(indice).atualizarQuantidade(novaQuantidade);
    }

    public void fabricarDemanda(int indiceDemanda) {
        // 1. Verificar se a demanda existe e não foi atendida
        if (demandas.get(indiceDemanda) == null || demandas.get(indiceDemanda).isAtendida()) {
            System.out.println("Demanda inválida ou já atendida.");
            return;
        }
        // 2. Verificar e consumir matéria-prima necessária

        // 3. Debitar custo de operação das máquinas do budget (calcularCustoProducao)
        // 4. Passar os produtos pelo fluxo de máquinas (Processamento -> Embalagem -> Inspeção)
        // 5. Adicionar produtos fabricados ao armazém (produtosFabricados)
        // 6. Marcar demanda como atendida
    }

    public void comprarMateriaPrima(double quantidade) {
        // 1. Calcular custo total (quantidade * custoPorUnidade)
        double custoTotal = quantidade * materiaPrima.getCustoPorUnidade();
        // 2. Verificar se há budget suficiente
        if (budget < custoTotal) {
            System.out.println("Budget insuficiente para comprar matéria-prima.");
            return;
        }
        // 3. Debitar valor do budget e adicionar quantidade ao estoque de materiaPrima
        budget -= custoTotal;
        materiaPrima.adicionarEstoque(quantidade);
        System.out.println("Compra de matéria-prima realizada com sucesso. Novo budget: R$ " + budget);
    }

    public void exibirBudget() {
        System.out.println("Budget: " + budget);
    }

    public void exibirArmazem() {
        System.out.println("Produtos Fabricados:");
        for (Produto produto : produtosFabricados) {
            System.out.println("ID: " + produto.getId() + ", Nome: " +  produto.getNome() + ", Qualidade: " + produto.getQualidade() + ", Status: " + produto.getStatus());
        }
    }

    public void exibirEstoqueMateriaPrima() {
        System.out.println("Estoque de Matéria-Prima:");
        System.out.println("ID: " + materiaPrima.getId() + ", Nome: " + materiaPrima.getNome() + ", Quantidade: " + materiaPrima.getQuantidade() + ", Unidade: " + materiaPrima.getUnidade());
    }

    public void adicionarMaquina(Maquina maquina) {
        maquinas.add(maquina);
    }

    // Método privado
    private double calcularCustoProducao(int quantidadeProdutos) {
        // todo: conteudo que preciso fazer (somar custos de operação das máquinas na linha de produção)
        return 0.0;
    }

    // Getters e Setters
    public ArrayList<Demanda> getDemandas() {
        // todo: conteudo que preciso fazer
        return demandas;
    }

    public void setDemandas(ArrayList<Demanda> demandas) {
        // todo: conteudo que preciso fazer
        this.demandas = demandas;
    }

    public ArrayList<Produto> getProdutosFabricados() {
        // todo: conteudo que preciso fazer
        return produtosFabricados;
    }

    public void setProdutosFabricados(ArrayList<Produto> produtosFabricados) {
        // todo: conteudo que preciso fazer
        this.produtosFabricados = produtosFabricados;
    }

    public ArrayList<Maquina> getMaquinas() {
        // todo: conteudo que preciso fazer
        return maquinas;
    }

    public void setMaquinas(ArrayList<Maquina> maquinas) {
        // todo: conteudo que preciso fazer
        this.maquinas = maquinas;
    }

    public MateriaPrima getMateriaPrima() {
        // todo: conteudo que preciso fazer
        return materiaPrima;
    }

    public void setMateriaPrima(MateriaPrima materiaPrima) {
        // todo: conteudo que preciso fazer
        this.materiaPrima = materiaPrima;
    }

    public double getBudget() {
        // todo: conteudo que preciso fazer
        return budget;
    }

    public void setBudget(double budget) {
        // todo: conteudo que preciso fazer
        this.budget = budget;
    }
}
