package br.com.lince.hackathon.standard;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe contendo uma implementação base de requisição para a API da OpenIA.
 */
public class OpenAI {
    private static final Logger logger = Logger.getLogger(OpenAI.class.getName());

    /**
     * Entidade seguindo modelo de requisição da API da OpenIA.
     * <br>
     * Documentação: <a href="https://platform.openai.com/docs/api-reference/making-requests">Making requests</a>
     */
    public static class Envelope {
        public Envelope() {
        }

        public Envelope(String model, List<Message> messages, double temperature) {
            this.model = model;
            this.messages = messages;
            this.temperature = temperature;
        }

        private String model;
        private List<Message> messages;
        private double temperature;

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public List<Message> getMessages() {
            return messages;
        }

        public void setMessages(List<Message> messages) {
            this.messages = messages;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }
    }

    /**
     * Entidade seguindo modelo de requisição da API da OpenIA
     * <br>
     * Documentação: <a href="https://platform.openai.com/docs/api-reference/making-requests">Making requests</a>
     */
    public static class Message {
        public Message() {

        }

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        private String role;
        private String content;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    /**
     * Entidade seguindo modelo de requisição da API da OpenIA
     * <br>
     * Documentação: <a href="https://platform.openai.com/docs/api-reference/making-requests">Making requests</a>
     */
    public static class Choice {
        public Choice() {
        }

        private long index;
        private Message message;
        private String logprobs;
        private String finishReason;

        public long getIndex() {
            return index;
        }

        public void setIndex(long index) {
            this.index = index;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

        public String getLogprobs() {
            return logprobs;
        }

        public void setLogprobs(String logprobs) {
            this.logprobs = logprobs;
        }

        public String getFinishReason() {
            return finishReason;
        }

        public void setFinishReason(String finishReason) {
            this.finishReason = finishReason;
        }
    }

    /**
     * Entidade seguindo modelo de requisição da API da OpenIA
     * <br>
     * Documentação: <a href="https://platform.openai.com/docs/api-reference/making-requests">Making requests</a>
     */
    public static class Usage {
        public Usage() {
        }

        private long promptTokens;
        private long completionTokens;
        private long totalTokens;

        public long getPromptTokens() {
            return promptTokens;
        }

        public void setPromptTokens(long promptTokens) {
            this.promptTokens = promptTokens;
        }

        public long getCompletionTokens() {
            return completionTokens;
        }

        public void setCompletionTokens(long completionTokens) {
            this.completionTokens = completionTokens;
        }

        public long getTotalTokens() {
            return totalTokens;
        }

        public void setTotalTokens(long totalTokens) {
            this.totalTokens = totalTokens;
        }
    }

    /**
     * Entidade seguindo modelo de requisição da API da OpenIA.
     * <br>
     * Documentação: <a href="https://platform.openai.com/docs/api-reference/making-requests">Making requests</a>
     */
    public static class Response {
        public Response() {
        }

        private String id;
        private String object;
        private long created;
        private String model;
        private List<Choice> choices;
        private Usage usage;
        private String systemFingerprint;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public long getCreated() {
            return created;
        }

        public void setCreated(long created) {
            this.created = created;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public List<Choice> getChoices() {
            return choices;
        }

        public void setChoices(List<Choice> choices) {
            this.choices = choices;
        }

        public Usage getUsage() {
            return usage;
        }

        public void setUsage(Usage usage) {
            this.usage = usage;
        }

        public String getSystemFingerprint() {
            return systemFingerprint;
        }

        public void setSystemFingerprint(String systemFingerprint) {
            this.systemFingerprint = systemFingerprint;
        }
    }

    /**
     * Envia a <code>pergunta</code> para a API da OpenAI, e retorna a primeira respostas recebida.
     *
     * @param pergunta pergunta a ser feita para a OpenIA
     * @return primeira resposta recebida
     */
    public static Optional<String> askQuestion(final String pergunta) {
        try {
            final var openAIKey = System.getenv("OPEN_AI_KEY");
            final var gerarFoo = new Message("user", pergunta);
            final var envelope = new Envelope("gpt-3.5-turbo", new ArrayList<>(List.of(gerarFoo)), 0.7);
            final var gson = new Gson();
            final var requestBody = HttpRequest.BodyPublishers.ofString(gson.toJson(envelope));
            final var request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                    .headers(
                            "Content-Type", "application/json",
                            "Authorization", "Bearer " + openAIKey
                    )
                    .POST(requestBody)
                    .build();

            final var response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            final var openaiResponse = gson.fromJson(response.body(), Response.class);
            if (!openaiResponse.choices.isEmpty()) {
                return Optional.of(openaiResponse.choices.get(0).message.content);
            }
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "Erro ao fazer requisição a OpenAI", e);
        }
        return Optional.empty();
    }

    public static void main(String[] args) {
        final var pergunta = "Gere um lorem ipsum com dois paragrafos, e utilize a palavra foo no lugar de ipsum";
        final var resposta = askQuestion(pergunta);

        resposta.ifPresentOrElse(
                (response) -> logger.info("Requisição a OpenAI\n  Pergunta: < " + pergunta + " >\n  Resposta: < " + response + " >"),
                () -> logger.warning("Nenhuma resposta foi recebida :(")
        );

    }
}
