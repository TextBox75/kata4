package software.ulpgc.katas.architecture.ports;

import java.io.IOException;

public interface SerializedJokeReader {
    String readJokes() throws IOException, InterruptedException;
}
