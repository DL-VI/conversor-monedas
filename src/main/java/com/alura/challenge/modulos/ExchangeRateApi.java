package com.alura.challenge.modulos;

public record ExchangeRateApi(String result, String base_code,
                              String target_code, float conversion_rate) {
}
