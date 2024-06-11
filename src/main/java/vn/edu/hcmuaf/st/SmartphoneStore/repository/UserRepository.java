package vn.edu.hcmuaf.st.SmartphoneStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.st.SmartphoneStore.model.User;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserId(int userId);
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
    Optional<User> findByEmail(String email);
}
