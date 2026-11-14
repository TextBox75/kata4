package software.ulpgc.katas;

import software.ulpgc.katas.architecture.ports.PostDeserializer;
import software.ulpgc.katas.architecture.ports.SerializedPostReader;
import software.ulpgc.katas.domain.model.Post;
import software.ulpgc.katas.infrastructure.adapters.HTTPSerializedPostReader;
import software.ulpgc.katas.infrastructure.adapters.JSONPostDeserializer;

import java.io.IOException;
import java.util.List;

public class Main {
    static void main() throws IOException, InterruptedException {
        SerializedPostReader reader = new HTTPSerializedPostReader(
                "https://jsonplaceholder.typicode.com/posts?_limit=30"
        );

        String serializedPosts = reader.readPosts();

        PostDeserializer deserializer = new JSONPostDeserializer(serializedPosts);
        List<Post> posts = deserializer.deserializePosts();

        for (Post post : posts) {
            System.out.println(post.toString());
        }
    }
}
