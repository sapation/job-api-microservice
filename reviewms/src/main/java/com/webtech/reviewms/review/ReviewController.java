package com.webtech.reviewms.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId)
    {
        return ResponseEntity.ok(reviewService.getAllReviews(companyId));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId)
    {
        Review review = reviewService.getReviewById(reviewId);
        return review != null ? new ResponseEntity<>(review, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createReview(@RequestParam Long companyId, @RequestBody Review review)
    {
        boolean isReviewSaved = reviewService.createReview(companyId, review);
        return isReviewSaved ?
                new ResponseEntity<>("Review added Successfully", HttpStatus.CREATED) :
                new ResponseEntity<>("Review not Saved", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId,
                                               @RequestBody Review review)
    {
        boolean isReviewUpdated = reviewService.updateReview(reviewId, review);
        return isReviewUpdated ?
                new ResponseEntity<>("Review Updated Successfully", HttpStatus.OK) :
                new ResponseEntity<>("Review Not Found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId)
    {
        boolean isReviewDeleted = reviewService.deleteReview(reviewId);
        return isReviewDeleted ?
                new ResponseEntity<>("Review Deleted Successfully", HttpStatus.OK) :
                new ResponseEntity<>("Review Not Found", HttpStatus.NOT_FOUND);

    }
}
