package vn.edu.hcmuaf.st.SmartphoneStore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Review;
import vn.edu.hcmuaf.st.SmartphoneStore.model.User;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.ReviewRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.UserRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.service.IReviewService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements IReviewService {

    @Autowired
    private  ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public Review addReview(Review review) {
        if (review.getUser() == null) {
            throw new RuntimeException("User is null in the review.");
        }
        // Lấy userId từ đối tượng review
        int userId = review.getUser().getUserId();
        System.out.println("User ID: " + userId);

        // Truy xuất thông tin người dùng từ cơ sở dữ liệu bằng userId
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with userId: " + userId));

        Review newReview = new Review();
        if (review.getRating() != 0) {
            newReview.setRating(review.getRating());
        }
        if (review.getComment() != null) {
            newReview.setComment(review.getComment());
        }
        newReview.setProduct(review.getProduct());
        newReview.setUser(user);

        // Set thời gian tạo và cập nhật
        LocalDateTime now = LocalDateTime.now();
        review.setCreatedAt(now);
        review.setUpdatedAt(now);
        // Lưu đánh giá vào cơ sở dữ liệu
        return reviewRepository.save(newReview);
    }
    @Override
    public List<Review> getReviewsByProductId(int productId) {
        return reviewRepository.findByProduct_ProductId(productId);
    }
}