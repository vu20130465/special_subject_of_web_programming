package vn.edu.hcmuaf.st.SmartphoneStore.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.CartDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.model.User;
import vn.edu.hcmuaf.st.SmartphoneStore.service.CartService;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = {"http://localhost:3000"})
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/get")
    public ResponseEntity<CartDTO> getCart() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = user.getUserId();
        CartDTO cartDTO = cartService.getCartByUserId(userId);
        if (cartDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCartByUserId(@PathVariable int userId) {
        CartDTO cartDTO = cartService.getCartByUserId(userId);
        if (cartDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<CartDTO> addProductToCart(@RequestParam int productId, @RequestParam int quantity) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = user.getUserId();
        CartDTO cartDTO = cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok(cartDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<CartDTO> updateProductQuantity( @RequestParam int productId, @RequestParam int quantity) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = user.getUserId();
        CartDTO cartDTO = cartService.updateProductQuantity(userId, productId, quantity);
        return ResponseEntity.ok(cartDTO);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<CartDTO> removeProductFromCart( @RequestParam int productId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = user.getUserId();
        CartDTO cartDTO = cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok(cartDTO);
    }
}
