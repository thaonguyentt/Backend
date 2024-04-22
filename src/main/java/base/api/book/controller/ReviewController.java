package base.api.book.controller;

import base.api.book.dto.ReviewDto;
import base.api.book.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("search/byUserId/{ownerId}")
    public List<ReviewDto> getReviewByOwnerId (@PathVariable Long ownerId) {
        return reviewService.getReviewByOwnerId(ownerId);
    }

    @PostMapping
    public ReviewDto createReview (@RequestBody ReviewDto reviewDto) {
        return reviewService.createReview(reviewDto);
    }
}
