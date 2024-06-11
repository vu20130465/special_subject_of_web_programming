package vn.edu.hcmuaf.st.SmartphoneStore.service;

import vn.edu.hcmuaf.st.SmartphoneStore.model.Review;

import java.util.List;

public interface IReviewService {
    Review addReview(Review review);
    List<Review> getReviewsByProductId(int productId);
}