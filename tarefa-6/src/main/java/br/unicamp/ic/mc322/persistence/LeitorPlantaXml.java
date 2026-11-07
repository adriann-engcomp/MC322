package br.unicamp.ic.mc322.persistence;

import br.unicamp.ic.mc322.exception.PersistenciaException;
import br.unicamp.ic.mc322.exception.PersistenciaException.Motivo;
import br.unicamp.ic.mc322.model.AtuadorValvula;
import br.unicamp.ic.mc322.model.Cenario;
import br.unicamp.ic.mc322.model.Demanda;
import br.unicamp.ic.mc322.model.LinhaDeProducao;
import br.unicamp.ic.mc322.model.Maquina;
import br.unicamp.ic.mc322.model.MaquinaEmbalagem;
import br.unicamp.ic.mc322.model.MaquinaInspecao;
import br.unicamp.ic.mc322.model.MaquinaProcessamento;
import br.unicamp.ic.mc322.model.MateriaPrima;
import br.unicamp.ic.mc322.model.MotorInducao;
import br.unicamp.ic.mc322.model.SensorCalibracao;
import br.unicamp.ic.mc322.model.StatusDemanda;
import br.unicamp.ic.mc322.model.StatusMaquina;
import br.unicamp.ic.mc322.model.TipoMedicamento;
import br.unicamp.ic.mc322.service.GerenciadorProducao;
import br.unicamp.ic.mc322.strategy.EstrategiaFilaDePedidos;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Monta uma planta nova a partir de um documento XML.
 * Os valores são conferidos pelas próprias classes do domínio ao serem criadas.
 *
 * @author Adriann e Alexsandro
 */
final class LeitorPlantaXml {

    private LeitorPlantaXml() {
    }

    /**
     * Cria a planta descrita no documento.
     *
     * @param doc Documento XML lido do arquivo.
     * @return Planta nova.
     * @throws PersistenciaException    Se faltar elemento ou atributo, ou se um número estiver ilegível.
     * @throws IllegalArgumentException Se algum valor for recusado pelas classes do domínio.
     */
    static GerenciadorProducao ler(Document doc) {
        Element raiz = doc.getDocumentElement();

        Cenario cenario = Cenario.valueOf(texto(raiz, "cenario"));
        double orcamento = numero(raiz, "orcamento");
        if (orcamento < 0) {
            throw new IllegalArgumentException("O orçamento não pode ser negativo (recebido: " + orcamento + ").");
        }

        Element mp = filho(raiz, "materiaPrima");
        MateriaPrima materiaPrima = new MateriaPrima(inteiro(mp, "id"), texto(mp, "nome"),
                numero(mp, "quantidade"), texto(mp, "unidade"), numero(mp, "custoPorUnidade"));

        // Mesma semente usada na InicializoFabrica.
        Random random = new Random(cenario.getSementeAleatoria());
        Element linhaXml = filho(raiz, "linhaDeProducao");
        LinhaDeProducao linha = new LinhaDeProducao(texto(linhaXml, "id"), texto(linhaXml, "nome"));
        for (Element m : filhos(linhaXml, "maquina")) {
            linha.adicionarMaquina(lerMaquina(m, cenario, random));
        }

        // A estratégia não é salva: a planta carregada começa na fila de pedidos.
        GerenciadorProducao planta = new GerenciadorProducao(
                materiaPrima, orcamento, new EstrategiaFilaDePedidos(), linha, cenario);

        for (Element d : filhos(filho(raiz, "demandas"), "demanda")) {
            planta.registrarDemanda(Demanda.restaurar(
                    inteiro(d, "numero"),
                    TipoMedicamento.valueOf(texto(d, "tipo")),
                    texto(d, "medicamento"),
                    inteiro(d, "quantidade"),
                    numero(d, "custoOperacionalPorUnidade"),
                    StatusDemanda.valueOf(texto(d, "status"))));
        }
        return planta;
    }

    private static Maquina lerMaquina(Element m, Cenario cenario, Random random) {
        String nome = texto(m, "nome");
        int capacidade = inteiro(m, "capacidadeMaxima");
        double probabilidade = numero(m, "probabilidadeFalha");
        double custo = numero(m, "custoOperacao");

        // A máquina cria suas próprias partes (composição); depois recebe os valores salvos.
        Maquina maquina;
        String tipo = texto(m, "tipo");
        if (tipo.equals("MaquinaProcessamento")) {
            maquina = new MaquinaProcessamento(nome, capacidade, probabilidade, custo, cenario, random);
        } else if (tipo.equals("MaquinaEmbalagem")) {
            maquina = new MaquinaEmbalagem(nome, capacidade, probabilidade, custo, cenario, random);
        } else if (tipo.equals("MaquinaInspecao")) {
            maquina = new MaquinaInspecao(nome, capacidade, probabilidade, custo, cenario, random);
        } else {
            throw new IllegalArgumentException("Tipo de máquina desconhecido: '" + tipo + "'.");
        }

        Element motor = filho(m, "motor");
        MotorInducao.Estado estadoMotor = new MotorInducao.Estado(
                texto(motor, "identificador"), numero(motor, "potenciaHP"), inteiro(motor, "rpm"),
                numero(motor, "temperaturaMaxima"), numero(motor, "temperaturaAtual"),
                numero(motor, "desgasteMecanico"), booleano(motor, "sobreaquecido"));

        Element sensor = filho(m, "sensor");
        SensorCalibracao.Estado estadoSensor = new SensorCalibracao.Estado(
                texto(sensor, "tipoParametro"), numero(sensor, "limiarMinimoPrecisao"),
                numero(sensor, "precisaoAtual"), booleano(sensor, "calibrado"), inteiro(sensor, "leiturasRealizadas"));

        List<AtuadorValvula.Estado> estadosAtuadores = new ArrayList<>();
        for (Element a : filhos(m, "atuador")) {
            estadosAtuadores.add(new AtuadorValvula.Estado(
                    inteiro(a, "id"), texto(a, "funcao"), numero(a, "pressaoTrabalhoBar"),
                    inteiro(a, "ciclosMaximosSemManutencao"), inteiro(a, "ciclosOperados"),
                    booleano(a, "travado"), numero(a, "integridadeVedacao")));
        }

        maquina.restaurarEstado(new Maquina.Estado(nome, StatusMaquina.valueOf(texto(m, "status")),
                capacidade, probabilidade, custo, numero(m, "saude"), numero(m, "saudeMinima"),
                inteiro(m, "historicoDeFalhas"), estadoMotor, estadoSensor, estadosAtuadores));
        return maquina;
    }

    // ==================== AUXILIARES DE XML ====================

    private static List<Element> filhos(Element pai, String nome) {
        List<Element> encontrados = new ArrayList<>();
        NodeList nos = pai.getChildNodes();
        for (int i = 0; i < nos.getLength(); i++) {
            Node no = nos.item(i);
            if (no instanceof Element elemento && elemento.getTagName().equals(nome)) {
                encontrados.add(elemento);
            }
        }
        return encontrados;
    }

    private static Element filho(Element pai, String nome) {
        List<Element> encontrados = filhos(pai, nome);
        if (encontrados.isEmpty()) {
            throw new PersistenciaException(Motivo.FORMATO_INVALIDO,
                    "Falta o elemento <" + nome + "> dentro de <" + pai.getTagName() + ">.");
        }
        return encontrados.get(0);
    }

    private static String texto(Element elemento, String atributo) {
        if (!elemento.hasAttribute(atributo)) {
            throw new PersistenciaException(Motivo.FORMATO_INVALIDO,
                    "Falta o atributo '" + atributo + "' em <" + elemento.getTagName() + ">.");
        }
        return elemento.getAttribute(atributo);
    }

    private static double numero(Element elemento, String atributo) {
        String valor = texto(elemento, atributo);
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            throw new PersistenciaException(Motivo.FORMATO_INVALIDO,
                    "Valor '" + valor + "' não é um número em " + atributo + " de <" + elemento.getTagName() + ">.");
        }
    }

    private static int inteiro(Element elemento, String atributo) {
        String valor = texto(elemento, atributo);
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new PersistenciaException(Motivo.FORMATO_INVALIDO,
                    "Valor '" + valor + "' não é um inteiro em " + atributo + " de <" + elemento.getTagName() + ">.");
        }
    }

    private static boolean booleano(Element elemento, String atributo) {
        return Boolean.parseBoolean(texto(elemento, atributo));
    }
}