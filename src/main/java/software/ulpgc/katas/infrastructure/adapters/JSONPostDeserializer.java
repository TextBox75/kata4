package software.ulpgc.katas.infrastructure.adapters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import software.ulpgc.katas.architecture.ports.PostDeserializer;
import software.ulpgc.katas.domain.model.Post;

import java.util.ArrayList;
import java.util.List;

public final class JSONPostDeserializer implements PostDeserializer {

    private final String serializedJokes;

    public JSONPostDeserializer(String serializedJokes) {
        this.serializedJokes = serializedJokes;
    }

    @Override
    public List<Post> deserializePosts() {
        try {
            return deserializePosts(new Gson().fromJson(serializedJokes, JsonElement.class).getAsJsonArray());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private List<Post> deserializePosts(JsonArray jsonJokeArray) {
        List<Post> jokes = new ArrayList<>();

        for (JsonElement jsonElement : jsonJokeArray) {
            try {
                jokes.add(deserializePosts(jsonElement));
            } catch (Exception e) {
                continue;
            }
        }

        return jokes;
    }

    private Post deserializePosts(JsonElement jsonElement) {
        return new Gson().fromJson(jsonElement, Post.class);
    }
}
