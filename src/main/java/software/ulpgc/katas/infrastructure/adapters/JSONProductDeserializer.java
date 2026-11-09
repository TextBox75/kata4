package software.ulpgc.katas.infrastructure.adapters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import software.ulpgc.katas.architecture.ports.ProductDeserializer;
import software.ulpgc.katas.domain.model.Product;

import java.util.ArrayList;
import java.util.List;

public final class JSONProductDeserializer implements ProductDeserializer {

    private final String serializedProducts;

    public JSONProductDeserializer(String serializedProducts) {
        this.serializedProducts = serializedProducts;
    }

    @Override
    public List<Product> deserializeProducts() {
        return deserializeProducts(new Gson().fromJson(serializedProducts, JsonObject.class).get("products").getAsJsonArray());
    }

    private List<Product> deserializeProducts(JsonArray jsonJokeArray) {
        List<Product> Products = new ArrayList<>();

        for (JsonElement jsonElement : jsonJokeArray) {
            Products.add(deserializeProducts(jsonElement));
        }

        return Products;
    }

    private Product deserializeProducts(JsonElement jsonElement) {
        return new Gson().fromJson(jsonElement, Product.class);
    }
}
