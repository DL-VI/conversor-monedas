package com.alura.challenge.api;

import com.alura.challenge.modulos.ExchangeRateApi;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;


public class conexionApi {
    private static final String API_KEY = "d31b3e3c33e4e3a3ce87e62c";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    private static HttpClient client;
    private static HttpRequest request;
    private static HttpResponse<String> response;
    private final Gson gson = new Gson();


    public String monedasSoportadas() throws IOException, InterruptedException {
        String direccion = BASE_URL + API_KEY + "/codes";

        // crear un cliente HTTP y realizar la petición
        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder().uri(URI.create(direccion)).build();

        // obtiene la respuesta de la API
        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // convierte la cadena JSON en un JsonObject
        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray array = jsonObject.getAsJsonArray("supported_codes");

        return formatoSoportadas(array);
    }


    private String formatoSoportadas(JsonArray array) {
        int anchoCodigo = 0;
        int anchoNombre = 0;

        // calcula las anchuras máximas
        for (JsonElement jsonElemento : array) {
            JsonArray codigoNombreArray = jsonElemento.getAsJsonArray();
            String codigo = codigoNombreArray.get(0).getAsString();
            String moneda = codigoNombreArray.get(1).getAsString();

            anchoCodigo = Math.max(anchoCodigo, codigo.length());
            anchoNombre = Math.max(anchoNombre, moneda.length());
        }

        StringBuilder constructor = construirFormatoSoportado(array, anchoCodigo, anchoNombre);

        return constructor.toString();
    }


    private StringBuilder construirFormatoSoportado(JsonArray array, int anchoCodigo, int anchoNombre) {
        StringBuilder builder = new StringBuilder();
        int contador = 0;

        // crea la salida formateada
        for (JsonElement jsonElemento : array) {
            JsonArray codigoNombreArray = jsonElemento.getAsJsonArray();
            String codigo = codigoNombreArray.get(0).getAsString();
            String moneda = codigoNombreArray.get(1).getAsString();

            // añadir código formateado y nombre a la salida
            builder.append(String.format("%-" + (anchoCodigo + 1) + "s", codigo));
            builder.append(String.format("%-" + (anchoNombre + 4) + "s", "(" + moneda + ")"));
            contador++;

            if (contador % 4 == 0) builder.append("\n");
        }
        return builder;
    }


    public String tarifaConversiones(String monedaBase) throws IOException {

        try {
            // URL de configuración
            String url_str = "https://v6.exchangerate-api.com/v6/d31b3e3c33e4e3a3ce87e62c/latest/" + monedaBase;

            // Hacer una petición
            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            // Convertir a JSON
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonObj = root.getAsJsonObject();

            JsonElement elemento = jsonObj.get("conversion_rates");
            JsonObject jsonTarifas = elemento.getAsJsonObject();

            return formatoTarifas(jsonTarifas);
        } catch (FileNotFoundException e) {
            return "¡Error!";
        }
    }


    private String formatoTarifas(JsonObject jsonObject) {
        int anchoCodigo = 0;
        int anchoNombre = 0;

        // calcula las anchuras máximas
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String llave = entry.getKey();
            float valor = entry.getValue().getAsFloat();

            anchoCodigo = Math.max(anchoCodigo, llave.length());
            anchoNombre = Math.max(anchoNombre, String.valueOf(valor).length());
        }

        StringBuilder builder = construirFormato(jsonObject, anchoCodigo, anchoNombre);

        return builder.toString();
    }


    private StringBuilder construirFormato(JsonObject jsonObject, int anchoCodigo, int anchoNomre) {
        StringBuilder constructor = new StringBuilder();
        int contador = 0;

        // crea la salida formateada
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String llave = entry.getKey();
            float valor = entry.getValue().getAsFloat();

            // añadir código formateado y nombre a la salida
            constructor.append(String.format("%-" + (anchoCodigo + 1) + "s", llave));
            constructor.append(String.format("%-" + (anchoNomre + 8) + "s", "(" + valor + ")"));
            contador++;

            if (contador % 6 == 0)
                constructor.append("\n");
        }
        return constructor;
    }


    public ExchangeRateApi cambiarMonedas(String monedaBase, String monedaConvertir) throws IOException, InterruptedException {
        String direction = BASE_URL + API_KEY + "/pair/" + monedaBase.toUpperCase() + "/" + monedaConvertir.toUpperCase();

        // crear un cliente HTTP y realizar la petición
        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder().uri(URI.create(direction)).build();

        // obtiene la respuesta de la API
        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return gson.fromJson(response.body(), ExchangeRateApi.class);
    }

}
