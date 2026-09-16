public class MaquinaInspecao extends Maquina {

    // Construtor
    public MaquinaInspecao(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao) {
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao);
    }

    @Override
    public void processar(Produto produto) {
        if (produto == null || !estaLigada()){
            return;
        }

        // 1. Chance de rejeição baseada na probabilidade de falha acumulada e no rigor de qualidade:
        // Alta qualidade (0.9) -> critérios mais rigorosos -> maior chance de rejeição
        // Baixa qualidade (0.5) -> critérios mais flexíveis -> menor chance de rejeição
        double chanceRejeicao = produto.getProbabilidadeFalhaAcumulada() * (produto.getQualidade() / 0.5);

        boolean defeituoso = random.nextDouble() < chanceRejeicao;

        // 2. A máquina de inspeção pode falhar diretamente com probabilidade Z% (verificarFalha()),
        // resultando em uma inspeção incorreta (inverte a decisão de aprovação/rejeição)
        boolean maquinaFalhou = verificarFalha();
        boolean aprovado = maquinaFalhou ? defeituoso : !defeituoso;

        if (aprovado) {
            produto.setStatus("Aprovado");
        } else {
            produto.setStatus("Rejeitado");
        }
    }

    @Override
    public String getTipo() {
        return "Máquina de Inspeção / Controle de Qualidade";
    }
}
