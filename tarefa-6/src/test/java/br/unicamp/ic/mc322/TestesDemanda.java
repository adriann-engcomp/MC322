package br.unicamp.ic.mc322;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.unicamp.ic.mc322.exception.DemandaInvalidaException;
import br.unicamp.ic.mc322.model.Demanda;
import br.unicamp.ic.mc322.model.StatusDemanda;
import br.unicamp.ic.mc322.model.TipoMedicamento;
import org.junit.jupiter.api.Test;

/** Regras de criação e de mudança de status da demanda. */
class TestesDemanda {

    @Test
    void novaDemandaComecaPendente() {
        Demanda demanda = new Demanda(TipoMedicamento.GENERICO, "Dipirona", 10, 4.5);
        assertEquals(StatusDemanda.PENDENTE, demanda.getStatus());
    }

    @Test
    void quantidadeZeroERecusada() {
        assertThrows(DemandaInvalidaException.class,
                () -> new Demanda(TipoMedicamento.GENERICO, "Dipirona", 0, 4.5));
    }

    @Test
    void demandaCanceladaNaoEntraEmProducao() {
        Demanda demanda = new Demanda(TipoMedicamento.GENERICO, "Dipirona", 10, 4.5);
        demanda.cancelar();
        assertThrows(DemandaInvalidaException.class, demanda::iniciarProducao);
    }

    @Test
    void restaurarMantemNumeroEStatus() {
        Demanda demanda = Demanda.restaurar(500, TipoMedicamento.CONTINUO, "Losartana", 3, 4.5, StatusDemanda.CONCLUIDA);
        assertEquals(500, demanda.getNumero());
        assertEquals(StatusDemanda.CONCLUIDA, demanda.getStatus());
    }

    @Test
    void novaDemandaNaoRepeteNumeroRestaurado() {
        Demanda.restaurar(900, TipoMedicamento.GENERICO, "Dipirona", 1, 4.5, StatusDemanda.PENDENTE);
        Demanda nova = new Demanda(TipoMedicamento.GENERICO, "Paracetamol", 1, 4.5);
        assertTrue(nova.getNumero() > 900);
    }

    @Test
    void restaurarEmProducaoERecusado() {
        assertThrows(DemandaInvalidaException.class,
                () -> Demanda.restaurar(1, TipoMedicamento.GENERICO, "Dipirona", 1, 4.5, StatusDemanda.EM_PRODUCAO));
    }
}