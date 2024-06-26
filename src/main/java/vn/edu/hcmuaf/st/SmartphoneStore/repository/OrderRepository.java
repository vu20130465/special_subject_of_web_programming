package vn.edu.hcmuaf.st.SmartphoneStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Order;

import java.util.Collection;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Collection<Order> findByUser_UserId(int userId);
}

