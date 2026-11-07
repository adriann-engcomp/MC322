
# Fábrica de Medicamentos — Tarefa 6 (MC322)

Dupla: Adriann e Alexsandro

Simulação de uma fábrica de medicamentos: estoque de insumo, linha de produção com três máquinas
(reator, embaladora e espectrômetro), cada uma composta por motor, sensor e atuadores, demandas com
status, estratégias de produção e orçamento.

Na Tarefa 6 a planta passou a **salvar e carregar seu estado em XML**, ganhou **testes automatizados
com JUnit 5** e foi organizada em **pacotes**.

## Funcionalidades

| Menu                      | O que faz                                                                                                  |
| ------------------------- | ---------------------------------------------------------------------------------------------------------- |
| 1. Demandas               | cadastra, lista e atualiza pedidos de medicamentos (controlado, contínuo ou genérico)                    |
| 2. Fabricação           | fabrica a próxima demanda pela estratégia ativa ou uma demanda escolhida, consumindo insumo e orçamento |
| 3. Consultar              | mostra o armazém e o estoque de matéria-prima                                                            |
| 4. Comprar matéria-prima | abastece o estoque, debitando o orçamento                                                                 |
| 5. Gerenciar estratégia  | troca a regra de escolha da próxima demanda: fila de pedidos, pandemia ou SUS                             |
| 6. Auditoria              | relatório da linha, das máquinas e de suas partes, e reparo das máquinas                                |
| 7. Linha de produção    | estado da esteira e das partes de cada máquina; liga e desliga a linha                                    |
| 8. Salvar / 9. Carregar   | salva e carrega o estado da planta em XML                                                                  |

Ao iniciar, o programa pede o cenário (fábrica normal ou pandemia), que define orçamento, estoque e
desgaste das máquinas. Se houver estado salvo, ele é carregado no lugar disso.

## Evolução do projeto

| Tarefa | O que foi acrescentado                                                                                                                               |
| ------ | ---------------------------------------------------------------------------------------------------------------------------------------------------- |
| 1      | matéria-prima com conferência de estoque e consumo                                                                                                 |
| 2      | herança em `Produto` (três tipos de medicamento) e em `Maquina` (três máquinas); `GerenciadorProducao` com demandas, armazém e orçamento |
| 3      | padrão Strategy para escolher a próxima demanda; enums de status, tipo de medicamento e cenário; interface `Auditavel`                          |
| 4      | Gradle, Javadoc e exceções da planta (`PlantaException` e subclasses)                                                                            |
| 5      | composição (máquina com motor, sensor e atuadores) e agregação (`LinhaDeProducao`), com Lei de Deméter                                       |
| 6      | persistência em XML, testes JUnit 5 e organização em pacotes                                                                                      |

A explicação de cada etapa, com as referências, está em `justificativa.txt`.

## Como executar

Requisito: JDK 21 (o Gradle baixa automaticamente, se for preciso).

```bash
./gradlew build    # compila e roda os testes
./gradlew run      # abre o menu da fábrica
./gradlew test     # roda só os testes
```

O relatório dos testes fica em `build/reports/tests/test/index.html`.
A pasta `build/` não é versionada: ela é gerada pelo `./gradlew build`.

## Estrutura

```
src/main/java/br/unicamp/ic/mc322/
├── model/        entidades e componentes (máquinas e suas partes, demanda, matéria-prima...)
├── service/      GerenciadorProducao e InicializoFabrica
├── strategy/     estratégias de produção (padrão Strategy)
├── persistence/  leitura e escrita do XML
├── exception/    exceções da planta
├── menu/         menus do terminal
└── Main.java
src/test/java/br/unicamp/ic/mc322/   testes JUnit 5
```

## Persistência

- O estado é salvo em `dados/planta.xml` (menu **8. Salvar estado da planta**). Salvar de novo atualiza o arquivo.
- **9. Carregar estado da planta** substitui a planta em uso pela do arquivo. Se o carregamento falhar,
  a planta em uso continua igual.
- Ao abrir o programa: se existe estado salvo, ele é carregado; se não existe, a planta começa com
  os dados padrão do cenário escolhido. Se o arquivo estiver com problema, o erro é mostrado,
  o arquivo não é alterado e a planta começa com os dados padrão.

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

### Classes

| Classe                 | Papel                                   |
| ---------------------- | --------------------------------------- |
| `PersistenciaPlanta` | lê e grava o arquivo e trata as falhas |
| `EscrevoPlantaXml`   | converte a planta em XML                |
| `LeitorPlantaXml`    | monta uma planta nova a partir do XML   |

As classes do domínio não sabem nada de arquivos. Para salvar o estado interno da máquina sem expor
suas partes, a `Maquina`, o `MotorInducao`, o `SensorCalibracao` e o `AtuadorValvula` exportam e
restauram um `record Estado` (padrão Memento). Ao carregar, cada máquina é criada pelo próprio
construtor, que cria as partes, e depois recebe os valores salvos.

### Falhas tratadas (`PersistenciaException.Motivo`)

| Motivo                    | Quando                                                                                                                            |
| ------------------------- | --------------------------------------------------------------------------------------------------------------------------------- |
| `ARQUIVO_INEXISTENTE`   | não há arquivo salvo                                                                                                            |
| `ARQUIVO_VAZIO`         | o arquivo não tem conteúdo                                                                                                      |
| `FORMATO_INVALIDO`      | não é XML, ou falta elemento/atributo, ou um número está ilegível                                                            |
| `FALHA_LEITURA_ESCRITA` | erro do sistema ao ler ou gravar                                                                                                  |
| `DADOS_INCOMPATIVEIS`   | um valor viola as regras da planta (estoque negativo, status inexistente, demanda em produção, máquina quebrada com saúde...) |

`PersistenciaException` estende a `PlantaException` da Tarefa 4, então os menus já sabiam tratá-la.

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

São 28 testes. Cada teste cria seus próprios dados (`PlantasDeTeste` monta uma planta nova a cada
chamada), então os testes não dependem da ordem nem uns dos outros. As máquinas de teste têm chance de
falha zero, para o resultado não depender do acaso. Os testes de persistência usam `@TempDir`, uma pasta
temporária apagada ao final, e nunca tocam em `dados/planta.xml`. Nenhum teste usa o teclado.

| Classe                        | O que verifica                                                                                                                                                                                                                                                                                   |
| ----------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `TestesDemanda`             | demanda nasce pendente; quantidade zero é recusada; cancelada não entra em produção; `restaurar` mantém número e status, não deixa pedidos novos repetirem o número e recusa `EM_PRODUCAO`                                                                                           |
| `TestesMaquina`             | a máquina já nasce com suas partes; desligada não processa e ligada processa; restaurar copia o estado da máquina e das partes; máquina quebrada com saúde é recusada                                                                                                                     |
| `TestesGerenciadorProducao` | fabricar consome insumo e orçamento; estoque insuficiente lança exceção sem alterar nada; máquina quebrada impede a produção; demanda concluída não é refeita; compra válida e compra acima do orçamento                                                                             |
| `TestesPersistencia`        | salvar cria o arquivo; carregar recupera estoque, orçamento, cenário, máquinas com partes e demandas com status; salvar de novo atualiza; arquivo inexistente, vazio, que não é XML, com estoque negativo e falha de leitura dão o motivo certo; falha ao carregar mantém a planta em uso |

## Referências

- GAMMA, E. et al. *Design Patterns*. Addison-Wesley, 1995. (Memento, Strategy, Factory Method)
- FOWLER, M. *Patterns of Enterprise Application Architecture*. Addison-Wesley, 2002. (Data Mapper)
- BLOCH, J. *Effective Java*. 3. ed. Addison-Wesley, 2018. (Itens 1, 17, 34, 49, 72 e 73)
- MARTIN, R. C. *Clean Architecture*. Prentice Hall, 2017. (Princípio da Responsabilidade Única)
- MESZAROS, G. *xUnit Test Patterns*. Addison-Wesley, 2007. (Fresh Fixture)
- JUNIT TEAM. *JUnit 5 User Guide*. https://junit.org/junit5/docs/current/user-guide/
- ORACLE. *Java API for XML Processing (JAXP)*. Documentação do Java SE 21.
- AGÊNCIA NACIONAL DE VIGILÂNCIA SANITÁRIA. *RDC nº 658/2022* (Boas Práticas de Fabricação de Medicamentos).
