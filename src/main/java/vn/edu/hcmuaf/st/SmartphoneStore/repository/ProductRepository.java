package vn.edu.hcmuaf.st.SmartphoneStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

    List<Product> findByCategories_CategoryId(int categoryId);

    List<Product> findByNameContainingOrDescriptionContaining(String query, String query1);
}
