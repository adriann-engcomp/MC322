package br.unicamp.ic.mc322;

import br.unicamp.ic.mc322.model.Cenario;
import br.unicamp.ic.mc322.model.Demanda;
import br.unicamp.ic.mc322.model.LinhaDeProducao;
import br.unicamp.ic.mc322.model.MaquinaEmbalagem;
import br.unicamp.ic.mc322.model.MaquinaInspecao;
import br.unicamp.ic.mc322.model.MaquinaProcessamento;
import br.unicamp.ic.mc322.model.MateriaPrima;
import br.unicamp.ic.mc322.model.TipoMedicamento;
import br.unicamp.ic.mc322.service.GerenciadorProducao;
import br.unicamp.ic.mc322.strategy.EstrategiaFilaDePedidos;
import java.util.Random;

/**
 * Cria plantas novas para os testes.
 * As máquinas têm chance de falha zero, então o resultado da produção não depende do acaso.
 */
final class PlantasDeTeste {

    private PlantasDeTeste() {
    }

    /**
     * Planta com 100 kg de insumo, R$ 1000 e três demandas pendentes.
     * Cada unidade custa R$ 4,50 para passar pelas três máquinas.
     */
    static GerenciadorProducao novaPlanta() {
        Cenario cenario = Cenario.FABRICA_NORMAL;
        Random random = new Random(1);

        LinhaDeProducao linha = new LinhaDeProducao("LINHA-T", "Linha de Teste");
        linha.adicionarMaquina(new MaquinaProcessamento("Reator", 500, 0.0, 1.50, cenario, random));
        linha.adicionarMaquina(new MaquinaEmbalagem("Blister", 500, 0.0, 1.00, cenario, random));
        linha.adicionarMaquina(new MaquinaInspecao("Espectrometro", 500, 0.0, 2.00, cenario, random));

        MateriaPrima materiaPrima = new MateriaPrima(1, "Insumo", 100.0, "kg", 10.0);
        GerenciadorProducao planta = new GerenciadorProducao(
                materiaPrima, 1000.0, new EstrategiaFilaDePedidos(), linha, cenario);

        planta.registrarDemanda(new Demanda(TipoMedicamento.CONTROLADO, "Ritalina 40mg", 5, 4.5));
        planta.registrarDemanda(new Demanda(TipoMedicamento.CONTINUO, "Losartana 50mg", 10, 4.5));
        planta.registrarDemanda(new Demanda(TipoMedicamento.GENERICO, "Dipirona 500mg", 20, 4.5));
        return planta;
    }
}