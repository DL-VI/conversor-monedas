package com.alura.challenge.modulos;

import com.alura.challenge.api.conexionApi;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Controlador {
    private final Scanner sc;
    private Moneda moneda;
    private final conexionApi api;
    private final List<Moneda> historial;


    public Controlador() {
        this.sc = new Scanner(System.in);
        this.historial = new ArrayList<>();
        this.api = new conexionApi();
    }


    public String todasLasMonedas() throws IOException, InterruptedException {
        return api.monedasSoportadas();
    }


    public String tasaConversion(String monedaBase) throws IOException {
        return api.tarifaConversiones(monedaBase);
    }


    public String nuevaMoneda(String monedaBase, float valor, String monedaConvertir) throws IOException, InterruptedException {
        ExchangeRateApi rateApi = api.cambiarMonedas(monedaBase, monedaConvertir);

        if (rateApi.result().equals("error"))
            return "\nEl codigo de la moneda no esta soportada.\n";

        moneda = new Moneda(rateApi, valor);
        registro();
        historial.add(moneda);

        return operacionDeConversion();
    }


    public void registro() {
        Clock clock = Clock.system(ZoneId.of("America/Bogota"));
        Instant instant = clock.instant();
        ZonedDateTime zonedDateTime = instant.atZone(clock.getZone());
        String time = zonedDateTime.toString();

        moneda.setRegistro(time);
    }

    
    public String crearJson() throws IOException {
        if (historial.isEmpty())
            return "Error, debes convertir alguna moneda.";


        Gson gson = new GsonBuilder().
                setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();


        FileWriter escribir = new FileWriter("exchangeHistory.json");
        escribir.write(gson.toJson(historial));
        escribir.close();

        return "¡Listo! revisa la carpeta del proyecto.";
    }


    private String operacionDeConversion() {
        String tasaConversion = String.valueOf(moneda.getTasaConversion());
        float valor = Float.parseFloat(tasaConversion) * moneda.getValor();
        moneda.setValorConvertido(valor);

        return """
                \n%.2f [%s] es igual a %.5f [%s]
                """.formatted(moneda.getValor(), moneda.getMonedaBase(), valor, moneda.getMonedaConvertida());
    }


    public void cerrarScanner() {
        sc.close();
    }

}
