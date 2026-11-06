package software.ulpgc.katas.architecture.ports;

import java.io.IOException;

public interface SerializedJokeReader {
    String ReadJokes() throws IOException, InterruptedException;
}
