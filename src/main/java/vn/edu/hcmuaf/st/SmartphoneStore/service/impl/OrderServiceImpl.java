package vn.edu.hcmuaf.st.SmartphoneStore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.CheckoutRequestDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.OrderDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.OrderDetailDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Cart;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Order;
import vn.edu.hcmuaf.st.SmartphoneStore.model.OrderDetail;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.CartItemRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.CartRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.OrderDetailRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.OrderRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    @Override
    public OrderDTO checkout(CheckoutRequestDTO checkoutRequest) {
        Cart cart = cartRepository.findByUser_UserId(checkoutRequest.getUserId());
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal totalAmount = cart.getCartItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create Order
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setShippingAddress(checkoutRequest.getShippingAddress());
        order.setTotalAmount(totalAmount);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order = orderRepository.save(order);

        // Create Order Details
        Order finalOrder = order;

        List<OrderDetail> orderDetails = cart.getCartItems().stream().map(cartItem -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(finalOrder);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(cartItem.getProduct().getPrice());
            orderDetail.setCreatedAt(LocalDateTime.now());
            orderDetail.setUpdatedAt(LocalDateTime.now());
            return orderDetail;
        }).collect(Collectors.toList());
        orderDetailRepository.saveAll(orderDetails);

        // Clear Cart
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);

        return mapToOrderDTO(order, orderDetails);
    }

    private OrderDTO mapToOrderDTO(Order order, List<OrderDetail> orderDetails) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setUserId(order.getUser().getUserId());
        orderDTO.setShippingAddress(order.getShippingAddress());
        orderDTO.setTotalAmount(order.getTotalAmount());

        List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream().map(orderDetail -> {
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setProductId(orderDetail.getProduct().getProductId());
            orderDetailDTO.setProductName(orderDetail.getProduct().getName());
            orderDetailDTO.setQuantity(orderDetail.getQuantity());
            orderDetailDTO.setPrice(orderDetail.getPrice());
            return orderDetailDTO;
        }).collect(Collectors.toList());

        orderDTO.setOrderDetails(orderDetailDTOs);
        return orderDTO;
    }
}

