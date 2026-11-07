package br.unicamp.ic.mc322.persistence;

import br.unicamp.ic.mc322.model.AtuadorValvula;
import br.unicamp.ic.mc322.model.Demanda;
import br.unicamp.ic.mc322.model.LinhaDeProducao;
import br.unicamp.ic.mc322.model.Maquina;
import br.unicamp.ic.mc322.model.MateriaPrima;
import br.unicamp.ic.mc322.model.MotorInducao;
import br.unicamp.ic.mc322.model.SensorCalibracao;
import br.unicamp.ic.mc322.service.GerenciadorProducao;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Converte a planta em um documento XML.
 *
 * @author Adriann e Alexsandro
 */
final class EscrevoPlantaXml {

    private EscrevoPlantaXml() {
    }

    /**
     * Monta o documento XML com o estado da planta.
     *
     * @param planta Planta a converter.
     * @return Documento XML pronto para gravar.
     * @throws ParserConfigurationException Se o Java não conseguir criar o documento.
     */
    static Document escrever(GerenciadorProducao planta) throws ParserConfigurationException {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        Element raiz = doc.createElement("planta");
        doc.appendChild(raiz);
        raiz.setAttribute("cenario", planta.getCenario().name());
        raiz.setAttribute("orcamento", String.valueOf(planta.getBudget()));

        MateriaPrima mp = planta.getMateriaPrima();
        Element materiaPrima = filhoNovo(doc, raiz, "materiaPrima");
        materiaPrima.setAttribute("id", String.valueOf(mp.getId()));
        materiaPrima.setAttribute("nome", mp.getNome());
        materiaPrima.setAttribute("quantidade", String.valueOf(mp.getQuantidade()));
        materiaPrima.setAttribute("unidade", mp.getUnidade());
        materiaPrima.setAttribute("custoPorUnidade", String.valueOf(mp.getCustoPorUnidade()));

        LinhaDeProducao linha = planta.getLinhaDeProducao();
        Element linhaXml = filhoNovo(doc, raiz, "linhaDeProducao");
        linhaXml.setAttribute("id", linha.getIdLinha());
        linhaXml.setAttribute("nome", linha.getNome());
        for (Maquina maquina : linha.getMaquinas()) {
            escreverMaquina(doc, linhaXml, maquina);
        }

        Element demandas = filhoNovo(doc, raiz, "demandas");
        for (Demanda d : planta.getDemandas()) {
            Element demanda = filhoNovo(doc, demandas, "demanda");
            demanda.setAttribute("numero", String.valueOf(d.getNumero()));
            demanda.setAttribute("tipo", d.getTipo().name());
            demanda.setAttribute("medicamento", d.getMedicamento());
            demanda.setAttribute("quantidade", String.valueOf(d.getQuantidade()));
            demanda.setAttribute("custoOperacionalPorUnidade", String.valueOf(d.getCustoOperacionalPorUnidade()));
            demanda.setAttribute("status", d.getStatus().name());
        }
        return doc;
    }

    private static void escreverMaquina(Document doc, Element linha, Maquina maquina) {
        Maquina.Estado e = maquina.exportarEstado();
        Element m = filhoNovo(doc, linha, "maquina");
        m.setAttribute("tipo", maquina.getClass().getSimpleName());
        m.setAttribute("nome", e.nome());
        m.setAttribute("status", e.status().name());
        m.setAttribute("capacidadeMaxima", String.valueOf(e.capacidadeMaxima()));
        m.setAttribute("probabilidadeFalha", String.valueOf(e.probabilidadeFalha()));
        m.setAttribute("custoOperacao", String.valueOf(e.custoOperacao()));
        m.setAttribute("saude", String.valueOf(e.saude()));
        m.setAttribute("saudeMinima", String.valueOf(e.saudeMinima()));
        m.setAttribute("historicoDeFalhas", String.valueOf(e.historicoDeFalhas()));

        MotorInducao.Estado motor = e.motor();
        Element motorXml = filhoNovo(doc, m, "motor");
        motorXml.setAttribute("identificador", motor.identificador());
        motorXml.setAttribute("potenciaHP", String.valueOf(motor.potenciaHP()));
        motorXml.setAttribute("rpm", String.valueOf(motor.rpm()));
        motorXml.setAttribute("temperaturaMaxima", String.valueOf(motor.temperaturaMaxima()));
        motorXml.setAttribute("temperaturaAtual", String.valueOf(motor.temperaturaAtual()));
        motorXml.setAttribute("desgasteMecanico", String.valueOf(motor.desgasteMecanico()));
        motorXml.setAttribute("sobreaquecido", String.valueOf(motor.sobreaquecido()));

        SensorCalibracao.Estado sensor = e.sensor();
        Element sensorXml = filhoNovo(doc, m, "sensor");
        sensorXml.setAttribute("tipoParametro", sensor.tipoParametro());
        sensorXml.setAttribute("limiarMinimoPrecisao", String.valueOf(sensor.limiarMinimoPrecisao()));
        sensorXml.setAttribute("precisaoAtual", String.valueOf(sensor.precisaoAtual()));
        sensorXml.setAttribute("calibrado", String.valueOf(sensor.calibrado()));
        sensorXml.setAttribute("leiturasRealizadas", String.valueOf(sensor.leiturasRealizadas()));

        for (AtuadorValvula.Estado atuador : e.atuadores()) {
            Element a = filhoNovo(doc, m, "atuador");
            a.setAttribute("id", String.valueOf(atuador.idAtuador()));
            a.setAttribute("funcao", atuador.funcao());
            a.setAttribute("pressaoTrabalhoBar", String.valueOf(atuador.pressaoTrabalhoBar()));
            a.setAttribute("ciclosMaximosSemManutencao", String.valueOf(atuador.ciclosMaximosSemManutencao()));
            a.setAttribute("ciclosOperados", String.valueOf(atuador.ciclosOperados()));
            a.setAttribute("travado", String.valueOf(atuador.travado()));
            a.setAttribute("integridadeVedacao", String.valueOf(atuador.integridadeVedacao()));
        }
    }

    private static Element filhoNovo(Document doc, Element pai, String nome) {
        Element elemento = doc.createElement(nome);
        pai.appendChild(elemento);
        return elemento;
    }
}