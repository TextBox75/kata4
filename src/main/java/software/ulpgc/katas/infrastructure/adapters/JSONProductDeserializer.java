package software.ulpgc.katas.infrastructure.adapters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import software.ulpgc.katas.architecture.ports.ProductDeserializer;
import software.ulpgc.katas.domain.model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class JSONProductDeserializer implements ProductDeserializer {

    private final String serializedProducts;

    public JSONProductDeserializer(String serializedProducts) {
        this.serializedProducts = serializedProducts;
    }

    @Override
    public List<Product> deserializeProducts() {
        try {
            return deserializeProducts(new Gson().fromJson(serializedProducts, JsonObject.class).get("products").getAsJsonArray());
        } catch (Exception e) {
            throw new IllegalArgumentException("El formato general del JSON recibido es inválido.");
        }
    }

    private List<Product> deserializeProducts(JsonArray jsonProductArray) {
        List<Product> Products = new ArrayList<>();

        for (JsonElement jsonElement : jsonProductArray) {
            try {
                Products.add(deserializeProducts(jsonElement));
            } catch (Exception e) {
                continue;
            }
        }

        return Products;
    }

    private Product deserializeProducts(JsonElement jsonElement) {
        return new Gson().fromJson(jsonElement, Product.class);
    }
}
