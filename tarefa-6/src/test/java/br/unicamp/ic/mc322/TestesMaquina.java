package br.unicamp.ic.mc322;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.unicamp.ic.mc322.model.Cenario;
import br.unicamp.ic.mc322.model.Maquina;
import br.unicamp.ic.mc322.model.MaquinaProcessamento;
import br.unicamp.ic.mc322.model.Produto;
import br.unicamp.ic.mc322.model.StatusMaquina;
import br.unicamp.ic.mc322.model.StatusProduto;
import br.unicamp.ic.mc322.model.TipoMedicamento;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Composição, operação e restauração de estado da máquina. */
class TestesMaquina {

    private MaquinaProcessamento novoReator() {
        return new MaquinaProcessamento("Reator", 500, 0.0, 1.5, Cenario.FABRICA_NORMAL, new Random(1));
    }

    @Test
    void novaMaquinaJaTemSuasPartes() {
        Maquina maquina = novoReator();
        assertEquals(StatusMaquina.DESLIGADA, maquina.getStatus());
        assertEquals(2, maquina.getQuantidadeAtuadores());
        assertTrue(maquina.verificaMotorOperacional());
        assertTrue(maquina.verificaSensorCalibrado());
    }

    @Test
    void maquinaDesligadaNaoProcessa() {
        Maquina maquina = novoReator();
        Produto produto = TipoMedicamento.GENERICO.criarProduto("Dipirona #1", "LOTE-1");
        maquina.processar(produto);
        assertEquals(StatusProduto.AGUARDANDO, produto.getStatus());
    }

    @Test
    void maquinaLigadaProcessa() {
        Maquina maquina = novoReator();
        Produto produto = TipoMedicamento.GENERICO.criarProduto("Dipirona #1", "LOTE-1");
        maquina.ligar();
        maquina.processar(produto);
        assertEquals(StatusProduto.PROCESSADO, produto.getStatus());
    }

    @Test
    void restaurarEstadoCopiaMaquinaEPartes() {
        Maquina usada = novoReator();
        usada.registrarCicloDeUso();
        Maquina nova = novoReator();

        nova.restaurarEstado(usada.exportarEstado());

        assertEquals(usada.exportarEstado(), nova.exportarEstado());
    }

    @Test
    void quebradaComSaudeERecusada() {
        Maquina maquina = novoReator();
        Maquina.Estado e = maquina.exportarEstado();
        Maquina.Estado inconsistente = new Maquina.Estado(e.nome(), StatusMaquina.QUEBRADA,
                e.capacidadeMaxima(), e.probabilidadeFalha(), e.custoOperacao(), 80.0,
                e.saudeMinima(), e.historicoDeFalhas(), e.motor(), e.sensor(), e.atuadores());

        assertThrows(IllegalArgumentException.class, () -> maquina.restaurarEstado(inconsistente));
        assertFalse(maquina.estaQuebrada());
    }
}