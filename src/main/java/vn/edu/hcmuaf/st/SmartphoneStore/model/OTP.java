package vn.edu.hcmuaf.st.SmartphoneStore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OTP {
    private static final int EXPIRATION = 60 * 24;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String otp;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    private Date expiryDate;
}
