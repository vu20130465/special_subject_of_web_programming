package vn.edu.hcmuaf.st.SmartphoneStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    boolean existsByUsername(String username);
}
