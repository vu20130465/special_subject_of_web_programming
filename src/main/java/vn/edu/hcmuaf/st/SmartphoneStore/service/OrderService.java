package vn.edu.hcmuaf.st.SmartphoneStore.service;

import vn.edu.hcmuaf.st.SmartphoneStore.dto.CheckoutRequestDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO checkout(CheckoutRequestDTO checkoutRequest);
    List<OrderDTO> getAllOrders();
    List<OrderDTO> getOrdersOfUser();
}

