public class MaquinaInspecao extends Maquina {

    // Construtor
    public MaquinaInspecao(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao) {
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao);
        // todo: conteudo que preciso fazer (inicializar atributos)
    }

    @Override
    public void processar(Produto produto) {
        // todo: conteudo que preciso fazer
        // 1. Pode falhar com probabilidade Z% (verificarFalha()), gerando uma inspeção incorreta.
        // 2. Avaliar se o produto é aprovado ou rejeitado com base em:
        //    - Probabilidade acumulada de falha do produto
        //    - Qualidade do produto (diretamente proporcional à chance de falha na inspeção: alta qualidade = critérios mais rigorosos = maior chance de rejeição)
        // 3. Atualizar o status do produto (ex: "Aprovado", "Rejeitado")
    }

    @Override
    public String getTipo() {
        // todo: conteudo que preciso fazer
        return "Máquina de Inspeção / Controle de Qualidade";
    }
}
