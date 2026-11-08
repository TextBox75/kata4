package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import software.ulpgc.katas.infrastructure.adapters.HTTPSerializedJokeReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HTTPTest {

    private static final String VALID_BASE_URL = "https://v2.jokeapi.dev";

    @Test
    @DisplayName("Punto 1: Ejecuta el cliente con respuesta correcta")
    void shouldReturnNonEmptyPayloadWhenBaseUrlIsValid() throws Exception {
        HTTPSerializedJokeReader reader = new HTTPSerializedJokeReader(VALID_BASE_URL);

        String jsonResponse = reader.readJokes();

        assertNotNull(jsonResponse, "La respuesta no debería ser nula");
        assertFalse(jsonResponse.isBlank(), "La respuesta no debería estar vacía");
        assertTrue(jsonResponse.contains("\"error\": false") || jsonResponse.contains("category"),
                "El JSON debe contener los campos esperados de la API");
    }

    @Test
    @DisplayName("Punto 2: Prueba el cliente con servicio no disponible o URL inexistente")
    void shouldThrowIOExceptionWhenHostDoesNotExist() {
        String unreachableBaseUrl = "https://servidor-falso-inexistente-kata4.com";
        HTTPSerializedJokeReader reader = new HTTPSerializedJokeReader(unreachableBaseUrl);

        assertThrows(IOException.class, reader::readJokes,
                "Debe lanzar una IOException cuando el host no existe o no hay red");
    }

    @Test
    @DisplayName("Punto 3: Prueba el cliente con una URL con esquema o sintaxis inválida")
    void shouldFailWhenUrlIsMalformed() {
        String invalidUrl = "htp://url-con-protocolo-invalido";
        HTTPSerializedJokeReader reader = new HTTPSerializedJokeReader(invalidUrl);

        assertThrows(Exception.class, reader::readJokes,
                "Debe propagar error de URI/HTTP al recibir una URL mal formada");
    }

    @Test
    @DisplayName("Punto 4: Prueba un identificador inexistente en el servicio (HTTP 404)")
    void shouldHandleNonExistentIdentifierGracefully() {
        HTTPSerializedJokeReader reader = new HTTPSerializedJokeReader("https://v2.jokeapi.dev/joke/Any?safe-mode&type=null");
        assertDoesNotThrow(reader::readJokes);
    }

    @Test
    @DisplayName("Punto 5: Comprueba que se puede instanciar con diferentes URL base")
    void shouldAllowConfiguringCustomBaseUrls() {
        String customUrl1 = "https://v2.jokeapi.dev";
        String customUrl2 = "http://localhost:8080";

        assertDoesNotThrow(() -> new HTTPSerializedJokeReader(customUrl1));
        assertDoesNotThrow(() -> new HTTPSerializedJokeReader(customUrl2));
    }
}