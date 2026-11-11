package software.ulpgc.katas;

import software.ulpgc.katas.domain.model.Product;
import software.ulpgc.katas.infrastructure.adapters.HTTPSerializedProductReader;
import software.ulpgc.katas.infrastructure.adapters.JSONProductDeserializer;

import java.io.IOException;
import java.util.List;

public class Main {
    static void main() {
        try {
            HTTPSerializedProductReader reader = new HTTPSerializedProductReader(
                    "https://dummyjson.com/products?limit=5"
            );

            String serializedProducts = reader.readProducts();

            JSONProductDeserializer deserializer = new JSONProductDeserializer(serializedProducts);
            List<Product> products = deserializer.deserializeProducts();

            for (Product product : products) {
                System.out.println(product.toString());
            }
        } catch (Exception e) {
            return;
        }
    }
}
