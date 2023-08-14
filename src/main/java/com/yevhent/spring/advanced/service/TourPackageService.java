package com.yevhent.spring.advanced.service;

import com.yevhent.spring.advanced.repo.TourPackageRepository;
import com.yevhent.spring.advanced.domain.TourPackage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TourPackageService {

    private final TourPackageRepository tourPackageRepository;

    public TourPackage createTourPackage(String code, String name) {
        return !tourPackageRepository.existsById(code) ?
                tourPackageRepository.save(new TourPackage(code, name)) :
                null;

    }

    public Iterable<TourPackage> lookup() {
        return tourPackageRepository.findAll();
    }

    public long total() {
        return tourPackageRepository.count();
    }

}