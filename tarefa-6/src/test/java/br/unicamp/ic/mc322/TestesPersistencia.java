package br.unicamp.ic.mc322;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.unicamp.ic.mc322.exception.PersistenciaException;
import br.unicamp.ic.mc322.exception.PersistenciaException.Motivo;
import br.unicamp.ic.mc322.model.Demanda;
import br.unicamp.ic.mc322.menu.MenuPersistencia;
import br.unicamp.ic.mc322.persistence.PersistenciaPlanta;
import br.unicamp.ic.mc322.service.GerenciadorProducao;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Salvar, carregar e falhas de persistência.
 * Cada teste usa uma pasta temporária criada pelo JUnit, nunca o arquivo real dados/planta.xml.
 */
class TestesPersistencia {

    @TempDir
    Path pasta;

    private Path arquivo;
    private PersistenciaPlanta persistencia;
    private GerenciadorProducao planta;

    @BeforeEach
    void preparar() {
        arquivo = pasta.resolve("planta.xml");
        persistencia = new PersistenciaPlanta(arquivo);
        planta = PlantasDeTeste.novaPlanta();
    }

    private Motivo motivoAoCarregar() {
        return assertThrows(PersistenciaException.class, persistencia::carregar).getMotivo();
    }

    // ---------- Salvar e carregar ----------

    @Test
    void salvarCriaOArquivo() {
        persistencia.salvar(planta);
        assertTrue(Files.exists(arquivo));
    }

    @Test
    void carregarRecuperaEstoqueOrcamentoECenario() {
        planta.fabricarDemanda(0);
        persistencia.salvar(planta);

        GerenciadorProducao carregada = persistencia.carregar();

        assertEquals(planta.getMateriaPrima().getQuantidade(), carregada.getMateriaPrima().getQuantidade());
        assertEquals(planta.getBudget(), carregada.getBudget());
        assertEquals(planta.getCenario(), carregada.getCenario());
    }

    @Test
    void carregarRecuperaMaquinasESuasPartes() {
        planta.fabricarDemanda(0); // gera desgaste nas máquinas e nas partes
        persistencia.salvar(planta);

        GerenciadorProducao carregada = persistencia.carregar();

        assertEquals(planta.getMaquinas().size(), carregada.getMaquinas().size());
        for (int i = 0; i < planta.getMaquinas().size(); i++) {
            assertEquals(planta.getMaquinas().get(i).exportarEstado(), carregada.getMaquinas().get(i).exportarEstado());
        }
    }

    @Test
    void carregarRecuperaDemandasComStatus() {
        planta.fabricarDemanda(0);
        planta.getDemandas().get(1).cancelar();
        persistencia.salvar(planta);

        GerenciadorProducao carregada = persistencia.carregar();

        assertEquals(planta.getDemandas().size(), carregada.getDemandas().size());
        for (int i = 0; i < planta.getDemandas().size(); i++) {
            Demanda original = planta.getDemandas().get(i);
            Demanda recuperada = carregada.getDemandas().get(i);
            assertEquals(original.getNumero(), recuperada.getNumero());
            assertEquals(original.getMedicamento(), recuperada.getMedicamento());
            assertEquals(original.getQuantidade(), recuperada.getQuantidade());
            assertEquals(original.getStatus(), recuperada.getStatus());
        }
    }

    @Test
    void salvarDeNovoAtualizaOArquivo() {
        persistencia.salvar(planta);
        planta.comprarMateriaPrima(5.0);
        persistencia.salvar(planta);

        assertEquals(planta.getBudget(), persistencia.carregar().getBudget());
    }

    // ---------- Falhas ----------

    @Test
    void arquivoInexistente() {
        assertEquals(Motivo.ARQUIVO_INEXISTENTE, motivoAoCarregar());
    }

    @Test
    void arquivoVazio() throws IOException {
        Files.writeString(arquivo, "");
        assertEquals(Motivo.ARQUIVO_VAZIO, motivoAoCarregar());
    }

    @Test
    void arquivoQueNaoEXml() throws IOException {
        Files.writeString(arquivo, "isto não é XML");
        assertEquals(Motivo.FORMATO_INVALIDO, motivoAoCarregar());
    }

    @Test
    void arquivoComEstoqueNegativo() throws IOException {
        persistencia.salvar(planta);
        String xml = Files.readString(arquivo).replaceFirst("quantidade=\"100.0\"", "quantidade=\"-5.0\"");
        Files.writeString(arquivo, xml);

        assertEquals(Motivo.DADOS_INCOMPATIVEIS, motivoAoCarregar());
    }

    @Test
    void falhaDeLeitura() throws IOException {
        Files.createDirectory(arquivo); // o caminho é uma pasta, não um arquivo
        assertEquals(Motivo.FALHA_LEITURA_ESCRITA, motivoAoCarregar());
    }

    @Test
    void falhaAoCarregarMantemAPlantaEmUso() throws IOException {
        Files.writeString(arquivo, "isto não é XML");

        GerenciadorProducao resultado = MenuPersistencia.carregar(planta, persistencia);

        assertSame(planta, resultado);
    }
}