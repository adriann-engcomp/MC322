public class Demanda {
    // Novos tributos
    private static int proximoNumero = 1;
    // Ordem de chegada da demanda usando a fila FIFO
    private final int numero;
    private final TipoMedicamento tipo;
    private final String medicamento;
    private final double custoOperacionalPorUnidade;
    private int quantidade;
    private StatusDemanda status;

    public Demanda(TipoMedicamento tipo, String medicamento, int quantidade, double custoOperacionalPorUnidade){
        if (tipo == null || quantidade < 0){
            throw new IllegalArgumentException("É obrigatório definir o tipo do medicamento e a quantidade não pode ser negativa");
        }
        this.numero = proximoNumero++;
        this.tipo = tipo;
        this.medicamento = medicamento;
        this.quantidade = quantidade;
        this.custoOperacionalPorUnidade = custoOperacionalPorUnidade;
        this.status = StatusDemanda.PENDENTE;
    }


    // Métodos
    /* public void atender() {
        this.atendida = true;
    } */
    private void mudarStatus(StatusDemanda novo){
        if (!status.mudaPara(novo)){
            throw new IllegalArgumentException(String.format(
                "Transição inválida no seu pedido #%d: %s -> %s",
            numero, status.getDescricao(), novo.getDescricao()));
        }
        this.status = novo;
    }
    public void iniciarProducao(){
        mudarStatus(StatusDemanda.EM_PRODUCAO);
    }
    public void concluir(){
        mudarStatus(StatusDemanda.CONCLUIDA);
    }
    public void cancelar(){
        mudarStatus(StatusDemanda.CANCELADA);
    }
    /* public void atualizarQuantidade(int novaQuantidade) {
        this.quantidadeProdutos = novaQuantidade;
        this.atendida = false;
    }, se torna:
    */
    // Trato primeiro o caso de quem recebe uma nova quantidade (PENDENTES ou CONCLUIDOS)
    public boolean podeSerAtualizada(){
        return status == StatusDemanda.PENDENTE || status == StatusDemanda.CONCLUIDA;
    }

    public void atualizarQuantidade(int novaQuantidade){
        // No caso de está PENDENTE atribuo uma nova demanda
        if (novaQuantidade < 0){
            throw new IllegalArgumentException("A quantidade nunca é negativa");
        }
        if (!podeSerAtualizada()){
            throw new IllegalStateException("Pedido #" + numero + "está " + status.getDescricao() + " e não pode ser alterado.");
        }
        this.quantidade = novaQuantidade;
        if (status == StatusDemanda.CONCLUIDA){
            mudarStatus(StatusDemanda.PENDENTE);
        }
    }

    /* public double calcularMateriaPrimaNecessaria(double materiaPrimaPorUnidade) {
        return materiaPrimaPorUnidade * quantidadeProdutos;
    } */
    // ??
    public boolean estaElegivel(){
        return status == StatusDemanda.PENDENTE && quantidade > 0;
    }
    // kg/demanda (quantidade)
    public double calcularConsumoPorDemanda(){
        return quantidade * tipo.getConsumoPorUnidade();
    }
    // R$/demanda (quantidade)
    public double calcularCustoPorDemanda(){
        return quantidade * custoOperacionalPorUnidade;
    }
    // Consigo produzir?
    public boolean cabeNoOrcamento(double orcamentoDisponivel){
        return calcularCustoPorDemanda() <= orcamentoDisponivel;
    }


    // Getters e Setters
    public int getNumero(){
        return numero;
    }
    
    public TipoMedicamento getTipo(){
        return tipo;
    }

    public String getMedicamento(){
        return medicamento;
    }

    public int getQuantidade(){
        return quantidade;
    }

    public StatusDemanda getStatus(){
        return status;
    }

    public double getCustoOperacionalPorUnidade(){
        return custoOperacionalPorUnidade;
    }

    @Override
    public String toString(){
        return String.format("Pedido #%d - %s (%s) x%d [%s]",
            numero, medicamento, tipo.getNome(), quantidade, status.getDescricao());
    }

}
