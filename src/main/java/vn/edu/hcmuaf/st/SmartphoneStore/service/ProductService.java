package vn.edu.hcmuaf.st.SmartphoneStore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(int id);

    ProductDTO createProduct(ProductDTO product);

    ProductDTO updateProduct(int id, ProductDTO productDTO);

    void deleteProduct(int id);

//    List<ProductDTO> getProductsByCategory(int categoryId);

    Page<ProductDTO> findByCriteria(String query, String brand, Pageable pageable);

    Page<ProductDTO> searchProductsByName(String name, Pageable pageable);

    List<ProductDTO> getLatestProducts();
}