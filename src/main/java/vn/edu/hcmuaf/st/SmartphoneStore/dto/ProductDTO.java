package vn.edu.hcmuaf.st.SmartphoneStore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private int productId;
    private String name;
    private String img;
    private String brand;
    private String model;
    private BigDecimal price;
    private String description;
    private int stockQuantity;
}
