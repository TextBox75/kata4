package software.ulpgc.katas.architecture.ports;

import software.ulpgc.katas.domain.model.RawJoke;

import java.util.List;

public interface JokeDeserializer {
    List<RawJoke> DeserializeJokes();
}
