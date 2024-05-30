package vn.edu.hcmuaf.st.SmartphoneStore;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Product;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDataSeeder implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            Product product = new Product();
            product.setName(faker.commerce().productName());
            product.setImg(faker.internet().image());
            product.setBrand(faker.company().name());
            product.setModel(faker.commerce().material());
            product.setPrice(new BigDecimal(faker.commerce().price()));
            product.setDescription(faker.lorem().paragraph());
            product.setStockQuantity(faker.number().numberBetween(1, 100));
            product.setCreatedAt(LocalDateTime.now());
            product.setUpdatedAt(LocalDateTime.now());

            products.add(product);
        }

        productRepository.saveAll(products);
    }
}

