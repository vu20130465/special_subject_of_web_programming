package vn.edu.hcmuaf.st.SmartphoneStore.service;

import vn.edu.hcmuaf.st.SmartphoneStore.dto.CheckoutRequestDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.OrderDTO;

public interface OrderService {
    OrderDTO checkout(CheckoutRequestDTO checkoutRequest);
}

