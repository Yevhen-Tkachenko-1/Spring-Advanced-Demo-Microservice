package com.yevhent.spring.advanced.service;

import com.yevhent.spring.advanced.domain.Tour;
import com.yevhent.spring.advanced.repo.TourRatingRepository;
import com.yevhent.spring.advanced.repo.TourRepository;
import com.yevhent.spring.advanced.domain.TourRating;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
@Transactional
@RequiredArgsConstructor
public class TourRatingService {

    private final TourRatingRepository tourRatingRepository;
    private final TourRepository tourRepository;

    public void createNew(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
        tourRatingRepository.save(new TourRating(verifyTour(tourId), customerId, score, comment));
    }

    public Optional<TourRating> lookupRatingById(int id) {
        return tourRatingRepository.findById(id);
    }

    public List<TourRating> lookupAll() {
        return tourRatingRepository.findAll();
    }

    public Page<TourRating> lookupRatings(int tourId, Pageable pageable) throws NoSuchElementException {
        return tourRatingRepository.findByTourId(verifyTour(tourId).getId(), pageable);
    }

    public TourRating update(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
        TourRating rating = verifyTourRating(tourId, customerId);
        rating.setScore(score);
        rating.setComment(comment);
        return tourRatingRepository.save(rating);
    }

    public TourRating updateSome(int tourId, Integer customerId, Integer score, String comment)
            throws NoSuchElementException {
        TourRating rating = verifyTourRating(tourId, customerId);
        if (score != null) {
            rating.setScore(score);
        }
        if (comment != null) {
            rating.setComment(comment);
        }
        return tourRatingRepository.save(rating);
    }

    public void delete(int tourId, Integer customerId) throws NoSuchElementException {
        TourRating rating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(rating);
    }

    public Double getAverageScore(int tourId) throws NoSuchElementException {
        List<TourRating> ratings = tourRatingRepository.findByTourId(verifyTour(tourId).getId());
        OptionalDouble average = ratings.stream().mapToInt((rating) -> rating.getScore()).average();
        return average.isPresent() ? average.getAsDouble() : null;
    }

    public void rateMany(int tourId, int score, Integer[] customers) {
        tourRepository.findById(tourId).ifPresent(tour -> {
            for (Integer c : customers) {
                tourRatingRepository.save(new TourRating(tour, c, score));
            }
        });
    }

    private Tour verifyTour(int tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId).orElseThrow(() ->
                new NoSuchElementException("Tour does not exist " + tourId)
        );
    }

    private TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId).orElseThrow(() ->
                new NoSuchElementException("Tour-Rating pair for request("
                        + tourId + " for customer" + customerId));
    }

}