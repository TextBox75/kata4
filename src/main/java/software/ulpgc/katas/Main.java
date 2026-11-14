package software.ulpgc.katas;

import software.ulpgc.katas.architecture.ports.ProductDeserializer;
import software.ulpgc.katas.architecture.ports.SerializedProductReader;
import software.ulpgc.katas.domain.model.Product;
import software.ulpgc.katas.infrastructure.adapters.HTTPSerializedProductReader;
import software.ulpgc.katas.infrastructure.adapters.JSONProductDeserializer;

import java.io.IOException;
import java.util.List;

public class Main {
    static void main() throws IOException, InterruptedException {
        SerializedProductReader reader = new HTTPSerializedProductReader(
                "https://dummyjson.com/products?limit=5"
        );

        String serializedProducts = reader.readProducts();

        ProductDeserializer deserializer = new JSONProductDeserializer(serializedProducts);
        List<Product> products = deserializer.deserializeProducts();

        for (Product product : products) {
            System.out.println(product.toString());
        }
    }
}
