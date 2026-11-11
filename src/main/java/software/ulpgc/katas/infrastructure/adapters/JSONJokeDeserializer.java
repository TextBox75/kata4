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
        try {
            return deserializeJokes(new Gson().fromJson(serializedJokes, JsonObject.class).get("jokes").getAsJsonArray());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private List<Joke> deserializeJokes(JsonArray jsonJokeArray) {
        List<Joke> jokes = new ArrayList<>();

        for (JsonElement jsonElement : jsonJokeArray) {
            try {
                jokes.add(deserializeJokes(jsonElement));
            } catch (Exception e) {
                continue;
            }
        }

        return jokes;
    }

    private Joke deserializeJokes(JsonElement jsonElement) {
        return new Gson().fromJson(jsonElement, Joke.class);
    }
}
