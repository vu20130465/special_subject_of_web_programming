package vn.edu.hcmuaf.st.SmartphoneStore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hcmuaf.st.SmartphoneStore.model.BaseEntity;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Product;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO extends BaseEntity {


    private int idProduct;
    private int idUser;
    private String fullName;
    private int rating;
    private String comment;
}
