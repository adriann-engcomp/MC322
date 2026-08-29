import java.util.Scanner;


public class Main {
    // inicializando remedios
    static Produto[] remedios = new Produto[3];
    static MateriaPrima[] materiaPrima = new MateriaPrima[0];
 
    public static void main(String[] args) {
        MateriaPrima materiaPrima = new MateriaPrima(1, "Composto Ativo XR-7", 100, "g", 20);

        remedios[0] = new Produto(1, "Paracetamol", 5);
        remedios[1] = new Produto(2, "Ibuprofeno", 3);
        remedios[2] = new Produto(3, "Dipirona", 7);

 
        System.out.println("""
        ========================================
        Fabrica de remédios
        ========================================
 
        Bem-vindos à nossa fábrica de remédios! Aqui transformamos e cuidamos de vidas
 
        Desenvolvido por:
        ========================================
        """);
 
        System.out.println("========================================");
        System.out.println("PLANTA INDUSTRIAL");
        System.out.println("========================================");
        System.out.println("Matéria-Prima: MP00" + materiaPrima.getId() + " - " + materiaPrima.getNome());
        System.out.println("Quantidade: " + materiaPrima.getQuantidade() + " " + materiaPrima.getUnidade());
        System.out.println("Unidade: " + materiaPrima.getUnidade());
        // implementar remédios disponíveis system.out.println("Remédios disponíveis:");
        System.out.println("========================================");
 
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;
 
        System.out.println("\n========================================");
        System.out.println("MENU PRINCIPAL");
        System.out.println("========================================");
        System.out.println("1 - Iniciar produção");
        System.out.println("2 - Consultar estoque");
        System.out.println("3 - Sair");
        System.out.print("Escolha uma opção: ");

        opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                System.out.println("Você escolheu: Iniciar produção.");
                break;
            case 2:
                System.out.println("Você escolheu: Consultar estoque.");
                break;
            case 3:
                System.out.println("Saindo do sistema");
                break;
            default:
                System.out.println("Opção inválida! Escolha entre 1 e 3.");
        }
    }
}

class MateriaPrima {
    private int id;
    private String nome;
    private int quantidade;
    private String unidade;
    private int quantidadeMinima;

    public MateriaPrima(int id, String nome, int quantidade, String unidade, int quantidadeMinima) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.quantidadeMinima = quantidadeMinima;
    }

    public void consumir(int quantidadeDemandada) {
    }

    public void adicionarEstoque(int quantidadeAdicionada) {
    }

    public boolean verificarDisponibilidade(int demanda) {
        return false;
    }

    public int getId() {
        return 0;
    }

    public String getNome() {
        return null;
    }

    public int getQuantidade() {
        return 0;
    }

    public String getUnidade() {
        return null;
    }
}

class Produto {
    private int id;
    private String nome;
    private boolean status;
    private int quantidadeMateriaPrimaNecessaria;

    public Produto(int id, String nome, int quantidadeMateriaPrimaNecessaria) {
        this.id = id;
        this.nome = nome;
        this.quantidadeMateriaPrimaNecessaria = quantidadeMateriaPrimaNecessaria;
        this.status = false;
    }

    public void processar() {
        // criar lógica
    }

    public void definirDemandaMateriaPrima(int quantidade) {
        this.quantidadeMateriaPrimaNecessaria = quantidade;
    }

    public int getDemandaMateriaPrima() {
        return this.quantidadeMateriaPrimaNecessaria = quantidadeMateriaPrimaNecessaria;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public boolean getStatus() {
        return this.status;
    }
}

class Maquina {
    private int id;
    private boolean ligada;
    private String nome;
    private int capacidadeMaxima;

    public void ligar() {
    }

    public void desligar() {
    }

    public void processar(MateriaPrima materiaPrima, int demanda) {
    }

    public String getNome() {
        return null;
    }

    public boolean estaLigada() {
        return false;
    }
}

class Esteira {
    private Object item;
    private boolean emMovimento;
    private int capacidadeMaxima;

    public void ligar() {
    }

    public void desligar() {
    }

    public void adicionarItem(Object item) {
    }

    public Object removerItem() {
        return null;
    }

    public boolean verificarCapacidade(Object item) {
        return false;
    }
}

class EstacaoInspecao {
    private boolean ativa;
    private int produtosInspecionados;

    public void ativar() {
    }

    public void desativar() {
    }

    public void inspecionar(Produto produto) {
    }

    public int getTotalInspecionados() {
        return 0;
    }
}