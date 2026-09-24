public enum StatusProduto {
    AGUARDANDO("Aguardando"),
    PROCESSADO("Processado"),
    PROCESSANDO("Processando"),
    EMBALADO("Embalado"),
    APROVADO("Aprovado"),
    REJEITADO("Rejeitado");

    private final String Descricao;

    StatusProduto(String Descricao){
        this.Descricao = Descricao;
    }

    public String getDescricao(){
        return Descricao;
    }

}
