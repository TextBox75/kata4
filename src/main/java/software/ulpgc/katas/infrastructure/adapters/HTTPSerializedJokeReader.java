package software.ulpgc.katas.infrastructure.adapters;

import software.ulpgc.katas.architecture.ports.SerializedJokeReader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPSerializedJokeReader implements SerializedJokeReader {

    private final URI uri;

    public HTTPSerializedJokeReader(String uri) {
        this.uri = URI.create(uri);
    }

    @Override
    public String readJokes() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        return readJokes(httpClient);
    }

    private String readJokes(HttpClient client) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(this.uri)
                .header("Accept", "application/json")
                .GET()
                .build();

        return readJokes(client, request);
    }

    private String readJokes(HttpClient client, HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }
}
