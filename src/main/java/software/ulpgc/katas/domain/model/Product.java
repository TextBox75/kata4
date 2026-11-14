package software.ulpgc.katas.domain.model;

public record Product(int id, String title, String description, float price) {
    @Override
    public String toString() {
        return "[Product " + id + "]: " + title + ", " + description + " " + price + "€";
    }
}
