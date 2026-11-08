package software.ulpgc.katas;

import software.ulpgc.katas.domain.model.Joke;
import software.ulpgc.katas.infrastructure.adapters.HTTPSerializedJokeReader;
import software.ulpgc.katas.infrastructure.adapters.JSONJokeDeserializer;

import java.io.IOException;
import java.util.List;

public class Main {
    static void main() throws IOException, InterruptedException {
        HTTPSerializedJokeReader reader = new HTTPSerializedJokeReader(
                "https://v2.jokeapi.dev/joke/Any?safe-mode&type=twopart&amount=10"
        );

        String serializedJokes = reader.readJokes();

        JSONJokeDeserializer deserializer = new JSONJokeDeserializer(serializedJokes);
        List<Joke> jokes = deserializer.deserializeJokes();

        for (Joke joke : jokes) {
            System.out.println(joke.toString());
        }
    }
}
