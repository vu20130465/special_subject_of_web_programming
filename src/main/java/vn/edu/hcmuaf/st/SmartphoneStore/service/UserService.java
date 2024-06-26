package vn.edu.hcmuaf.st.SmartphoneStore.service;

import vn.edu.hcmuaf.st.SmartphoneStore.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(int id);

    User createUser(User user);

    User updateUser(int id, User user);

    void deleteById(int id);

    User findByUsername(String username);

    boolean existById(int id);

    boolean existByUsername(String username);
}
