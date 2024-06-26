package vn.edu.hcmuaf.st.SmartphoneStore.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.ProductDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.response.DeleteResponse;
import vn.edu.hcmuaf.st.SmartphoneStore.service.impl.ProductServiceImpl;

import java.util.List;

@RequestMapping("/product")
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class ProductController {
    private final ProductServiceImpl productServiceImpl;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    // Get all products
    @GetMapping("/getAll")
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        List<ProductDTO> products = productServiceImpl.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Get a single product by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable int id) {
        ProductDTO product = productServiceImpl.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    // Create a new product
    @PostMapping("/add")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productServiceImpl.createProduct(productDTO);
        return ResponseEntity.ok(createdProduct);
    }

    // Update an existing product
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable int id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productServiceImpl.updateProduct(id, productDTO);
        if (updatedProduct == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteProduct(@PathVariable int id) {
        productServiceImpl.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    // Get products by category
//    @GetMapping("/category/{categoryId}")
//    public List<ProductDTO> getProductsByCategory(@PathVariable int categoryId) {
//        return productService.getProductsByCategory(categoryId);
//    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> searchProducts(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "price,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sort[0]));
        Page<ProductDTO> products = productServiceImpl.searchProductsByName(query, pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/latest-products")
    public ResponseEntity<List<ProductDTO>> getLatestProducts() {
        try {
            List<ProductDTO> latestProducts = productServiceImpl.getLatestProducts();
            return new ResponseEntity<>(latestProducts, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching latest products", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
