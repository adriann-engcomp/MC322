/**
 * Classe utilitária para exibição de erros no console.
 * <p>
 * Apresenta o título da falha, sua causa e a descrição do estado preservado.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public final class ExibeErro{
    private ExibeErro(){
    }

    /**
     * Exibe o painel visual padronizado de erro com o diagnóstico e a garantia de consistência do estado.
     *
     * @param titulo           Título descritivo da falha ou categoria do erro.
     * @param mensagem         Mensagem explicativa sobre a causa do problema.
     * @param estadoPreservado Descrição das garantias de integridade do estado da aplicação.
     */
    public static void exibirPainelErro(String titulo, String mensagem, String estadoPreservado) {
        System.out.println("\n========================================");
        System.out.println("ERRO: " + titulo.toUpperCase());
        System.out.println("========================================");
        System.out.println(mensagem);
        if (estadoPreservado != null && !estadoPreservado.isBlank()) {
            System.out.println(estadoPreservado);
        }
        System.out.println("========================================\n");
    }
}