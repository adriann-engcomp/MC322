public enum StatusMaquina {
    DESLIGADA("Desligada"),
    LIGADA("Ligada"),
    QUEBRADA("Quebrada"),
    MANUNTENCAO("Em manuntenção");

    private final String Descricao;

    StatusMaquina(String Descricao){
        this.Descricao = Descricao;
    }

    public String getDescricao(){
        return Descricao;
    }
}
