package software.ulpgc.katas.infrastructure.adapters;

import software.ulpgc.katas.architecture.ports.SerializedProductReader;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;

public class HTTPSerializedProductReader implements SerializedProductReader {

    private final URI uri;

    public HTTPSerializedProductReader(String uri) {
        this.uri = URI.create(uri);
    }

    @Override
    public String readProducts() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        return readProducts(httpClient);
    }

    private String readProducts(HttpClient client) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(this.uri)
                .header("Accept", "application/json")
                .GET()
                .build();

        return readProducts(client, request);
    }

    private String readProducts(HttpClient client, HttpRequest request) throws IOException, InterruptedException {
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
