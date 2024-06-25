package vn.edu.hcmuaf.st.SmartphoneStore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.ReviewDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.response.ReviewResponse;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Review;
import vn.edu.hcmuaf.st.SmartphoneStore.model.User;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.UserRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.service.impl.ReviewServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/review")
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class ReviewController {
    private final ReviewServiceImpl reviewServiceIml;
    private final UserRepository userRepository;
    // Add a review for a product
    @PostMapping("/add")
    public ResponseEntity<ReviewResponse> addReview(@RequestBody ReviewDTO reviewDTO) {

        Review addedReview = reviewServiceIml.mapFromReviewDTOToReview(reviewDTO);
        Review result = reviewServiceIml.addReview(addedReview);
        return ResponseEntity.ok(ReviewResponse.builder().message("Review added").status(HttpStatus.OK.value()).build());
    }

    // Get reviews for a product by product ID
        @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProductId(@PathVariable int productId) {
        List<Review> reivews = reviewServiceIml.getReviewsByProductId(productId);
        List<ReviewDTO> result = new ArrayList<>();
        for(Review review : reivews) {
            User user = userRepository.findByUserId(review.getUser().getUserId()).orElseThrow(() -> new RuntimeException());
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setIdUser(user.getUserId());
            reviewDTO.setComment(review.getComment());
            reviewDTO.setRating(review.getRating());
            reviewDTO.setIdProduct(productId);
            reviewDTO.setFullName(user.getFullName());
            reviewDTO.setCreatedAt(review.getCreatedAt());
            reviewDTO.setUpdatedAt(review.getUpdatedAt());
            result.add(reviewDTO);
        }

        return ResponseEntity.ok(result);
    }
}