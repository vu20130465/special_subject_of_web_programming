package vn.edu.hcmuaf.st.SmartphoneStore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.ProductDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Product;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.ProductRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements vn.edu.hcmuaf.st.SmartphoneStore.service.ProductService {
    private final ProductRepository productRepository;

    private final ReviewRepository reviewRepository;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(int id) {
        return productRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(int id, ProductDTO productDTO) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDTO.getName());
            product.setImg(productDTO.getImg());
            product.setBrand(productDTO.getBrand());
            product.setModel(productDTO.getModel());
            product.setPrice(productDTO.getPrice());
            product.setDescription(productDTO.getDescription());
            product.setStockQuantity(productDTO.getStockQuantity());
            return convertToDTO(productRepository.save(product));
        } else {
            return null;
        }
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductDTO> findByCriteria(String query, String brand, Pageable pageable) {
        return null;
    }


//    @Override
//    public List<ProductDTO> getProductsByCategory(int categoryId) {
//        return productRepository.findByCategories_CategoryId(categoryId);
//    }

    @Override
    public Page<ProductDTO> searchProductsByName(String name, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(this::convertToDTO);
    }


    @Override
    public List<ProductDTO> getLatestProducts() {
        Pageable pageable = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getProductId(),
                product.getName(),
                product.getImg(),
                product.getBrand(),
                product.getModel(),
                product.getPrice(),
                product.getDescription(),
                product.getStockQuantity()
        );
    }

    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setImg(productDTO.getImg());
        product.setBrand(productDTO.getBrand());
        product.setModel(productDTO.getModel());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setStockQuantity(productDTO.getStockQuantity());
        return product;
    }
}
