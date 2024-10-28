package base.api.book.controller;

import base.api.book.dto.ReviewDto;
import base.api.book.service.ReviewService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8082", "https://the-flying-bookstore.vercel.app","https://the-flying-bookstore-dashboard-fe.vercel.app"})
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

    @GetMapping("search/byListingId/{id}")
    public List<ReviewDto> getReviewByListingId (@PathVariable Long id) {
        return reviewService.getReviewByListingId(id);
    }



    @PostMapping
    public ReviewDto createReview (@RequestBody ReviewDto reviewDto) {
        return reviewService.createReview(reviewDto);
    }
}
