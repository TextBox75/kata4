package software.ulpgc.katas.architecture.ports;

import software.ulpgc.katas.domain.model.Post;

import java.util.List;

public interface PostDeserializer {
    List<Post> deserializePosts();
}
