package vn.edu.hcmuaf.st.SmartphoneStore.repository;

import vn.edu.hcmuaf.st.SmartphoneStore.model.Cart;
import vn.edu.hcmuaf.st.SmartphoneStore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    void deleteByCart(Cart cart);
}
