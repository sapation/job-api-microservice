package com.webtech.reviewms.review.ReviewServiceImpl;

import com.webtech.reviewms.review.Review;
import com.webtech.reviewms.review.ReviewRepository;
import com.webtech.reviewms.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository)
    {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public Review getReviewById(Long reviewId)
    {
        return  reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean createReview(Long companyId, Review review)
    {
        if (companyId != null && review != null) {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateReview(Long reviewId, Review review)
    {
        Review reviewToUpdate = reviewRepository.findById(reviewId).orElse(null);
        if (reviewToUpdate != null)
        {
            reviewToUpdate.setCompanyId(review.getCompanyId());
            reviewToUpdate.setDescription(review.getDescription());
            reviewToUpdate.setTitle(review.getTitle());
            reviewToUpdate.setRating(review.getRating());
            reviewRepository.save(reviewToUpdate);
            return  true;
        }
        return false;
    }

    @Override
    public boolean deleteReview(Long reviewId)
    {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (reviewRepository.existsById(reviewId) && review != null)
        {
            reviewRepository.delete(review);
            return true;
        }
        return false;
    }
}
