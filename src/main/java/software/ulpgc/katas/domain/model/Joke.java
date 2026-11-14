package software.ulpgc.katas.domain.model;

public record Joke(int id, JokeCategory category, String setup, String delivery) {
    @Override
    public String toString() {
        return "Joke " + id + " [" + category + "]: " + setup + " " + delivery;
    }
}
