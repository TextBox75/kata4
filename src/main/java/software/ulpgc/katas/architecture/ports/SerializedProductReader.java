package software.ulpgc.katas.architecture.ports;

import java.io.IOException;

public interface SerializedProductReader {
    String readProducts() throws IOException, InterruptedException;
}
