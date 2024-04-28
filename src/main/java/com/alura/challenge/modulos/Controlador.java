package com.alura.challenge.modulos;

import com.alura.challenge.api.conexionApi;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
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
        // Obtener la fecha y hora actual
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        // Formatear la fecha y hora actual
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHoraFormateada = fechaHoraActual.format(formatter);

        moneda.setRegistro(fechaHoraFormateada);
    }

    
    public String crearJson() throws IOException {
        if (historial.isEmpty())
            return "Debes convertir alguna moneda.";


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
                \n%.2f [%s] ---> %.5f [%s]
                """.formatted(moneda.getValor(), moneda.getMonedaBase(), valor, moneda.getMonedaConvertida());
    }


    public void cerrarScanner() {
        sc.close();
    }


    public String historial() {
        if (historial.isEmpty())
            return "\nNo hay historial de monedas.";

        System.out.println("\n             HISTORIAL DE CONVERSIONES\n");

        StringBuilder stringBuilder = new StringBuilder();

        for (Moneda moneda : historial)
            stringBuilder.append(moneda.toString());

        return stringBuilder.toString();
    }


}
