package base.api.book.service;

import base.api.book.dto.ReviewDto;
import base.api.book.entity.Review;
import base.api.book.mapper.ReviewMapper;
import base.api.book.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    public List<ReviewDto> getReviewByOwnerId (Long ownerId)  {
        List<Review> reviews = reviewRepository.findReviewByUserId(ownerId);
        return reviews.stream().map(reviewMapper::toDto).collect(Collectors.toList());
    }


    public List<ReviewDto> getReviewByListingId (Long id)  {
        List<Review> reviews = reviewRepository.findReviewByListingId(id);
        return reviews.stream().map(reviewMapper::toDto).collect(Collectors.toList());
    }

    public ReviewDto createReview (ReviewDto reviewDto) {
        Review review = reviewMapper.toEntity(reviewDto);
        review.setCreatedDate(Instant.now());
        Review createdReview = reviewRepository.save(review);
        return reviewMapper.toDto(createdReview);
    }
}
