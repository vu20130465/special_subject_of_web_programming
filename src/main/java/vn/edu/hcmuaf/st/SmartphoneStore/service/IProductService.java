package vn.edu.hcmuaf.st.SmartphoneStore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();

    Product getProductById(int id);

    Product createProduct(Product product);

    Product updateProduct(int id, Product productDetails);

    boolean deleteProduct(int id);

    List<Product> getProductsByCategory(int categoryId);

    Page<Product> findByCriteria(String query, String brand, Pageable pageable);
}