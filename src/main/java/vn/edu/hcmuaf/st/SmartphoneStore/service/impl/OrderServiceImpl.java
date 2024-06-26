package vn.edu.hcmuaf.st.SmartphoneStore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.CheckoutRequestDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.OrderDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.OrderDetailDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Cart;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Order;
import vn.edu.hcmuaf.st.SmartphoneStore.model.OrderDetail;
import vn.edu.hcmuaf.st.SmartphoneStore.model.User;
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart = cartRepository.findByUser_UserId(user.getUserId());
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal totalAmount = cart.getCartItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create Order
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setEmail(checkoutRequest.getEmail());
        order.setFirstName(checkoutRequest.getFirstName());
        order.setLastName(checkoutRequest.getLastName());
        order.setPhoneNumber(checkoutRequest.getPhoneNumber());
        order.setShippingAddress(checkoutRequest.getShippingAddress());
        order.setCountry(checkoutRequest.getCountry());
        order.setShippingMethod(checkoutRequest.getShippingMethod());
        order.setPaymentMethod(checkoutRequest.getPaymentMethod());
        order.setTotalAmount(BigDecimal.valueOf(totalAmount.doubleValue()));
        order.setStatus("PENDING");
        order = orderRepository.save(order);

        // Create Order Details
        Order finalOrder = order;

        List<OrderDetail> orderDetails = cart.getCartItems().stream().map(cartItem -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(finalOrder);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(cartItem.getProduct().getPrice());
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
        orderDTO.setEmail(order.getEmail());
        orderDTO.setFirstName(order.getFirstName());
        orderDTO.setLastName(order.getLastName());
        orderDTO.setPhoneNumber(order.getPhoneNumber());
        orderDTO.setCountry(order.getCountry());
        orderDTO.setShippingAddress(order.getShippingAddress());
        orderDTO.setShippingMethod(order.getShippingMethod());
        orderDTO.setPaymentMethod(order.getPaymentMethod());
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


    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersOfUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = user.getUserId();
        return orderRepository.findByUser_UserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO convertToDTO(Order order) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_OrderId(order.getOrderId());
        List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream()
                .map(detail -> new OrderDetailDTO(detail.getProduct().getProductId(), detail.getProduct().getName(), detail.getQuantity(), detail.getPrice()))
                .collect(Collectors.toList());

        return new OrderDTO(
                order.getOrderId(),
                order.getUser().getUserId(),
                order.getEmail(),
                order.getFirstName(),
                order.getLastName(),
                order.getPhoneNumber(),
                order.getShippingAddress(),
                order.getCountry(),
                order.getShippingMethod(),
                order.getPaymentMethod(),
                order.getTotalAmount(),
                orderDetailDTOs
        );
    }

//    private OrderDetailDTO convertToOrderDetailDTO(OrderDetail orderDetail) {
//        return new OrderDetailDTO(
//                orderDetail.getProduct().getProductId(),
//                orderDetail.getProduct().getName(),
//                orderDetail.getQuantity(),
//                orderDetail.getPrice()
//        );
//    }
}

