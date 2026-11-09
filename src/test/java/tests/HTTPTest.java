package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.katas.infrastructure.adapters.HTTPSerializedProductReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HTTPTest {

    private static final String VALID_BASE_URL = "https://dummyjson.com";

    @Test
    @DisplayName("Punto 1: Ejecuta el cliente con respuesta correcta")
    void shouldReturnValidJsonWhenBaseUrlAndEndpointAreCorrect() throws Exception {
        HTTPSerializedProductReader reader = new HTTPSerializedProductReader(VALID_BASE_URL);

        String jsonResponse = reader.readProducts();

        assertNotNull(jsonResponse, "La respuesta no debe ser nula");
        assertFalse(jsonResponse.isBlank(), "La respuesta no debe estar en blanco");

        assertTrue(jsonResponse.contains("products") || jsonResponse.contains("title"),
                "El JSON debe contener los campos esperados del producto");
    }

    @Test
    @DisplayName("Punto 2: Prueba el cliente con el servicio no disponible o host inexistente")
    void shouldThrowIOExceptionWhenHostIsUnreachable() {
        String unreachableBaseUrl = "https://servidor-falso-inexistente-dummyjson.com";
        HTTPSerializedProductReader reader = new HTTPSerializedProductReader(unreachableBaseUrl);

        assertThrows(IOException.class, reader::readProducts,
                "Debe lanzar una IOException cuando el host no existe o falla la red");
    }

    @Test
    @DisplayName("Punto 2: Prueba el cliente con una URL mal formada o inválida")
    void shouldFailWhenUrlIsMalformed() {
        String malformedUrl = "not-a-valid-http-url";
        HTTPSerializedProductReader reader = new HTTPSerializedProductReader(malformedUrl);

        assertThrows(Exception.class, reader::readProducts,
                "Debe lanzar una excepción al recibir una sintaxis de URI errónea");
    }

    @Test
    @DisplayName("Punto 3: Prueba un identificador inexistente en el servicio (HTTP 404)")
    void shouldHandleNonExistentProductIdentifierGracefully() {
        HTTPSerializedProductReader reader = new HTTPSerializedProductReader("https://dummyjson.com/products/99999");
        assertDoesNotThrow(() -> reader.readProducts(),
                "No debe lanzar excepción");
    }

    @Test
    @DisplayName("Punto 5: Comprueba que se puede cambiar la URL base dinámicamente")
    void shouldAllowSettingDifferentBaseUrlsWithoutModifyingTheClass() {
        String productionUrl = "https://dummyjson.com";
        String localTestUrl = "http://localhost:8080";

        assertDoesNotThrow(() -> new HTTPSerializedProductReader(productionUrl));
        assertDoesNotThrow(() -> new HTTPSerializedProductReader(localTestUrl));
    }
}