package vn.edu.hcmuaf.st.SmartphoneStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUser_UserId(int userId);
}
