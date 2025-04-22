public class ConvertaSeuDindin {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.exibirMenu();

        ConsultaAPI consulta = new ConsultaAPI();
        double cotacao = consulta.obterCotacao("USD", "BRL");

        if (cotacao != -1.0) {
            System.out.println("Cotação USD para BRL: " + cotacao);
        }
    }
}