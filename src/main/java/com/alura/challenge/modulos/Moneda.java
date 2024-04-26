package com.alura.challenge.modulos;


public class Moneda {
    private String registro;
    private final String resultado;
    private final float valor;
    private final String monedaBase;
    private final float tasaConversion;
    private final String monedaConvertida;
    private float valorConvertido;

    public Moneda(ExchangeRateApi exchangeRateApi, float valor) {
        this.monedaBase = exchangeRateApi.base_code();
        this.monedaConvertida = exchangeRateApi.target_code();
        this.resultado = exchangeRateApi.result();
        this.tasaConversion = exchangeRateApi.conversion_rate();
        this.valor = valor;
        this.valorConvertido = 0;
        this.registro = "";
    }


    public String getMonedaBase() {
        return monedaBase;
    }


    public String getMonedaConvertida() {
        return monedaConvertida;
    }


    public float getValor() {
        return valor;
    }


    public float getTasaConversion() {
        return tasaConversion;
    }


    public void setValorConvertido(float valorConvertido) {
        this.valorConvertido = valorConvertido;
    }


    public void setRegistro(String registro) {
        this.registro = registro;
    }


    @Override
    public String toString() {
        return """
                [%s] %.2f [%s] ---> %.2f [%s]
                """.formatted(registro, valor, monedaBase, valorConvertido, monedaConvertida);
    }
}
