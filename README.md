# IS2 Kata 4

### Objetivo

El objetivo de esta kata es evolucionar la lectura directa desde streams a la construcción de un cliente HTTP robusto en Java con responsabilidades separadas. Se busca conectar con servicios web públicos mediante peticiones HTTP estructuradas, transformar las respuestas JSON a objetos del dominio/DTOs y presentar los resultados por consola, aislando la interfaz (puerto), el adaptador HTTP, el parser/deserializador y el modelo de datos, además de gestionar de forma controlada errores de red, respuestas no válidas y códigos de estado HTTP.

### Cómo compilar y ejecutar

Clonar el repositorio con:

```bash
git clone https://github.com/TextBox75/kata4.git
```

Abrir la carpeta con **IntelliJ IDEA** utilizando **JDK 21** (o superior).

Asegurarse de que las dependencias de Maven se descarguen correctamente (`pom.xml`).

Ejecutar la clase `Main` del proyecto o ejecutar la suite de pruebas unitarias automatizadas con Maven (`mvn test`).

**Dependencias del proyecto:**

* `org.junit.jupiter:junit-jupiter` (para pruebas unitarias automatizadas).


* `com.google.code.gson:gson` (para la deserialización de las respuestas JSON a objetos).



---

### Estructura de la entrega y repeticiones de la kata

Para cumplir con la dinámica de repetición deliberada requerida, el ejercicio se ha replicado a través de 3 ramas con alcance equivalente consumiendo APIs públicas sin credenciales:

* **Master branch (Versión evaluable / JokeAPI):**
  Cliente HTTP enfocado en consumir la API de chistes de *JokeAPI* (`[https://v2.jokeapi.dev](https://v2.jokeapi.dev)`). Consulta colecciones de chistes de tipo pregunta-respuesta (*twopart*), procesa el JSON recibido mapeando categorías a enums de dominio limpios y muestra una salida formateada por consola.


* **JsonPlaceholder branch (Segunda repetición):**
  Implementación equivalente consumiendo *JSONPlaceholder* (`[https://jsonplaceholder.typicode.com](https://jsonplaceholder.typicode.com)`) para consultar publicaciones (`/posts?_limit=5`) y posts individuales. El modelo principal pasa a ser `Post`, adaptando el deserializador a una estructura centrada en artículos con títulos y cuerpos.


* **DummyJson branch (Tercera repetición):**
  Tercera réplica orientada a un catálogo de productos comerciales utilizando *DummyJSON* (`[https://dummyjson.com/products?limit=5](https://dummyjson.com/products?limit=5)`). Trabaja con el modelo de dominio `Product`, transformando atributos numéricos y categóricos de artículos en venta.



Todas las repeticiones mantienen la misma arquitectura desacoplada:

* **Dominio (`software.ulpgc.katas.domain.model`):** Clases y records puros (`Joke`, `Post`, `Product`) y enums asociados sin dependencias de librerías externas ni anotaciones JSON.


* **Puertos de arquitectura (`software.ulpgc.katas.architecture.ports`):** Interfaces que expresan las necesidades de la aplicación sin acoplarse a detalles de transporte ni a URLs (`SerializedJokeReader`, `JokeDeserializer`).


* **Adaptadores de infraestructura (`software.ulpgc.katas.infrastructure.adapters`):** Implementaciones concretas como `HTTPSerializedJokeReader` (que usa `java.net.http.HttpClient` recibiendo la URL base inyectable por constructor) y los deserializadores basados en Gson.


* **Orquestación y presentación (`Main`):** Centraliza la configuración de la URL base, inyecta las dependencias, coordina el flujo y realiza la salida formateada por consola, capturando excepciones para presentar mensajes de error comprensibles.



---

### Enlace al vídeo

(Enlace de sustitución con duración máxima de 7 minutos).

---

### Verificación

La verificación se ha documentado e implementado mediante pruebas automatizadas con **JUnit Jupiter**, cubriendo los casos requeridos en el enunciado:

* **Respuesta correcta:** Verificación de consulta exitosa recibiendo payloads válidos y parseables.


* **Servicio no disponible / URL rota:** Simulación de fallo de red mediante un host inexistente (`IOException`) sin que el sistema falle abruptamente.


* **Identificador inexistente (HTTP 404):** Validación de lanzamiento y captura de excepción controlada al consultar IDs que no existen en el servidor.


* **Configuración centralizada de URL base:** Comprobación de que las clases adaptadoras permiten inyectar URLs base dinámicas sin modificar el código interno.


* **Ausencia de credenciales:** El proyecto utiliza exclusivamente APIs abiertas que no requieren claves, tokens ni rutas absolutas locales.