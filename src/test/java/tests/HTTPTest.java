package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.katas.infrastructure.adapters.HTTPSerializedPostReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HTTPTest {

    private static final String VALID_BASE_URL = "https://jsonplaceholder.typicode.com";

    @Test
    @DisplayName("Punto 1: Ejecuta el cliente con respuesta correcta (lista o elemento)")
    void shouldReturnValidJsonWhenBaseUrlAndEndpointAreCorrect() throws Exception {
        HTTPSerializedPostReader reader = new HTTPSerializedPostReader(VALID_BASE_URL);

        String jsonResponse = reader.readPosts(); // o readPost(1) según tu método

        assertNotNull(jsonResponse, "La respuesta no debe ser nula");
        assertFalse(jsonResponse.isBlank(), "La respuesta no debe estar vacía");

        assertTrue(jsonResponse.contains("userId"), "El JSON debe contener el campo 'userId'");
        assertTrue(jsonResponse.contains("title"), "El JSON debe contener el campo 'title'");
    }

    @Test
    @DisplayName("Punto 2: Prueba el cliente con el servicio no disponible o host inexistente")
    void shouldThrowIOExceptionWhenHostIsUnreachable() {
        String unreachableBaseUrl = "https://servidor-falso-inexistente-kata4.com";
        HTTPSerializedPostReader reader = new HTTPSerializedPostReader(unreachableBaseUrl);

        assertThrows(IOException.class, reader::readPosts,
                "Debe lanzar una IOException cuando el servidor remoto no responde o la red falla");
    }

    @Test
    @DisplayName("Punto 3: Prueba el cliente con una URL mal formada o inválida")
    void shouldFailWhenUrlIsMalformed() {
        String malformedUrl = "not-a-valid-http-url";
        HTTPSerializedPostReader reader = new HTTPSerializedPostReader(malformedUrl);

        assertThrows(Exception.class, reader::readPosts,
                "Debe lanzar una excepción si la sintaxis del esquema o la URI es inválida");
    }

    @Test
    @DisplayName("Punto 4: Prueba un identificador inexistente en el servicio (HTTP 404)")
    void shouldHandleNonExistentIdentifierGracefully() {
        HTTPSerializedPostReader reader = new HTTPSerializedPostReader("https://jsonplaceholder.typicode.com/posts/9999");
        assertDoesNotThrow(reader::readPosts);
    }

    @Test
    @DisplayName("Punto 5: Comprueba que se puede cambiar la URL base dinámicamente")
    void shouldAllowSettingDifferentBaseUrlsWithoutModifyingTheClass() {
        String productionUrl = "https://jsonplaceholder.typicode.com";
        String localTestUrl = "http://localhost:3000";

        assertDoesNotThrow(() -> new HTTPSerializedPostReader(productionUrl));
        assertDoesNotThrow(() -> new HTTPSerializedPostReader(localTestUrl));
    }
}