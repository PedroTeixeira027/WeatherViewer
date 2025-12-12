package com.pedro.weatherviewer;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Representa os dados meteorológicos de um único dia da previsão [cite: 369]
public class Weather {

    public final String dayOfWeek;
    public final String minTemp;
    public final String maxTemp;
    public final String humidity;
    public final String description;
    public final String icon; // Armazena o Emoji retornado pela API [cite: 968]

    public Weather(String dateString, double minTemp, double maxTemp, double humidity, String description, String icon) {

        // Converte a data recebida para o nome do dia da semana
        this.dayOfWeek = convertDateToDay(dateString);

        // Configura formatação numérica (arredondamento) para temperaturas
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);

        // Aplica formatação e adiciona símbolo Celsius (API retorna em C) [cite: 995]
        this.minTemp = numberFormat.format(minTemp) + "\u00B0C";
        this.maxTemp = numberFormat.format(maxTemp) + "\u00B0C";

        // Formata a umidade para porcentagem (ex: 0.75 vira 75%)
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        this.humidity = percentFormat.format(humidity);

        this.description = description;
        this.icon = icon;
    }

    // Método auxiliar para formatar a data
    private String convertDateToDay(String dateString) {
        try {
            // Parser para o formato que vem da API (yyyy-MM-dd)
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = inputFormat.parse(dateString);

            // Formata para o nome do dia completo em Português
            SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE", new Locale("pt", "BR"));
            String dia = outputFormat.format(date);

            // Retorna com a primeira letra maiúscula para melhor estética
            return dia.substring(0, 1).toUpperCase() + dia.substring(1);
        } catch (Exception e) {
            e.printStackTrace();
            return dateString; // Retorna string original em caso de falha no parse
        }
    }
}