package vn.edu.hcmuaf.st.SmartphoneStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.CheckoutRequestDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.OrderDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderDTO> checkout(@RequestBody CheckoutRequestDTO checkoutRequest) {
        OrderDTO orderDTO = orderService.checkout(checkoutRequest);
        return ResponseEntity.ok(orderDTO);
    }
}

