import java.util.Scanner;

class Menu {
    private final ConversorMoeda conversor;
    private final Scanner scanner;

    public Menu() {
        this.conversor = new ConversorMoeda();
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n=== ConvertaSeuDindin ===");
            System.out.println("1. Dólar -> Real");
            System.out.println("2. Euro -> Real");
            System.out.println("3. Real -> Dólar");
            System.out.println("4. Real -> Euro");
            System.out.println("5. Iene -> Real");
            System.out.println("6. Real -> Iene");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            String de = "", para = "";
            switch (opcao) {
                case 1 -> { de = "USD"; para = "BRL"; }
                case 2 -> { de = "EUR"; para = "BRL"; }
                case 3 -> { de = "BRL"; para = "USD"; }
                case 4 -> { de = "BRL"; para = "EUR"; }
                case 5 -> { de = "JPY"; para = "BRL"; }
                case 6 -> { de = "BRL"; para = "JPY"; }
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }

            if (opcao >= 1 && opcao <= 6) {
                System.out.print("Digite o valor a ser convertido: ");
                double valor = scanner.nextDouble();
                conversor.converter(de, para, valor);
            }

        } while (opcao != 0);
    }
}