package com.pedro.weatherviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

// Adaptador personalizado para vincular a lista de objetos Weather ao ListView
public class WeatherArrayAdapter extends ArrayAdapter<Weather> {

    // Padrão ViewHolder: armazena referências das views para evitar chamadas repetitivas ao findViewById durante a rolagem
    private static class ViewHolder {
        TextView conditionTextView;
        TextView dayTextView;
        TextView lowTextView;
        TextView hiTextView;
        TextView humidityTextView;
    }

    public WeatherArrayAdapter(Context context, List<Weather> forecast) {
        // Passamos -1 como ID de recurso porque vamos inflar nosso layout personalizado manualmente no getView
        super(context, -1, forecast);
    }

    // Método responsável por criar ou atualizar as visualizações para cada item da lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Recupera o objeto de dados para a posição atual
        Weather day = getItem(position);

        ViewHolder viewHolder;

        // Lógica de reciclagem: verifica se existe uma view reutilizável antes de criar uma nova
        if (convertView == null) {
            // Se não houver view para reutilizar, infla o layout list_item.xml
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);

            // Mapeia os componentes da interface ao ViewHolder
            viewHolder.conditionTextView = convertView.findViewById(R.id.conditionTextView);
            viewHolder.dayTextView = convertView.findViewById(R.id.dayTextView);
            viewHolder.lowTextView = convertView.findViewById(R.id.lowTextView);
            viewHolder.hiTextView = convertView.findViewById(R.id.hiTextView);
            viewHolder.humidityTextView = convertView.findViewById(R.id.humidityTextView);

            // Armazena o ViewHolder na tag da view para recuperação futura
            convertView.setTag(viewHolder);
        } else {
            // A view já existe, então apenas recuperamos o cache do ViewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Popula os dados na interface, caso o objeto seja válido
        if (day != null) {
            // A API retorna um Emoji (texto), então setamos diretamente no TextView
            viewHolder.conditionTextView.setText(day.icon);

            // Formata e exibe os textos descritivos
            viewHolder.dayTextView.setText(String.format("%s: %s", day.dayOfWeek, day.description));
            viewHolder.lowTextView.setText(String.format("Min: %s", day.minTemp));
            viewHolder.hiTextView.setText(String.format("Max: %s", day.maxTemp));
            viewHolder.humidityTextView.setText(String.format("Umidade: %s", day.humidity));
        }

        return convertView;
    }
}