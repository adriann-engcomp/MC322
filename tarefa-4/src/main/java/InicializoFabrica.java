import java.util.Random;

public class InicializoFabrica {
    private InicializoFabrica(){
    }

    /**
     * Inicializa a fábrica conforme o cenário escolhido.
     *
     * @param cenario Cenário escolhido pelo usuário.
     * @return Gerenciador com máquinas e demandas iniciais.
     */
    public static GerenciadorProducao inicializar(Cenario cenario){
        Random random = new Random(cenario.getSementeAleatoria());

        // Instancio a MP
        MateriaPrima materiaPrima = new MateriaPrima(1, "Princípio Ativo Farmacêutico (Insumo)", cenario.getEstoqueInicialMateriaPrima(), "kg", 10.0);
        
        // Instancio o Gerenciador de Produção
        GerenciadorProducao gerenciador = new GerenciadorProducao(materiaPrima, cenario.getBudgetInicial(), new EstrategiaFilaDePedidos());
        
        // Instancio as maquinas
        MaquinaProcessamento maquinaProcessamento = new MaquinaProcessamento("Reator Farmacêutico / Mistura", 500, 0.15, 1.50, cenario, random);
        MaquinaEmbalagem maquinaEmbalagem = new MaquinaEmbalagem("Embaladora Blister / Selagem", 500, 0.10, 1.00, cenario, random);
        MaquinaInspecao maquinaInspecao = new MaquinaInspecao("Espectrômetro de Inspeção / Controle Anvisa", 500, 0.05, 2.00, cenario, random);
        
        // Registro as Máquinas na linha de produção
        gerenciador.adicionarMaquina(maquinaProcessamento);
        gerenciador.adicionarMaquina(maquinaEmbalagem);
        gerenciador.adicionarMaquina(maquinaInspecao);

        // Registro as demandas iniciais de Medicamentos
        gerenciador.registrarDemanda(new Demanda(TipoMedicamento.CONTROLADO, "Ritalina 40mg", 10, 4.50));
        gerenciador.registrarDemanda(new Demanda(TipoMedicamento.CONTINUO, "Losartana 50mg", 20, 4.50));
        gerenciador.registrarDemanda(new Demanda(TipoMedicamento.GENERICO, "Dipirona 500mg", 30, 4.50));

        // Retorno a fabrica inicializada
        return gerenciador;
    }
}
