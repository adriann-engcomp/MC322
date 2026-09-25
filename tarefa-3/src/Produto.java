/* 
1. O que é o Produto.java?
Produto.java é onde defino como vou produzir uma unidade de medicamento.
2. Como vou usar o Produto.java na Tarefa3?
Vou usar ele reapoveitando o que fizemos com ele na Tarefa2 (Aplicamos
Herança para definirmos os tipos de medicamentos: Controlado, Contínuo e Genérico)
e aplicaremos nele:
1. StatusProduto (status como enum);
2. Lote de produção;
3. Auditavel.java (Implementa a interface Auditável).
*/

public abstract class Produto implements Auditavel {
    // Atributos
    private final int id;
    private final String nome;
    private StatusProduto status;
    private final double qualidade; // 0.0 a 1.0
    private double probabilidadeFalhaAcumulada;
    private static int totalProdutosFabricados = 0;

    // Tarefa 3
    private final String lote;
    private final TipoMedicamento tipo;

    // Construtor
    protected Produto(String nome, String lote, TipoMedicamento tipo) {
        // O produto gera seu id
        totalProdutosFabricados++;
        this.id = totalProdutosFabricados;
        this.nome = nome;
        this.status = StatusProduto.AGUARDANDO;
        this.qualidade = limitar(tipo.getQualidadePedida());
        this.probabilidadeFalhaAcumulada = 0.0;
        // Tarefa3
        this.lote = lote;
        this.tipo = tipo;
    }

    // Métodos Abstratos
    public abstract double calcularTempoProducao(int quantidade);
    // Tarefa3: cada tipo de produto sabe qual é o seu RiscoDeFalha
    protected abstract double getRiscoDeFalha();

    // Métodos Concretos
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public StatusProduto getStatus() {
        return status;
    }

    public void setStatus(StatusProduto status) {
        this.status = status;
    }

    public double getQualidade() {
        return qualidade;
    }

    public double getProbabilidadeFalhaAcumulada() {
        return probabilidadeFalhaAcumulada;
    }

    public static int getTotalProdutosFabricados() {
        return totalProdutosFabricados;
    }

    // Fixo valor entre 0.0 e 1.0
    private static double limitar(double valor){
        return Math.max(0.0, Math.min(1.0, valor));
    }
    public void aumentarProbabilidadeFalha(double incremento) {
        if (incremento <= 0){
            return;
        }
        probabilidadeFalhaAcumulada = limitar(probabilidadeFalhaAcumulada + incremento);
    }

    public boolean foiAprovado(){
        return status == StatusProduto.APROVADO;
    }

    // Tarefa 3
    public String getLote(){
        return lote;
    }
    public TipoMedicamento getTipo(){
        return tipo;
    }
    // Substituo o processar()
    public void iniciarProcessamento(){
        status = StatusProduto.PROCESSANDO;
    }
    // Métodos da interface Auditavel
    @Override 
    public boolean precisaManutencao(){
        return status == StatusProduto.REJEITADO ||
        probabilidadeFalhaAcumulada >= getRiscoDeFalha();
    }
    @Override 
    public String gerarRelatorioDiagnostico(){
        return "Produto " + id + " | " + 
        nome + " | lote " + lote
        + " | " + status.getDescricao()
        + " | risco " + probabilidadeFalhaAcumulada;

    }
}