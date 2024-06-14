package vn.edu.hcmuaf.st.SmartphoneStore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

    List<Product> findByCategories_CategoryId(int categoryId);


    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    @Query("SELECT p FROM Product p WHERE " +
            "(?1 IS NULL OR p.name LIKE %?1% OR p.description LIKE %?1%) AND " +
            "(?2 IS NULL OR p.brand = ?2)")
    Page<Product> findByCriteria(String query, String brand, Pageable pageable);
}
