package vn.edu.hcmuaf.st.SmartphoneStore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String status;
    private BigDecimal totalAmount;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String country;
    private String shippingAddress;
    private String shippingMethod;
    private String paymentMethod;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
}
