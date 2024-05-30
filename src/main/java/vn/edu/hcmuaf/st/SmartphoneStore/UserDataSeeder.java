package vn.edu.hcmuaf.st.SmartphoneStore;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vn.edu.hcmuaf.st.SmartphoneStore.model.User;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        List<User> users = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            User user = new User();
            user.setUsername(faker.name().username());
            user.setPassword(faker.internet().password(10, 14));
            user.setEmail(faker.internet().emailAddress());
            user.setFullName(faker.name().fullName());
            user.setPhoneNumber(faker.phoneNumber().phoneNumber());
            user.setAddress(faker.address().fullAddress());
            user.setRole("USER"); // Default role, adjust as needed
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            users.add(user);
        }

        userRepository.saveAll(users);
    }
}
