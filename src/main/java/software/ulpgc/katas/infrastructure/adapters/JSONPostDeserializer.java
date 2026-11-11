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
        return deserializePosts(new Gson().fromJson(serializedJokes, JsonElement.class).getAsJsonArray());
    }

    private List<Post> deserializePosts(JsonArray jsonJokeArray) {
        List<Post> jokes = new ArrayList<>();

        for (JsonElement jsonElement : jsonJokeArray) {
            jokes.add(deserializePosts(jsonElement));
        }

        return jokes;
    }

    private Post deserializePosts(JsonElement jsonElement) {
        return new Gson().fromJson(jsonElement, Post.class);
    }
}
