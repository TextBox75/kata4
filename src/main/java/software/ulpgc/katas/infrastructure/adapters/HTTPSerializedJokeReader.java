package software.ulpgc.katas.infrastructure.adapters;

import software.ulpgc.katas.architecture.ports.SerializedJokeReader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPSerializedJokeReader implements SerializedJokeReader {
    @Override
    public String ReadJokes() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        return ReadJokes(httpClient);
    }

    private String ReadJokes(HttpClient client) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://official-joke-api.appspot.com/jokes/random/25"))
                .header("Accept", "application/json")
                .GET()
                .build();

        return ReadJokes(client, request);
    }

    private String ReadJokes(HttpClient client, HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }
}
