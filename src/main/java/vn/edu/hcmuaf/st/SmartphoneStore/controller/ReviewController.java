package vn.edu.hcmuaf.st.SmartphoneStore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Review;
import vn.edu.hcmuaf.st.SmartphoneStore.service.IReviewService;
import vn.edu.hcmuaf.st.SmartphoneStore.service.impl.ReviewServiceImpl;

import java.util.List;

@RequestMapping("/review")
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class ReviewController {
    private final ReviewServiceImpl reviewServiceIml;

    // Add a review for a product
    @PostMapping("/add")
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review addedReview = reviewServiceIml.addReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedReview);
    }

    // Get reviews for a product by product ID
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable int productId) {
        List<Review> reviews = reviewServiceIml.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }
}