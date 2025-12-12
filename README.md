# WeatherViewer - Android Forecast App 🌦️

Aplicação nativa Android para consulta de previsão do tempo, desenvolvida com foco em consumo de API REST, arquitetura limpa e boas práticas de UI/UX. Baseada nos conceitos do *WeatherViewer App* (Deitel).

## 📱 Sobre o Projeto

O **WeatherViewer** permite que o usuário consulte a previsão do tempo para os próximos 7 dias em qualquer cidade. O aplicativo resolve desafios comuns de desenvolvimento mobile, como:
* Execução de tarefas de rede em background (assincronismo).
* Tratamento de respostas JSON complexas.
* Gerenciamento de erros de conexão e entrada de dados.
* Interface responsiva com feedback visual ao usuário.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java
* **Ambiente:** Android Studio
* **Componentes de UI:**
    * `CoordinatorLayout` & `LinearLayout`
    * `RecyclerView` / `ListView` com View Holder Pattern
    * `FloatingActionButton` (FAB)
    * `Snackbar` para mensagens de feedback
    * `TextInputLayout` (Material Design)
* **Rede & Dados:**
    * `HttpURLConnection` (Consumo REST nativo)
    * `AsyncTask` (Gerenciamento de Threads)
    * `org.json` (Parsing de dados)

## 🚀 Funcionalidades

* **Busca Flexível:** Aceita input no formato "Cidade, Estado, País".
* **Tratamento de Dados:** Codificação automática de URL (UTF-8) e sanitização de inputs.
* **Feedback Visual:** Indicadores de carregamento e mensagens de erro amigáveis (Toasts e Snackbars).
* **Lista Otimizada:** Exibição fluida dos dias da semana, temperaturas (Min/Max em °C), umidade e ícone (Emoji) fornecido pela API.

## ⚙️ Como Executar

1.  **Clonar o repositório:**
    ```bash
    git clone [https://github.com/PedroTeixeira027/WeatherViewer.git]
    ```
2.  **Abrir no Android Studio:** Selecione a pasta do projeto.
3.  **Sincronizar:** Aguarde o Gradle baixar as dependências.
4.  **Executar:** Rode em um emulador (API 24+) ou dispositivo físico.

**Nota sobre a API:**
Este projeto consome uma API educacional hospedada na AWS. A chave de acesso está configurada internamente para fins de demonstração acadêmica.

---
*Desenvolvido como projeto prático de Arquitetura de Software Móvel.*
