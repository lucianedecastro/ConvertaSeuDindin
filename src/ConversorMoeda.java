class ConversorMoeda {
    private final ConsultaAPI consulta = new ConsultaAPI();

    public void converter(String de, String para, double valor) {
        double taxa = consulta.obterCotacao(de, para);
        if (taxa < 0) {
            System.out.println("Não foi possível obter a taxa de câmbio.");
            return;
        }
        double resultado = valor * taxa;
        System.out.printf("\uD83D\uDCB0 %.2f %s = %.2f %s\n", valor, de, resultado, para);
    }
}