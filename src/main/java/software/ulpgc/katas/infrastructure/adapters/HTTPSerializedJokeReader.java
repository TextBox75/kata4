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
        try {
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() >= 400) {
                throw new IOException("Error HTTP " + response.statusCode() + ": El servidor rechazó la petición o el recurso no existe.");
            } else if (response.statusCode() != 200) {
                throw new IOException("Respuesta inesperada del servidor (Código HTTP " + response.statusCode() + ").");
            }

            return response.body();

        } catch (UnknownHostException | ConnectException e) {
            throw new IOException("Servicio no disponible: No se pudo conectar con el servidor (" + uri.getHost() + "). Comprueba tu conexión o la URL.");
        } catch (HttpTimeoutException e) {
            throw new IOException("Tiempo de espera agotado: El servidor tardó demasiado en responder.");
        } catch (InterruptedException e) {
            throw new IOException("La petición HTTP fue interrumpida inesperadamente.");
        }
    }
}
