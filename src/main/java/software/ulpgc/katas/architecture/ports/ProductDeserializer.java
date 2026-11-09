package software.ulpgc.katas.architecture.ports;

import software.ulpgc.katas.domain.model.Product;

import java.util.List;

public interface ProductDeserializer {
    List<Product> deserializeProducts();
}
