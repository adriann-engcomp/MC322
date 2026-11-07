package br.unicamp.ic.mc322.persistence;

import br.unicamp.ic.mc322.exception.PersistenciaException;
import br.unicamp.ic.mc322.exception.PersistenciaException.Motivo;
import br.unicamp.ic.mc322.exception.PlantaException;
import br.unicamp.ic.mc322.service.GerenciadorProducao;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Salva e carrega o estado da planta em um arquivo XML.
 * <p>
 * Cuida do arquivo e dos erros. A conversão fica em {@link EscritorPlantaXml} (planta para XML)
 * e em {@link LeitorPlantaXml} (XML para planta).
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class PersistenciaPlanta {

    private final Path arquivo;

    /**
     * Cria a persistência ligada a um arquivo.
     *
     * @param arquivo Caminho do arquivo XML.
     */
    public PersistenciaPlanta(Path arquivo) {
        this.arquivo = arquivo;
    }

    /**
     * Obtém o caminho do arquivo XML.
     *
     * @return Caminho do arquivo.
     */
    public Path getArquivo() {
        return arquivo;
    }

    /**
     * Indica se já existe um estado salvo.
     *
     * @return {@code true} se o arquivo existir.
     */
    public boolean existeEstadoSalvo() {
        return Files.exists(arquivo);
    }

    /**
     * Salva o estado da planta no arquivo, substituindo o conteúdo anterior.
     *
     * @param planta Planta a salvar.
     * @throws PersistenciaException Se não for possível gravar o arquivo.
     */
    public void salvar(GerenciadorProducao planta) {
        try {
            Document doc = EscritorPlantaXml.escrever(planta);

            Files.createDirectories(arquivo.toAbsolutePath().getParent());

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(arquivo.toFile()));
        } catch (ParserConfigurationException | TransformerException | IOException e) {
            throw new PersistenciaException(Motivo.FALHA_LEITURA_ESCRITA,
                    "Não foi possível salvar em '" + arquivo + "': " + e.getMessage(), e);
        }
    }

    /**
     * Lê o arquivo e monta uma planta nova com os dados salvos.
     * A planta em uso não é alterada: quem chama decide se troca pela nova.
     *
     * @return Planta recriada a partir do arquivo.
     * @throws PersistenciaException Se o arquivo não existir, estiver vazio, corrompido,
     *                               não puder ser lido ou tiver dados que violam as regras da planta.
     */
    public GerenciadorProducao carregar() {
        if (!Files.exists(arquivo)) {
            throw new PersistenciaException(Motivo.ARQUIVO_INEXISTENTE,
                    "Nenhum estado salvo em '" + arquivo + "'.");
        }

        Document doc;
        try {
            byte[] conteudo = Files.readAllBytes(arquivo);
            if (conteudo.length == 0) {
                throw new PersistenciaException(Motivo.ARQUIVO_VAZIO, "O arquivo '" + arquivo + "' está vazio.");
            }
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(conteudo));
        } catch (SAXException e) {
            throw new PersistenciaException(Motivo.FORMATO_INVALIDO,
                    "O arquivo '" + arquivo + "' não é um XML válido: " + e.getMessage(), e);
        } catch (IOException | ParserConfigurationException e) {
            throw new PersistenciaException(Motivo.FALHA_LEITURA_ESCRITA,
                    "Não foi possível ler '" + arquivo + "': " + e.getMessage(), e);
        }

        try {
            return LeitorPlantaXml.ler(doc);
        } catch (PersistenciaException e) {
            throw e;
        } catch (IllegalArgumentException | PlantaException e) {
            // Valores recusados pelas próprias classes (estoque negativo, status desconhecido, etc.)
            throw new PersistenciaException(Motivo.DADOS_INCOMPATIVEIS,
                    "Dados inválidos em '" + arquivo + "': " + e.getMessage(), e);
        }
    }
}