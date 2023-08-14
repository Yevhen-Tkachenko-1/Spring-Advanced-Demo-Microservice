package com.yevhent.spring.advanced.service;

import com.yevhent.spring.advanced.domain.Difficulty;
import com.yevhent.spring.advanced.repo.TourPackageRepository;
import com.yevhent.spring.advanced.repo.TourRepository;
import com.yevhent.spring.advanced.domain.Region;
import com.yevhent.spring.advanced.domain.Tour;
import com.yevhent.spring.advanced.domain.TourPackage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TourService {

    private final TourRepository tourRepository;
    private final TourPackageRepository tourPackageRepository;

    public Tour createTour(String title, String description, String blurb, Integer price,
                           String duration, String bullets,
                           String keywords, String tourPackageName, Difficulty difficulty, Region region) {
        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName).orElseThrow(() ->
                new RuntimeException("Tour package does not exist: " + tourPackageName));

        return tourRepository.save(new Tour(title, description, blurb, price, duration,
                bullets, keywords, tourPackage, difficulty, region));
    }

    public long total() {
        return tourRepository.count();
    }

}