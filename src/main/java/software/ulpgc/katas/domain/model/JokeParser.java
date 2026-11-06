package software.ulpgc.katas.domain.model;

import java.util.ArrayList;
import java.util.List;

public record JokeParser(List<RawJoke> rawJokes) {
    public List<Joke> toDomain() {
        List<Joke> jokes = new ArrayList<>();

        for (RawJoke rawJoke : rawJokes) {
            jokes.add(new Joke(
                    Integer.parseInt(rawJoke.id()),
                    fromValue(rawJoke.type()),
                    rawJoke.setup(),
                    rawJoke.punchline()
            ));
        }

        return jokes;
    }

    private JokeType fromValue(String type) {
        if (type.equals("knock-knock")) {
            return JokeType.knock_knock;
        }
        return JokeType.valueOf(type);
    }
}
