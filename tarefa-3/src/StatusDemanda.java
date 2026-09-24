public enum StatusDemanda {
    PENDENTE("Pendente", "Aguardando a produção"),
    EM_PRODUCAO("Em produção", "Lote na esteira de fabricação"),
    CONCLUIDA("Concluída", "Todas as unidades foram fabricadas"),
    CANCELADA("Cancelada", "Não tem matéria prima ou orçamento disponível");

    private final String Rotulo;
    private final String Detalhe;

    StatusDemanda(String Rotulo, String Detalhe){
        this.Rotulo = Rotulo;
        this.Detalhe = Detalhe;
    }

    // Texto curto
    public String getDescricao(){
        return Rotulo;
    }

    // Texto longo
    public String getDetalhe(){
        return Detalhe;
    }

    // Agora, vou montar a lógica de transições para cada Status
    public boolean mudaPara(StatusDemanda Novo){
        switch (this) {
            case PENDENTE:
                return Novo == EM_PRODUCAO || Novo == CANCELADA;
            case EM_PRODUCAO:
                return Novo == CONCLUIDA;
            case CONCLUIDA:
                // Mesmo se produzir a quantidade de uma demanda pedida, posso precisar produzir a mesma quantidade n vezes 
                // ou tive defeito na produção e assim devo produzir mais e por isso saio de concluida para pendente.
                return Novo == PENDENTE;
            default:
                return false;
        }
    }
}

