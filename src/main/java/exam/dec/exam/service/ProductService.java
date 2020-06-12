package exam.dec.exam.service;

import exam.dec.exam.model.service.ProductServiceModel;

import java.util.List;

public interface ProductService {
    void saveProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> getAllProducts();

    ProductServiceModel getProductById(String id);

    void deleteProduct(String id);
}
