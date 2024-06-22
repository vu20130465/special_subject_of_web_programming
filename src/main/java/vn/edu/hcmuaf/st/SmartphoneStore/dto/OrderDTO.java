package vn.edu.hcmuaf.st.SmartphoneStore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private int orderId;
    private int userId;
    private String shippingAddress;
    private BigDecimal totalAmount;
    private List<OrderDetailDTO> orderDetails;

    // Getters and Setters
}

