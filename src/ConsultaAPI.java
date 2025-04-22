import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.*;

class ConsultaAPI {
    private final HttpClient client = HttpClient.newHttpClient();
    private static final String API_KEY = "44cc5c0dea81e6055f91aba6"; // Substitua pela sua chave real
    private final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .create();


    public double obterCotacao(String moedaBase, String moedaAlvo) {
        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + moedaBase;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();


        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Aqui usamos o Gson para parsear o JSON
            JsonObject json = gson.fromJson(response.body(), JsonObject.class);
            JsonObject conversionRates = json.getAsJsonObject("conversion_rates");

            // Verifica se a moeda alvo existe
            if (conversionRates.has(moedaAlvo)) {
                return conversionRates.get(moedaAlvo).getAsDouble();
            } else {
                System.out.println("Moeda alvo não encontrada na resposta da API.");
                return -1.0;  // Ou lance uma exceção mais específica
            }


        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao consultar a API: " + e.getMessage());
            return -1.0;
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println("Erro ao parsear o JSON: " + e.getMessage());
            return -1.0;
        }
    }
}