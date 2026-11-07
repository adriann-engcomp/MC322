package br.unicamp.ic.mc322;

import br.unicamp.ic.mc322.exception.PersistenciaException;
import br.unicamp.ic.mc322.model.Cenario;
import br.unicamp.ic.mc322.persistence.PersistenciaPlanta;
import br.unicamp.ic.mc322.service.GerenciadorProducao;
import br.unicamp.ic.mc322.service.InicializoFabrica;
import java.util.Scanner;

/**
 * Menu para salvar e carregar o estado da planta.
 *
 * @author Adriann e Alexsandro
 */
public final class MenuPersistencia {

    private MenuPersistencia() {
    }

    /**
     * Prepara a planta ao abrir o programa.
     * Se houver estado salvo, carrega. Se não houver, ou se o arquivo estiver com problema,
     * cria a planta padrão do cenário escolhido.
     *
     * @param scanner      Leitor do console.
     * @param persistencia Persistência da planta.
     * @return Planta pronta para uso.
     */
    public static GerenciadorProducao inicializar(Scanner scanner, PersistenciaPlanta persistencia) {
        if (persistencia.existeEstadoSalvo()) {
            try {
                GerenciadorProducao planta = persistencia.carregar();
                System.out.println("\nEstado salvo carregado de '" + persistencia.getArquivo() + "'.");
                return planta;
            } catch (PersistenciaException e) {
                ExibeErro.exibirPainelErro("Falha ao carregar", e.getMessage(),
                        "A planta será iniciada com os dados padrão. O arquivo salvo não foi alterado.");
            }
        } else {
            System.out.println("\nNenhum estado salvo. A planta será iniciada com os dados padrão.");
        }
        Cenario cenario = MenuCenario.exibir(scanner);
        return InicializoFabrica.inicializar(cenario);
    }

    /**
     * Salva o estado da planta.
     *
     * @param planta       Planta em uso.
     * @param persistencia Persistência da planta.
     */
    public static void salvar(GerenciadorProducao planta, PersistenciaPlanta persistencia) {
        try {
            persistencia.salvar(planta);
            System.out.println("\nEstado da planta salvo em '" + persistencia.getArquivo() + "'.");
        } catch (PersistenciaException e) {
            ExibeErro.exibirPainelErro("Falha ao salvar", e.getMessage(), "A planta em uso não foi alterada.");
        }
    }

    /**
     * Carrega o estado salvo. Se der certo, a planta carregada substitui a atual;
     * se falhar, a planta atual continua igual.
     *
     * @param plantaAtual  Planta em uso.
     * @param persistencia Persistência da planta.
     * @return A planta carregada, ou a planta atual se o carregamento falhar.
     */
    public static GerenciadorProducao carregar(GerenciadorProducao plantaAtual, PersistenciaPlanta persistencia) {
        try {
            GerenciadorProducao carregada = persistencia.carregar();
            System.out.println("\nEstado carregado de '" + persistencia.getArquivo() + "'. A planta anterior foi substituída.");
            return carregada;
        } catch (PersistenciaException e) {
            ExibeErro.exibirPainelErro("Falha ao carregar", e.getMessage(), "A planta em uso não foi alterada.");
            return plantaAtual;
        }
    }
}