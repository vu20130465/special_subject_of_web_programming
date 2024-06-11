package vn.edu.hcmuaf.st.SmartphoneStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByProduct_ProductId(int productId);
}