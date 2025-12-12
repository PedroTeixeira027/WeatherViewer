# WeatherViewer 🌦️

Trabalho Prático da disciplina de Programação III - Consumo de Web Service de Previsão do Tempo.

## 👨‍🎓 Identificação do Aluno

* **Nome do aluno:** Pedro Henrique Teixeira da Silva
* **Curso:** Sistemas de Informação
* **Período:** 6º Período
* **Disciplina:** Programação III - 2025/02
* **Instituição:** Universidade do Estado de Minas Gerais - Unidade Passos

## 📝 Descrição da Aplicação

Esta aplicação Android (Java) foi desenvolvida para consultar a previsão do tempo utilizando uma API RESTful específica. O projeto baseia-se na arquitetura apresentada no Capítulo 7 do livro didático ("WeatherViewer App"), com as devidas adaptações para o endpoint fornecido pelo professor.

**Principais funcionalidades:**
* Entrada de dados do usuário (Cidade, Estado, País).
* Conexão HTTP realizada em thread separada (AsyncTask).
* Processamento de resposta JSON contendo array de dias.
* Exibição de uma lista personalizada com: Data, Descrição do clima, Temperaturas (Min/Max em °C), Umidade e Ícone (Emoji).
* Tratamento de erros de conexão e validação de URL.

## 🚀 Instruções para Execução

1.  **Clonar o Repositório:** Faça o download ou clone este projeto para sua máquina local.
2.  **Abrir no Android Studio:** Abra o projeto utilizando o Android Studio (Recomendado versão Ladybug ou superior).
3.  **Sincronizar:** Aguarde o Gradle baixar as dependências e indexar o projeto.
4.  **⚠️ Configuração da Chave de API:**
    * **Nota de Segurança:** Por boas práticas de segurança, a chave da API (`APPID`) **não foi incluída** neste repositório público.
    * **Como configurar:** Abra o arquivo `app/src/main/res/values/strings.xml` e adicione a chave correta na tag `api_key`.
5.  **Executar:** Inicie a aplicação em um Emulador (Android 7.0+) ou dispositivo físico.
6.  **Uso:**
    * No campo de texto, digite a cidade no formato exato: `Cidade, Estado, País` (Exemplo: `Passos, MG, BR`).
    * Clique no botão flutuante (Lupa) para carregar os dados.

## 🔗 Exemplo da URL Utilizada na Requisição

A aplicação monta a URL dinamicamente, garantindo a codificação correta dos espaços e caracteres especiais. Abaixo está o exemplo da estrutura da URL utilizada (com a chave ocultada):

```text
[http://agent-weathermap-env-env.eba-6pzgqekp.us-east-2.elasticbeanstalk.com/api/weather?city=Passos%2C%20MG%2C%20BR&days=7&APPID=](http://agent-weathermap-env-env.eba-6pzgqekp.us-east-2.elasticbeanstalk.com/api/weather?city=Passos%2C%20MG%2C%20BR&days=7&APPID=)[CHAVE_REMOVIDA_POR_SEGURANCA]
