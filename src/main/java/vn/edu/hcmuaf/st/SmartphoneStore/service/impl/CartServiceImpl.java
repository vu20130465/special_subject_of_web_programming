package vn.edu.hcmuaf.st.SmartphoneStore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.CartDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.CartItemDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Cart;
import vn.edu.hcmuaf.st.SmartphoneStore.model.CartItem;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Product;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.CartItemRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.CartRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.ProductRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.UserRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.service.CartService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public CartDTO getCartByUserId(int userId) {
        Cart cart = cartRepository.findByUser_UserId(userId);
        if (cart == null) {
            return null;
        }

        return mapToCartDTO(cart);
    }

    @Override
    public CartDTO addProductToCart(int userId, int productId, int quantity) {
        Cart cart = cartRepository.findByUser_UserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
            cart.setCreatedAt(LocalDateTime.now());
            cart.setUpdatedAt(LocalDateTime.now());
        }

        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>());
        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Optional<CartItem> existingItem = cart.getCartItems().stream().filter(item -> item.getProduct().getProductId() == productId).findFirst();

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setUpdatedAt(LocalDateTime.now());
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCreatedAt(LocalDateTime.now());
            cartItem.setUpdatedAt(LocalDateTime.now());
            cart.getCartItems().add(cartItem);
        }

        cart.setUpdatedAt(LocalDateTime.now());
        cart = cartRepository.save(cart);

        return mapToCartDTO(cart);
    }

    @Override
    public CartDTO updateProductQuantity(int userId, int productId, int quantity) {
        Cart cart = cartRepository.findByUser_UserId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        CartItem cartItem = cart.getCartItems().stream().filter(item -> item.getProduct().getProductId() == productId).findFirst().orElseThrow(() -> new RuntimeException("Product not found in cart"));
        cartItem.setQuantity(quantity);
        cartItem.setUpdatedAt(LocalDateTime.now());

        cart.setUpdatedAt(LocalDateTime.now());
        cart = cartRepository.save(cart);

        return mapToCartDTO(cart);
    }

    @Override
    public CartDTO removeProductFromCart(int userId, int productId) {
        Cart cart = cartRepository.findByUser_UserId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        CartItem cartItem = cart.getCartItems().stream().filter(item -> item.getProduct().getProductId() == productId).findFirst().orElseThrow(() -> new RuntimeException("Product not found in cart"));
        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        cart.setUpdatedAt(LocalDateTime.now());
        cart = cartRepository.save(cart);

        return mapToCartDTO(cart);
    }

    private CartDTO mapToCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getCartId());
        cartDTO.setUserId(cart.getUser().getUserId());
        List<CartItemDTO> cartItemDTOs = cart.getCartItems().stream().map(this::mapToCartItemDTO).collect(Collectors.toList());
        cartDTO.setCartItems(cartItemDTOs);
        return cartDTO;
    }

    private CartItemDTO mapToCartItemDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setProductId(cartItem.getProduct().getProductId());
        cartItemDTO.setProductName(cartItem.getProduct().getName());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setPrice(cartItem.getProduct().getPrice());
        return cartItemDTO;
    }
}
