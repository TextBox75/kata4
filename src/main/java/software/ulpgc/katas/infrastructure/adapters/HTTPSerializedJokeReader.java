package software.ulpgc.katas.infrastructure.adapters;

import software.ulpgc.katas.architecture.ports.SerializedJokeReader;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;

public class HTTPSerializedJokeReader implements SerializedJokeReader {

    private final URI uri;

    public HTTPSerializedJokeReader(String uri) {
        this.uri = URI.create(uri);
    }

    @Override
    public String readJokes() throws IOException {
        HttpClient httpClient = HttpClient.newHttpClient();
        return readJokes(httpClient);
    }

    private String readJokes(HttpClient client) throws IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(this.uri)
                .header("Accept", "application/json")
                .GET()
                .build();

        return readJokes(client, request);
    }

    private String readJokes(HttpClient client, HttpRequest request) throws IOException {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return validateResponse(response);
        } catch (IOException | InterruptedException e) {
            throw toReadableException(e);
        }
    }

    private String validateResponse(HttpResponse<String> response) throws IOException {
        if (response.statusCode() == 200) return response.body();
        throw new IOException("Error HTTP " + response.statusCode() + ": No se pudo obtener el recurso.");
    }

    private IOException toReadableException(Exception e) {
        return switch (e) {
            case InterruptedException ex -> new IOException("La petición HTTP fue interrumpida.");
            case HttpTimeoutException ex -> new IOException("Tiempo de espera agotado.");
            case UnknownHostException ex -> new IOException("Servicio no disponible: " + uri.getHost());
            case ConnectException ex -> new IOException("Comprueba to conexión a internet o URI.");
            case IOException io -> io;
            default -> new IOException("Error inesperado.", e);
        };
    }
}
