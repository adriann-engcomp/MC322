package br.unicamp.ic.mc322;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.unicamp.ic.mc322.exception.DemandaInvalidaException;
import br.unicamp.ic.mc322.exception.MaquinaIndisponivelException;
import br.unicamp.ic.mc322.exception.RecursoInsuficienteException;
import br.unicamp.ic.mc322.model.Demanda;
import br.unicamp.ic.mc322.model.Maquina;
import br.unicamp.ic.mc322.model.StatusDemanda;
import br.unicamp.ic.mc322.model.StatusMaquina;
import br.unicamp.ic.mc322.model.TipoMedicamento;
import br.unicamp.ic.mc322.service.GerenciadorProducao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Regras de produção e de compra do gerenciador. */
class TestesGerenciadorProducao {

    private GerenciadorProducao planta;

    @BeforeEach
    void criarPlanta() {
        planta = PlantasDeTeste.novaPlanta();
    }

    @Test
    void fabricarConsomeInsumoEOrcamento() {
        planta.fabricarDemanda(0); // Ritalina: 5 unidades x 3 kg; 5 x R$ 4,50

        assertEquals(85.0, planta.getMateriaPrima().getQuantidade(), 1e-9);
        assertEquals(977.5, planta.getBudget(), 1e-9);
        assertEquals(StatusDemanda.CONCLUIDA, planta.getDemandas().get(0).getStatus());
        assertEquals(5, planta.getProdutosFabricados().size());
    }

    @Test
    void estoqueInsuficienteNaoAlteraNada() {
        planta.registrarDemanda(new Demanda(TipoMedicamento.CONTROLADO, "Ritalina", 40, 4.5)); // 120 kg

        assertThrows(RecursoInsuficienteException.class, () -> planta.fabricarDemanda(3));
        assertEquals(100.0, planta.getMateriaPrima().getQuantidade(), 1e-9);
        assertEquals(1000.0, planta.getBudget(), 1e-9);
        assertEquals(StatusDemanda.PENDENTE, planta.getDemandas().get(3).getStatus());
    }

    @Test
    void maquinaQuebradaImpedeProducao() {
        Maquina reator = planta.getMaquinas().get(0);
        Maquina.Estado e = reator.exportarEstado();
        reator.restaurarEstado(new Maquina.Estado(e.nome(), StatusMaquina.QUEBRADA, e.capacidadeMaxima(),
                e.probabilidadeFalha(), e.custoOperacao(), 0.0, e.saudeMinima(), e.historicoDeFalhas(),
                e.motor(), e.sensor(), e.atuadores()));

        assertThrows(MaquinaIndisponivelException.class, () -> planta.fabricarDemanda(0));
    }

    @Test
    void demandaConcluidaNaoEFabricadaDeNovo() {
        planta.fabricarDemanda(0);
        assertThrows(DemandaInvalidaException.class, () -> planta.fabricarDemanda(0));
    }

    @Test
    void compraAbasteceEstoqueEDebitaOrcamento() {
        planta.comprarMateriaPrima(10.0); // 10 kg x R$ 10,00
        assertEquals(110.0, planta.getMateriaPrima().getQuantidade(), 1e-9);
        assertEquals(900.0, planta.getBudget(), 1e-9);
    }

    @Test
    void compraAcimaDoOrcamentoERecusada() {
        assertThrows(RecursoInsuficienteException.class, () -> planta.comprarMateriaPrima(1000.0));
        assertEquals(1000.0, planta.getBudget(), 1e-9);
    }
}