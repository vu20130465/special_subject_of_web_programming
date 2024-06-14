package vn.edu.hcmuaf.st.SmartphoneStore.service;

import vn.edu.hcmuaf.st.SmartphoneStore.dto.CartDTO;

public interface CartService {
    CartDTO getCartByUserId(int userId);

    CartDTO addProductToCart(int userId, int productId, int quantity);

    CartDTO updateProductQuantity(int userId, int productId, int quantity);

    CartDTO removeProductFromCart(int userId, int productId);
}
