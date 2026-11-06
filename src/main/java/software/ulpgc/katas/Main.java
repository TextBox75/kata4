package software.ulpgc.katas;

import software.ulpgc.katas.domain.model.Joke;
import software.ulpgc.katas.domain.model.JokeParser;
import software.ulpgc.katas.domain.model.RawJoke;
import software.ulpgc.katas.infrastructure.adapters.HTTPSerializedJokeReader;
import software.ulpgc.katas.infrastructure.adapters.JSONJokeDeserializer;

import java.io.IOException;
import java.util.List;

public class Main {
    static void main() throws IOException, InterruptedException {
        // TODO: el serialized reader indexa internamente la URL, está bien?
        HTTPSerializedJokeReader reader = new HTTPSerializedJokeReader();
        String serializedJokes = reader.ReadJokes();

        JSONJokeDeserializer deserializer = new JSONJokeDeserializer(serializedJokes);
        List<RawJoke> rawJokes = deserializer.DeserializeJokes();

        JokeParser jokeParser = new JokeParser(rawJokes);
        List<Joke> jokes = jokeParser.toDomain();

        for (Joke joke : jokes) {
            System.out.println(joke.toString());
        }
    }
}
