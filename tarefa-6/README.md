# Fábrica de Medicamentos — MC322

Dupla: Adriann e Alexsandro

Simulação, em Java orientado a objetos, de uma fábrica de medicamentos. A matéria-prima chega,
é conferida, passa por uma linha de produção (reator, embaladora e espectrômetro de inspeção) e vira
medicamentos aprovados ou rejeitados, sempre dentro de um orçamento. O projeto foi construído ao longo
de seis tarefas; cada uma acrescentou conceitos sobre a anterior.

Escolhemos o tema porque um dos integrantes faz iniciação científica com computação aplicada à saúde,
e porque a indústria farmacêutica exige precisão, rastreabilidade e controle de qualidade, seguindo as
Boas Práticas de Fabricação da Anvisa [1].

## Como executar

Requisito: JDK 21 (o Gradle baixa automaticamente, se for preciso).

```bash
./gradlew build    # compila e roda os testes
./gradlew run      # abre o menu da fábrica
./gradlew test     # roda só os testes
```

O relatório dos testes fica em `build/reports/tests/test/index.html` [14].
A pasta `build/` não é versionada: ela é gerada pelo `./gradlew build`.

## Funcionalidades

| Menu                      | O que faz                                                                               |
| ------------------------- | --------------------------------------------------------------------------------------- |
| 1. Demandas               | cadastra, lista e atualiza pedidos de medicamentos (controlado, contínuo ou genérico) |
| 2. Fabricação           | fabrica a próxima demanda pela estratégia ativa ou uma demanda escolhida              |
| 3. Consultar              | mostra o armazém e o estoque de matéria-prima                                         |
| 4. Comprar matéria-prima | abastece o estoque, debitando o orçamento                                              |
| 5. Gerenciar estratégia  | troca a regra de escolha da próxima demanda                                            |
| 6. Auditoria              | relatório da linha, das máquinas e de suas partes; reparo das máquinas               |
| 7. Linha de produção    | estado da esteira e das partes de cada máquina; liga e desliga a linha                 |
| 8. Salvar                 | grava o estado atual em `dados/planta.xml`; salvar de novo atualiza o arquivo         |
| 9. Carregar               | substitui a planta em uso pela do arquivo; se falhar, a planta em uso continua igual    |

Ao iniciar, o programa carrega o estado salvo, se houver. Se não houver, pede o cenário
(fábrica normal ou pandemia), que define orçamento, estoque e desgaste das máquinas.

## Síntese das tarefas

### Tarefa 1 — Matéria-prima

A matéria-prima chega com id, nome, quantidade e unidade, como na tabela que o operador confere antes de
liberar o caminhão. `MateriaPrima` verifica se há estoque (`verificarDisponibilidade`) e o consome
(`consumir`): se a demanda for maior que o estoque, a produção não começa.

### Tarefa 2 — Herança, produção e orçamento

- **Produtos:** classe abstrata `Produto` com três tipos de medicamento. Quanto mais exigente o
  medicamento, maior o consumo de insumo e mais rigorosa a inspeção [1]:

  | Tipo       | Exemplo   | Qualidade exigida | Insumo por unidade | Tempo por unidade |
  | ---------- | --------- | ----------------- | ------------------ | ----------------- |
  | Controlado | Ritalina  | 0,9               | 3 kg               | 10 min            |
  | Contínuo  | Losartana | 0,7               | 2 kg               | 6 min             |
  | Genérico  | Dipirona  | 0,5               | 1 kg               | 5 min             |
- **Máquinas:** classe abstrata `Maquina`, percorrida em ordem na esteira:

  - **Processamento** (R\$ 1,50 por unidade): a mistura pode sair irregular e aumentar a falha do produto;
  - **Embalagem** (R\$ 1,00): a selagem pode falhar;
  - **Inspeção** (R\$ 2,00): aprova ou rejeita; se ela mesma falhar, inverte a decisão.
- **Gerente:** `GerenciadorProducao` confere insumo e orçamento antes de produzir, consome, liga as
  máquinas, fabrica unidade por unidade e guarda tudo no armazém. O orçamento só cai na compra de
  insumo (R\$ 10/kg) e no uso das máquinas (R\$ 4,50 por unidade).

### Tarefa 3 — Strategy, enums e auditoria

- **Strategy:** a fábrica escolhe a próxima demanda por uma regra trocável [6]:
  - **Fila de Pedidos:** atende o pedido mais antigo, pois quem pediu primeiro recebe primeiro. É a regra
    FIFO (*First In, First Out*) de sequenciamento da produção [2, 3].
  - **Pandemia:** atende o pedido com mais unidades, sem olhar o orçamento, pois, em uma emergência sanitária,
    abastecer os hospitais rápido vale mais que poupar dinheiro. Na COVID-19, a lei chegou a dispensar a
    licitação para acelerar essas compras [4].
  - **SUS:** atende o pedido com mais unidades que o orçamento consegue pagar inteiro, pois, em uma licitação,
    a fábrica se compromete a entregar o pedido completo; se não entregar, pode ser multada e impedida de
    participar de novas licitações [5].
- **Enums:**
  - `StatusDemanda`, que sabe quais transições valem: pendente → em produção → concluída, e cancelada não volta;
  - `StatusProduto` e `StatusMaquina`;
  - `TipoMedicamento`, que cria o produto certo (Factory Method [6]);
  - `Cenario`: normal ou pandemia.
- **Auditoria:** a interface `Auditavel` (`gerarRelatorioDiagnostico`, `precisaManutencao`) permite
  auditar máquinas e produtos juntos.

### Tarefa 4 — Gradle, Javadoc e exceções

- **Gradle:** o projeto passou a ser compilado e executado pelo Gradle (Wrapper incluso).
- **Javadoc:** todas as classes públicas foram documentadas.
- **Exceções:** foram criadas exceções da planta, com `PlantaException` como base e três especializações [7]:

  - `DemandaInvalidaException`: pedido inexistente, em status incompatível ou com quantidade inválida;
  - `MaquinaIndisponivelException`: máquina quebrada na linha;
  - `RecursoInsuficienteException`: insumo ou orçamento insuficiente.

  As operações validam tudo antes de mudar o estado: se faltar algo, nada é alterado.

### Tarefa 5 — Composição, agregação e Lei de Deméter

- **Composição:** cada `Maquina` cria suas partes no próprio construtor e controla o ciclo de vida delas:

  - `MotorInducao` (1:1): aquece e se desgasta;
  - `SensorCalibracao` (1:1): perde a calibração com o uso;
  - `AtuadorValvula` (1:N, dois por máquina): pode travar.

  O estado das partes aumenta a chance de falha da máquina, e `reparar()` revisa todas elas.
- **Agregação:** `LinhaDeProducao` agrupa as máquinas da esteira e devolve uma lista que não pode ser
  alterada por fora.
- **Lei de Deméter [8]:** a máquina não expõe suas partes; oferece métodos como `verificaMotorOperacional()`.
- **Auditoria em cascata:** linha → máquinas → partes.

### Tarefa 6 — Persistência, testes e pacotes

- **Pacotes:** o código saiu do pacote padrão e foi organizado em pacotes (ver Estrutura).
- **Persistência:** a planta salva e carrega seu estado em XML (ver Persistência).
- **Testes:** 28 testes JUnit 5, executados pelo Gradle (ver Suíte de testes).

## Estrutura

```
src/main/java/br/unicamp/ic/mc322/
├── model/        entidades e componentes (máquinas e suas partes, demanda, produto, matéria-prima, enums)
├── service/      GerenciadorProducao e InicializoFabrica
├── strategy/     estratégias de produção (padrão Strategy)
├── persistence/  leitura e escrita do XML
├── exception/    exceções da planta
├── menu/         menus do terminal
└── Main.java
src/test/java/br/unicamp/ic/mc322/   testes JUnit 5
```

## Persistência

- O estado é salvo em `dados/planta.xml` (menu **8**). Salvar de novo atualiza o arquivo.
- O menu **9** substitui a planta em uso pela do arquivo. Se o carregamento falhar, a planta em uso
  continua igual.
- Ao abrir o programa:
  - se existe estado salvo, ele é carregado;
  - se não existe, a planta começa com os dados padrão do cenário escolhido;
  - se o arquivo estiver com problema, o erro é mostrado, o arquivo não é alterado e a planta começa
    com os dados padrão.

### Formato do arquivo (resumido)

```xml
<planta cenario="FABRICA_NORMAL" orcamento="955.0">
    <materiaPrima id="1" nome="Princípio Ativo Farmacêutico (Insumo)" quantidade="70.0" unidade="kg" custoPorUnidade="10.0"/>
    <linhaDeProducao id="LINHA-01" nome="Linha Principal de Medicamentos (Comprimidos)">
        <maquina tipo="MaquinaProcessamento" nome="Reator Farmacêutico / Mistura" status="DESLIGADA" saude="98.59" ...>
            <motor temperaturaAtual="25.0" desgasteMecanico="0.4" sobreaquecido="false" .../>
            <sensor precisaoAtual="99.3" calibrado="true" leiturasRealizadas="1" .../>
            <atuador id="1" ciclosOperados="1" travado="false" integridadeVedacao="99.95" .../>
            <atuador id="2" .../>
        </maquina>
        <!-- embaladora e espectrômetro -->
    </linhaDeProducao>
    <demandas>
        <demanda numero="1" tipo="CONTROLADO" medicamento="Ritalina 40mg" quantidade="10" status="CONCLUIDA" .../>
    </demandas>
</planta>
```

A hierarquia segue o domínio: a planta tem a linha, a linha tem as máquinas e cada máquina tem suas partes.
A leitura e a escrita usam a API de XML do próprio Java [11], sem bibliotecas externas.

### Classes

| Classe                 | Papel                                   |
| ---------------------- | --------------------------------------- |
| `PersistenciaPlanta` | lê e grava o arquivo e trata as falhas |
| `EscrevoPlantaXml`   | converte a planta em XML                |
| `LeitorPlantaXml`    | monta uma planta nova a partir do XML   |

Cada classe tem um só motivo para mudar [10], e a conversão fica separada do domínio, como no padrão
Data Mapper [9]. As classes do domínio não sabem nada de arquivos. Para salvar o estado interno da
máquina sem expor suas partes (Tarefa 5), a `Maquina`, o `MotorInducao`, o `SensorCalibracao` e o
`AtuadorValvula` exportam e restauram um `record Estado` (padrão Memento [6]). Ao carregar, cada
máquina é criada pelo próprio construtor, que cria as partes, e depois recebe os valores salvos.

### Falhas tratadas (`PersistenciaException.Motivo`)

| Motivo                    | Quando                                                                                                                            |
| ------------------------- | --------------------------------------------------------------------------------------------------------------------------------- |
| `ARQUIVO_INEXISTENTE`   | não há arquivo salvo                                                                                                            |
| `ARQUIVO_VAZIO`         | o arquivo não tem conteúdo                                                                                                      |
| `FORMATO_INVALIDO`      | não é XML, ou falta elemento/atributo, ou um número está ilegível                                                            |
| `FALHA_LEITURA_ESCRITA` | erro do sistema ao ler ou gravar                                                                                                  |
| `DADOS_INCOMPATIVEIS`   | um valor viola as regras da planta (estoque negativo, status inexistente, demanda em produção, máquina quebrada com saúde...) |

`PersistenciaException` estende a `PlantaException` da Tarefa 4, então os menus já sabiam tratá-la.
Os erros do Java (`IOException`, `SAXException`) são traduzidos para essa exceção [7].

## Escopo e Limitações

### Dados persistidos

| Dado                                                                                                  | Por quê                                                                                                            |
| ----------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------- |
| Estoque de matéria-prima                                                                             | exigido; define se uma demanda pode ser fabricada                                                                   |
| Máquinas e suas partes (motor, sensor, atuadores): status, saúde, desgaste, falhas e configuração | exigido; o desgaste e o status mudam a chance de falha e podem impedir a produção                                 |
| Demandas com número e status                                                                         | exigido; o status define o que ainda pode ser fabricado. O número é mantido para que pedidos novos não o repitam |
| Orçamento                                                                                            | limita a produção e a compra; sem ele cada carregamento "daria dinheiro novo"                                     |
| Cenário                                                                                              | necessário para recriar as máquinas (desgaste e semente aleatória)                                               |

### Dados não persistidos

| Dado                            | Por quê                                                                                                                     |
| ------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| Produtos do armazém            | opcional no enunciado; é o histórico do que foi fabricado e não afeta a produção. Ao carregar, o armazém começa vazio |
| Estratégia de produção ativa | escolha do operador, não estado da planta. Ao carregar, a planta volta para a Fila de Pedidos                               |
| Estado do gerador aleatório    | é recriado com a semente do cenário, como na inicialização                                                               |
| Status `EM_PRODUCAO`          | só existe durante a fabricação; um arquivo com esse status é recusado                                                    |

### Limitações

- Há um único arquivo de estado (`dados/planta.xml`).
- A planta tem um único tipo de insumo e sempre três máquinas com dois atuadores cada.

### Suíte de testes

São 28 testes JUnit 5 [12]. Cada teste cria seus próprios dados [13] (`PlantasDeTeste` monta uma planta
nova a cada chamada), então os testes não dependem da ordem nem uns dos outros. As máquinas de teste têm
chance de falha zero, para o resultado não depender do acaso. Os testes de persistência usam `@TempDir`,
uma pasta temporária apagada ao final [12], e nunca tocam em `dados/planta.xml`. Nenhum teste usa o teclado.

| Classe                        | O que verifica                                                                                                                                                                                                                                                                                   |
| ----------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `TestesDemanda`             | demanda nasce pendente; quantidade zero é recusada; cancelada não entra em produção; `restaurar` mantém número e status, não deixa pedidos novos repetirem o número e recusa `EM_PRODUCAO`                                                                                           |
| `TestesMaquina`             | a máquina já nasce com suas partes; desligada não processa e ligada processa; restaurar copia o estado da máquina e das partes; máquina quebrada com saúde é recusada                                                                                                                     |
| `TestesGerenciadorProducao` | fabricar consome insumo e orçamento; estoque insuficiente lança exceção sem alterar nada; máquina quebrada impede a produção; demanda concluída não é refeita; compra válida e compra acima do orçamento                                                                             |
| `TestesPersistencia`        | salvar cria o arquivo; carregar recupera estoque, orçamento, cenário, máquinas com partes e demandas com status; salvar de novo atualiza; arquivo inexistente, vazio, que não é XML, com estoque negativo e falha de leitura dão o motivo certo; falha ao carregar mantém a planta em uso |

## Referências

A justificativa completa de cada tarefa está em `justificativa.txt`.

[1] AGÊNCIA NACIONAL DE VIGILÂNCIA SANITÁRIA (Brasil). Resolução da Diretoria Colegiada - RDC nº 658, de 30 de março de 2022. Diretrizes Gerais de Boas Práticas de Fabricação de Medicamentos. Brasília: Anvisa, 2022.

[2] SLACK, Nigel; BRANDON-JONES, Alistair; JOHNSTON, Robert. Administração da Produção. 8. ed. São Paulo: Atlas, 2018. (regras de sequenciamento)

[3] LEAN ENTERPRISE INSTITUTE. First In, First Out (FIFO). Lean Lexicon. Disponível em: [https://www.lean.org/lexicon-terms/first-in-first-out-fifo/](https://www.lean.org/lexicon-terms/first-in-first-out-fifo/). Acesso em: 9 nov. 2026.

[4] BRASIL. Lei nº 13.979, de 6 de fevereiro de 2020. Medidas para enfrentamento da emergência de saúde pública decorrente do coronavírus. Art. 4º. Disponível em: [https://www.planalto.gov.br/ccivil_03/_ato2019-2022/2020/lei/l13979.htm](https://www.planalto.gov.br/ccivil_03/_ato2019-2022/2020/lei/l13979.htm). Acesso em: 9 nov. 2026.

[5] BRASIL. Lei nº 14.133, de 1º de abril de 2021. Lei de Licitações e Contratos Administrativos. Arts. 155 e 156. Disponível em: [https://www.planalto.gov.br/ccivil_03/_ato2019-2022/2021/lei/l14133.htm](https://www.planalto.gov.br/ccivil_03/_ato2019-2022/2021/lei/l14133.htm). Acesso em: 9 nov. 2026.

[6] GAMMA, Erich; HELM, Richard; JOHNSON, Ralph; VLISSIDES, John. Design Patterns: Elements of Reusable Object-Oriented Software. Reading: Addison-Wesley, 1995. (Strategy, Factory Method, Memento)

[7] BLOCH, Joshua. Effective Java. 3. ed. Boston: Addison-Wesley, 2018. (Itens 72 e 73: exceções padrão e tradução de exceções)

[8] LIEBERHERR, Karl; HOLLAND, Ian. Assuring Good Style for Object-Oriented Programs. IEEE Software, v. 6, n. 5, p. 38-48, 1989.

[9] FOWLER, Martin. Patterns of Enterprise Application Architecture. Boston: Addison-Wesley, 2002. (Data Mapper)

[10] MARTIN, Robert C. Clean Architecture: A Craftsman's Guide to Software Structure and Design. Boston: Prentice Hall, 2017. (Princípio da Responsabilidade Única)

[11] ORACLE. Java API for XML Processing (JAXP). Java SE 21 Documentation. Disponível em: [https://docs.oracle.com/en/java/javase/21/docs/api/java.xml/module-summary.html](https://docs.oracle.com/en/java/javase/21/docs/api/java.xml/module-summary.html). Acesso em: 9 nov. 2026.

[12] JUNIT TEAM. JUnit 5 User Guide. Disponível em: [https://junit.org/junit5/docs/current/user-guide/](https://junit.org/junit5/docs/current/user-guide/). Acesso em: 9 nov. 2026.

[13] MESZAROS, Gerard. xUnit Test Patterns: Refactoring Test Code. Boston: Addison-Wesley, 2007. (Fresh Fixture)

[14] GRADLE INC. Testing in Java & JVM projects. Disponível em: [https://docs.gradle.org/current/userguide/java_testing.html](https://docs.gradle.org/current/userguide/java_testing.html). Acesso em: 9 nov. 2026.
