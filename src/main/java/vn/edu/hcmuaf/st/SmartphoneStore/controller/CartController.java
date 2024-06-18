package vn.edu.hcmuaf.st.SmartphoneStore.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.CartDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.service.CartService;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = {"http://localhost:3000"})
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCartByUserId(@PathVariable int userId) {
        CartDTO cartDTO = cartService.getCartByUserId(userId);
        if (cartDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartDTO);
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable int userId, @RequestParam int productId, @RequestParam int quantity) {
        CartDTO cartDTO = cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok(cartDTO);
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<CartDTO> updateProductQuantity(@PathVariable int userId, @RequestParam int productId, @RequestParam int quantity) {
        CartDTO cartDTO = cartService.updateProductQuantity(userId, productId, quantity);
        return ResponseEntity.ok(cartDTO);
    }

    @DeleteMapping("/{userId}/remove")
    public ResponseEntity<CartDTO> removeProductFromCart(@PathVariable int userId, @RequestParam int productId) {
        CartDTO cartDTO = cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok(cartDTO);
    }
}
