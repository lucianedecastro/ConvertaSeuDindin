import com.google.gson.*;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

class ConsultaAPI {
    private final HttpClient client = HttpClient.newHttpClient();
    private static final String API_KEY = "44cc5c0dea81e6055f91aba6";
    private final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .create();

    public double obterCotacao(String moedaBase, String moedaAlvo) {
        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + moedaBase;
        String jsonFileName = "cotacao.json"; // Nome do arquivo JSON

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();


            // --- Salvando a resposta em um arquivo JSON ---
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(jsonFileName), StandardCharsets.UTF_8))) {
                writer.write(responseBody);
            } catch (IOException e) {
                System.err.println("Erro ao salvar o JSON em arquivo: " + e.getMessage());

                return -1.0;
            }



            // --- Processando o JSON ---
            JsonObject json = gson.fromJson(responseBody, JsonObject.class);
            JsonObject conversionRates = json.getAsJsonObject("conversion_rates");

            if (conversionRates.has(moedaAlvo)) {
                return conversionRates.get(moedaAlvo).getAsDouble();
            } else {
                System.out.println("Moeda alvo não encontrada na resposta da API.");
                return -1.0;
            }


        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao consultar a API: " + e.getMessage());
            return -1.0;
        } catch (JsonSyntaxException e) {
            System.err.println("Erro ao parsear o JSON: " + e.getMessage());
            return -1.0;
        }
    }
}