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
    

    Page<Product> findAll(Pageable pageable);
}
