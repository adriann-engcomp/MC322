import java.util.Random;

/**
 * Classe responsável pela configuração inicial da fábrica de medicamentos.
 * <p>
 * Cria os insumos ({@link MateriaPrima}), o Agrupador {@link LinhaDeProducao},
 * instancia as unidades produtivas ({@link MaquinaProcessamento}, {@link MaquinaEmbalagem}, {@link MaquinaInspecao})
 * agregando-as à linha fabril, e inicializa o {@link GerenciadorProducao} com as demandas iniciais.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class InicializoFabrica {

    private InicializoFabrica() {
    }

    /**
     * Inicializa a fábrica conforme o cenário escolhido.
     *
     * @param cenario Cenário escolhido pelo usuário.
     * @return Gerenciador com a linha de produção, máquinas compostas e demandas iniciais.
     */
    public static GerenciadorProducao inicializar(Cenario cenario) {
        Random random = new Random(cenario.getSementeAleatoria());

        // 1. Instancia a Matéria-Prima inicial
        MateriaPrima materiaPrima = new MateriaPrima(1, "Princípio Ativo Farmacêutico (Insumo)",
                cenario.getEstoqueInicialMateriaPrima(), "kg", 10.0);

        // 2. Instancia o Agrupador: Linha de Produção Farmacêutica
        LinhaDeProducao linhaDeProducao = new LinhaDeProducao("LINHA-01", "Linha Principal de Medicamentos (Comprimidos)");

        // 3. Instancia as unidades produtivas ("O Todo"), cada uma construindo internamente suas Partes
        MaquinaProcessamento maquinaProcessamento = new MaquinaProcessamento(
                "Reator Farmacêutico / Mistura", 500, 0.15, 1.50, cenario, random);
        MaquinaEmbalagem maquinaEmbalagem = new MaquinaEmbalagem(
                "Embaladora Blister / Selagem", 500, 0.10, 1.00, cenario, random);
        MaquinaInspecao maquinaInspecao = new MaquinaInspecao(
                "Espectrômetro de Inspeção / Controle Anvisa", 500, 0.05, 2.00, cenario, random);

        // 4. Agrupamento (Agregação 1:N entre LinhaDeProducao e Maquinas)
        linhaDeProducao.adicionarMaquina(maquinaProcessamento);
        linhaDeProducao.adicionarMaquina(maquinaEmbalagem);
        linhaDeProducao.adicionarMaquina(maquinaInspecao);

        // 5. Instancia o Gerenciador de Produção com a Linha de Produção
        GerenciadorProducao gerenciador = new GerenciadorProducao(
                materiaPrima, cenario.getBudgetInicial(), new EstrategiaFilaDePedidos(), linhaDeProducao);

        // 6. Registra as demandas iniciais de Medicamentos
        gerenciador.registrarDemanda(new Demanda(TipoMedicamento.CONTROLADO, "Ritalina 40mg", 10, 4.50));
        gerenciador.registrarDemanda(new Demanda(TipoMedicamento.CONTINUO, "Losartana 50mg", 20, 4.50));
        gerenciador.registrarDemanda(new Demanda(TipoMedicamento.GENERICO, "Dipirona 500mg", 30, 4.50));

        // Retorna a fábrica completamente inicializada
        return gerenciador;
    }
}
