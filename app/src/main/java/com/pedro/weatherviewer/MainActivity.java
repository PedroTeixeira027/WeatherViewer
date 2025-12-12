package com.pedro.weatherviewer;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Lista de dados e Adaptador para gerenciar a exibição na ListView
    private List<Weather> weatherList = new ArrayList<>();
    private WeatherArrayAdapter weatherArrayAdapter;
    private ListView weatherListView;

    // Armazena mensagens de erro para feedback detalhado ao usuário
    private String lastError = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuração inicial da lista e vínculo com o adaptador
        weatherListView = findViewById(R.id.weatherListView);
        weatherArrayAdapter = new WeatherArrayAdapter(this, weatherList);
        weatherListView.setAdapter(weatherArrayAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText locationEditText = findViewById(R.id.locationEditText);
                String city = locationEditText.getText().toString();

                // Validação simples de entrada
                if (city.isEmpty()) {
                    Snackbar.make(view, "Por favor, digite uma cidade.", Snackbar.LENGTH_LONG).show();
                    return;
                }

                lastError = ""; // Limpa erros de execuções anteriores

                URL url = createURL(city);
                if (url != null) {
                    dismissKeyboard(locationEditText);
                    // Inicia a tarefa de rede em segundo plano (AsyncTask)
                    GetWeatherTask getLocalWeatherTask = new GetWeatherTask();
                    getLocalWeatherTask.execute(url);
                } else {
                    Snackbar.make(findViewById(R.id.coordinatorLayout), "URL Inválida ao criar.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    // Oculta o teclado virtual para melhorar a UX após a busca
    private void dismissKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // Monta a URL da API garantindo a codificação correta dos parâmetros
    private URL createURL(String city) {
        String apiKey = getString(R.string.api_key).trim();
        String baseUrl = getString(R.string.base_url).trim();

        try {
            // URLEncoder usa '+' para espaços, mas APIs modernas preferem '%20'.
            // Realizamos a substituição manual para garantir compatibilidade.
            String cityEncoded = java.net.URLEncoder.encode(city, "UTF-8").replace("+", "%20");

            String urlString = baseUrl + "?city=" + cityEncoded +
                    "&days=7" +
                    "&APPID=" + apiKey;

            return new URL(urlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tarefa Assíncrona para realizar a conexão de rede fora da Thread Principal (UI Thread)
    private class GetWeatherTask extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, R.string.loading, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected JSONObject doInBackground(URL... params) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) params[0].openConnection();

                // Configuração de Headers HTTP para evitar rejeição pelo servidor (Erro 401/403)
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "*/*");
                connection.setRequestProperty("User-Agent", "WeatherViewerApp/1.0");

                // Timeouts para evitar que o app trave esperando resposta
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);

                int response = connection.getResponseCode();
                Log.d("DEBUG_RESPONSE", "Código de Resposta: " + response);

                if (response == HttpURLConnection.HTTP_OK) {
                    // Leitura da resposta com sucesso (200)
                    StringBuilder builder = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                    }
                    return new JSONObject(builder.toString());
                } else {
                    // Tratamento de erros do servidor (lê o ErrorStream para debug)
                    try (BufferedReader errorReader = new BufferedReader(
                            new InputStreamReader(connection.getErrorStream()))) {
                        StringBuilder errorBuilder = new StringBuilder();
                        String errorLine;
                        while ((errorLine = errorReader.readLine()) != null) {
                            errorBuilder.append(errorLine);
                        }
                        lastError = "Erro " + response + ": " + errorBuilder.toString();
                        Log.e("DEBUG_ERRO_SERVER", lastError);
                    } catch (Exception ex) {
                        lastError = "Erro Servidor: Código " + response;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                lastError = "Erro Técnico: " + e.getMessage();
            } finally {
                if (connection != null) connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject weather) {
            if (weather != null) {
                // Sucesso: Processa dados e atualiza a interface
                convertJSONtoArrayList(weather);
                weatherArrayAdapter.notifyDataSetChanged();
                weatherListView.smoothScrollToPosition(0);
                Toast.makeText(MainActivity.this, "Sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                // Falha: Exibe o erro capturado para o usuário
                String msg = lastError.isEmpty() ? "Erro desconhecido" : lastError;
                Snackbar.make(findViewById(R.id.coordinatorLayout), msg, Snackbar.LENGTH_INDEFINITE)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) { }
                        }).show();
            }
        }
    }

    // Converte o JSON bruto em objetos da classe Weather
    private void convertJSONtoArrayList(JSONObject forecast) {
        weatherList.clear();
        try {
            // A API retorna um array identificado por "days"
            JSONArray list = forecast.getJSONArray("days");
            for (int i = 0; i < list.length(); i++) {
                JSONObject day = list.getJSONObject(i);
                weatherList.add(new Weather(
                        day.getString("date"),
                        day.getDouble("minTempC"),
                        day.getDouble("maxTempC"),
                        day.getDouble("humidity"),
                        day.getString("description"),
                        day.getString("icon")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            lastError = "Erro ao processar JSON: " + e.getMessage();
        }
    }
}