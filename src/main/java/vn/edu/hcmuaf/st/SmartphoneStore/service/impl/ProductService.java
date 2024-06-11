package vn.edu.hcmuaf.st.SmartphoneStore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Product;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Review;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.ProductRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.ReviewRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.service.IProductService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    private final ReviewRepository reviewRepository;
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }
    public List<Review> getReviewsByProductId(int productId) {
        return reviewRepository.findByProduct_ProductId(productId);
    }
    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(int id, Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDetails.getName());
            product.setImg(productDetails.getImg());
            product.setBrand(productDetails.getBrand());
            product.setModel(productDetails.getModel());
            product.setPrice(productDetails.getPrice());
            product.setDescription(productDetails.getDescription());
            product.setStockQuantity(productDetails.getStockQuantity());
            product.setCategories(productDetails.getCategories());
            return productRepository.save(product);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteProduct(int id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        return productRepository.findByCategories_CategoryId(categoryId);
    }

    @Override
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingOrDescriptionContaining(query, query);
    }
}
