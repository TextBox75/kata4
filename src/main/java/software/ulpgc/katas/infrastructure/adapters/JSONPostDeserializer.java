package software.ulpgc.katas.infrastructure.adapters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import software.ulpgc.katas.architecture.ports.PostDeserializer;
import software.ulpgc.katas.domain.model.Post;

import java.util.ArrayList;
import java.util.List;

public final class JSONPostDeserializer implements PostDeserializer {

    private final String serializedPosts;

    public JSONPostDeserializer(String serializedPosts) {
        this.serializedPosts = serializedPosts;
    }

    @Override
    public List<Post> deserializePosts() {
        try {
            return deserializePosts(new Gson().fromJson(serializedPosts, JsonElement.class).getAsJsonArray());
        } catch (Exception e) {
            throw new IllegalArgumentException("El formato general del JSON recibido es inválido.");
        }
    }

    private List<Post> deserializePosts(JsonArray jsonPostArray) {
        List<Post> posts = new ArrayList<>();

        for (JsonElement jsonElement : jsonPostArray) {
            try {
                posts.add(deserializePosts(jsonElement));
            } catch (Exception e) {
                continue;
            }
        }

        return posts;
    }

    private Post deserializePosts(JsonElement jsonElement) {
        return new Gson().fromJson(jsonElement, Post.class);
    }
}
