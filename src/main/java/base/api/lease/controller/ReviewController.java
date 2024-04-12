package base.api.lease.controller;

import base.api.lease.dto.ReviewDto;
import base.api.lease.service.ReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{ownerId}")
    public List<ReviewDto> getReviewByOwnerId (@PathVariable Long ownerId) {
        return reviewService.getReviewByOwnerId(ownerId);
    }
}
