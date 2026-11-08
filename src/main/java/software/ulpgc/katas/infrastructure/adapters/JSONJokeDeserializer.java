package software.ulpgc.katas.infrastructure.adapters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import software.ulpgc.katas.architecture.ports.JokeDeserializer;
import software.ulpgc.katas.domain.model.Joke;

import java.util.ArrayList;
import java.util.List;

public final class JSONJokeDeserializer implements JokeDeserializer {

    private final String serializedJokes;

    public JSONJokeDeserializer(String serializedJokes) {
        this.serializedJokes = serializedJokes;
    }

    @Override
    public List<Joke> deserializeJokes() {
        return DeserializeJokes(new Gson().fromJson(serializedJokes, JsonObject.class).get("jokes").getAsJsonArray());
    }

    private List<Joke> DeserializeJokes(JsonArray jsonJokeArray) {
        List<Joke> jokes = new ArrayList<>();

        for (JsonElement jsonElement : jsonJokeArray) {
            jokes.add(DeserializeJokes(jsonElement));
        }

        return jokes;
    }

    private Joke DeserializeJokes(JsonElement jsonElement) {
        return new Gson().fromJson(jsonElement, Joke.class);
    }
}
