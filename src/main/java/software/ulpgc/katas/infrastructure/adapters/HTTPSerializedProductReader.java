package software.ulpgc.katas.infrastructure.adapters;

import software.ulpgc.katas.architecture.ports.SerializedProductReader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPSerializedProductReader implements SerializedProductReader {

    private final URI uri;

    public HTTPSerializedProductReader(String uri) {
        this.uri = URI.create(uri);
    }

    @Override
    public String readProducts() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        return ReadProducts(httpClient);
    }

    private String ReadProducts(HttpClient client) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(this.uri)
                .header("Accept", "application/json")
                .GET()
                .build();

        return ReadProducts(client, request);
    }

    private String ReadProducts(HttpClient client, HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }
}
