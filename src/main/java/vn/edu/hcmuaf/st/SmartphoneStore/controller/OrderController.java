package vn.edu.hcmuaf.st.SmartphoneStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.CheckoutRequestDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.OrderDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = {"http://localhost:3000"})
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderDTO> checkout(@RequestBody CheckoutRequestDTO checkoutRequest) {
        OrderDTO orderDTO = orderService.checkout(checkoutRequest);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/getAllByUser")
    public ResponseEntity<List<OrderDTO>> getAllOrdersByUser() {
        List<OrderDTO> orders = orderService.getOrdersOfUser();
        return ResponseEntity.ok(orders);
    }
}

