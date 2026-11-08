package software.ulpgc.katas.architecture.ports;

import java.io.IOException;

public interface SerializedPostReader {
    String readPosts() throws IOException, InterruptedException;
}
