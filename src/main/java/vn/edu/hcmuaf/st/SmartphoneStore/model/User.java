package vn.edu.hcmuaf.st.SmartphoneStore.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.UserDto;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private String fullName;

    private String phoneNumber;

    @Column(length = 256)
    private String address;

    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    public void loadFromDto(UserDto userDto) {
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
        this.email = userDto.getEmail();
        this.fullName = userDto.getFullName();
        this.phoneNumber = userDto.getPhoneNumber();
        this.address = userDto.getAddress();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
