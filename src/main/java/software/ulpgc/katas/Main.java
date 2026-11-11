package software.ulpgc.katas;

import software.ulpgc.katas.domain.model.Post;
import software.ulpgc.katas.infrastructure.adapters.HTTPSerializedPostReader;
import software.ulpgc.katas.infrastructure.adapters.JSONPostDeserializer;

import java.io.IOException;
import java.util.List;

public class Main {
    static void main() {
        try {
            HTTPSerializedPostReader reader = new HTTPSerializedPostReader(
                    "https://jsonplaceholder.typicode.com/posts?_limit=30"
            );

            String serializedposts = reader.readPosts();

            JSONPostDeserializer deserializer = new JSONPostDeserializer(serializedposts);
            List<Post> posts = deserializer.deserializePosts();

            for (Post post : posts) {
                System.out.println(post.toString());
            }
        } catch (Exception ex) {
            return;
        }
    }
}
