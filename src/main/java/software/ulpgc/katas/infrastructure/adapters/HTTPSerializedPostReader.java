package software.ulpgc.katas.infrastructure.adapters;

import software.ulpgc.katas.architecture.ports.SerializedPostReader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPSerializedPostReader implements SerializedPostReader {

    private final URI uri;

    public HTTPSerializedPostReader(String uri) {
        this.uri = URI.create(uri);
    }

    @Override
    public String readPosts() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        return ReadPosts(httpClient);
    }

    private String ReadPosts(HttpClient client) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(this.uri)
                .header("Accept", "application/json")
                .GET()
                .build();

        return ReadPosts(client, request);
    }

    private String ReadPosts(HttpClient client, HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }
}
