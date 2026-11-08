package software.ulpgc.katas.architecture.ports;

import software.ulpgc.katas.domain.model.Joke;

import java.util.List;

public interface JokeDeserializer {
    List<Joke> deserializeJokes();
}
