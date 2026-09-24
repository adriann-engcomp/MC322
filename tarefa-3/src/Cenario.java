public enum Cenario {
    
    FABRICA_NORMAL(
        "Fabrica em estado normal",
        "Produto novo no mercado, já auditado pela Anvisa e com perspectiva de altas vendas",
        1000.0,
        100.0,
        // metade da chance de falhar, por exemplo
        0.5,
        // saude/uso
        0.0, 
        3.0,
        42L),

    FABRICA_PANDEMIA(
        "Fabrica em estado Apocalíptico",
        "Demanda nas alturas, falta de matéria prima, maquinas no limite e estoque de remédios no vermelho",
        // Diminui devido a primeira onda da pandemia
        500.0,
        // Diminui devido a alta procura
        10.0,
        // É o dobro ou mais, pois tenho que produzir rápido em pouco tempo com alta qualidade
        2.0,
        5.0, 
        15.0,
        2026L);

    private final String nome;
    private final String descricao;
    private final double budgetInicial;
    private final double estoqueInicialMateriaPrima;
    private final double fatorFalha;
    private final double desgasteMinimo;
    private final double desgasteMaximo;
    private final long sementeAleatoria;

    Cenario(String nome, String descricao, double budgetInicial,
        double estoqueInicialMateriaPrima, double fatorFalha, 
        double desgasteMinimo, double desgasteMaximo, long sementeAleatoria){
            this.nome = nome;
            this.descricao = descricao;
            this.budgetInicial = budgetInicial;
            this.estoqueInicialMateriaPrima = estoqueInicialMateriaPrima;
            this.fatorFalha = fatorFalha;
            this.desgasteMinimo = desgasteMinimo;
            this.desgasteMaximo = desgasteMaximo;
            this.sementeAleatoria = sementeAleatoria;
        }

        public String getNome(){
            return nome;
        }
        public String getDescricao(){
            return descricao;
        }
        public double getBudgetInicial(){
            return budgetInicial;
        }
        public double getEstoqueInicialMateriaPrima(){
            return estoqueInicialMateriaPrima;
        }
        public double getFatorFalha(){
            return fatorFalha;
        }
        public double getDesgasteMinimo(){
            return desgasteMinimo;
        }
        public double getDesgasteMaximo(){
            return desgasteMaximo;
        }
        public long getSementeAleatoria(){
            return sementeAleatoria;
        }

}
