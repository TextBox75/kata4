package software.ulpgc.katas.domain.model;

public record Post(int userId, int id, String title, String body) {
    @Override
    public String toString() {
        return "[Post " + id + " by user ID " + userId + " with title " + title + "]: " + body;
    }
}
