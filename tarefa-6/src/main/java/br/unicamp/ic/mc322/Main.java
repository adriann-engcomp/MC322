package br.unicamp.ic.mc322;

import br.unicamp.ic.mc322.menu.LeitorDeOpcoes;
import br.unicamp.ic.mc322.menu.MenuAuditoria;
import br.unicamp.ic.mc322.menu.MenuConsultar;
import br.unicamp.ic.mc322.menu.MenuDemandas;
import br.unicamp.ic.mc322.menu.MenuEstrategias;
import br.unicamp.ic.mc322.menu.MenuFabricacao;
import br.unicamp.ic.mc322.menu.MenuLinhaProducao;
import br.unicamp.ic.mc322.menu.MenuMateriaPrima;
import br.unicamp.ic.mc322.menu.MenuPersistencia;
import br.unicamp.ic.mc322.model.LinhaDeProducao;
import br.unicamp.ic.mc322.persistence.PersistenciaPlanta;
import br.unicamp.ic.mc322.service.GerenciadorProducao;

import java.nio.file.Path;
import java.util.Scanner;

/**
 * Ponto de entrada da fábrica farmacêutica.
 * <p>
 * Solicita o cenário, inicializa a fábrica com o Agrupador {@link LinhaDeProducao}
 * e coordena o menu principal com todas as opções hierárquicas do sistema.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class Main {

    /**
     * Construtor privado para prevenir instanciação direta da classe utilitária de entrada.
     */
    private Main() {
    }

    /**
     * Método principal que pede o cenário, inicializa a fábrica e controla o loop do menu interativo.
     *
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PersistenciaPlanta persistencia = new PersistenciaPlanta(Path.of("dados", "planta.xml"));
        GerenciadorProducao gerenciador = MenuPersistencia.inicializar(scanner, persistencia);

        boolean executando = true;

        while (executando) {
            System.out.println("\n==============================================================");
            System.out.println("       FÁBRICA DE MEDICAMENTOS - Crescendo para te ajudar!!!");
            System.out.printf(" ESTRATÉGIA ATUAL: [%s]\n", gerenciador.getEstrategiaAtual().getNomeEstrategia());
            System.out.printf(" CENÁRIO ATIVO:    [%s]\n", gerenciador.getCenario().getNome());
            System.out.printf(" BUDGET ATUAL:     R$ %.2f\n", gerenciador.getBudget());
            System.out.printf(" LINHA FABRIL:     [%s] %s\n", gerenciador.getLinhaDeProducao().getIdLinha(), gerenciador.getLinhaDeProducao().getNome());
            System.out.println("==============================================================");

            System.out.println("MENU PRINCIPAL");
            System.out.println("1. Demandas");
            System.out.println("2. Fabricacao");
            System.out.println("3. Consultar");
            System.out.println("4. Comprar materia-prima");
            System.out.println("5. Gerenciar estrategia");
            System.out.println("6. Auditoria");
            System.out.println("7. Linha de producao (Agrupador)");
            System.out.println("8. Salvar estado da planta");
            System.out.println("9. Carregar estado da planta");
            System.out.println("0. Sair");
            System.out.print("ESCOLHA: ");

            int opcao = LeitorDeOpcoes.opcao(scanner);
            if (opcao == -1) {
                continue;
            }

            switch (opcao) {
                case 1:
                    MenuDemandas.exibir(scanner, gerenciador);
                    break;
                case 2:
                    MenuFabricacao.exibir(scanner, gerenciador);
                    break;
                case 3:
                    MenuConsultar.exibir(scanner, gerenciador);
                    break;
                case 4:
                    MenuMateriaPrima.exibir(scanner, gerenciador);
                    break;
                case 5:
                    MenuEstrategias.exibir(scanner, gerenciador);
                    break;
                case 6:
                    MenuAuditoria.exibir(scanner, gerenciador);
                    break;
                case 7:
                    MenuLinhaProducao.exibir(scanner, gerenciador);
                    break;
                case 8:
                    MenuPersistencia.salvar(gerenciador, persistencia);
                    break;
                case 9:
                    gerenciador = MenuPersistencia.carregar(gerenciador, persistencia);
                    break;
                case 0:
                    System.out.println("\nEncerrando o sistema da fábrica farmacêutica. Até logo!");
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida! Escolha um número válido do menu principal (0 a 9).");
                    break;
            }
        }
        scanner.close();
    }
}
