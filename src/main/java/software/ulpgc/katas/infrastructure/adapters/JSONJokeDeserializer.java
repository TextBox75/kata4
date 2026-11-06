package software.ulpgc.katas.infrastructure.adapters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import software.ulpgc.katas.architecture.ports.JokeDeserializer;
import software.ulpgc.katas.domain.model.Joke;
import software.ulpgc.katas.domain.model.RawJoke;

import java.util.ArrayList;
import java.util.List;

public class JSONJokeDeserializer implements JokeDeserializer {

    private final String serializedJokes;

    public JSONJokeDeserializer(String serializedJokes) {
        this.serializedJokes = serializedJokes;
    }

    @Override
    public List<RawJoke> DeserializeJokes() {
        return DeserializeJokes(new Gson().fromJson(serializedJokes, JsonArray.class).getAsJsonArray());
    }

    private List<RawJoke> DeserializeJokes(JsonArray jsonJokeArray) {
        List<RawJoke> jokes = new ArrayList<>();

        for (JsonElement jsonElement : jsonJokeArray) {
            jokes.add(DeserializeJokes(jsonElement));
        }

        return jokes;
    }

    private RawJoke DeserializeJokes(JsonElement jsonElement) {
        return new Gson().fromJson(jsonElement, RawJoke.class);
    }
}
