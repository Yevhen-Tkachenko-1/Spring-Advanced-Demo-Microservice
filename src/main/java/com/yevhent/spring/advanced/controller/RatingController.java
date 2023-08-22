package com.yevhent.spring.advanced.controller;

import com.yevhent.spring.advanced.service.TourRatingService;
import com.yevhent.spring.advanced.web.RatingAssembler;
import com.yevhent.spring.advanced.web.RatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/ratings")
public class RatingController {

    private final TourRatingService tourRatingService;

    private final RatingAssembler assembler;

    @Autowired
    public RatingController(TourRatingService tourRatingService, RatingAssembler assembler) {
        this.tourRatingService = tourRatingService;
        this.assembler = assembler;
    }

    @GetMapping
    public List<RatingDto> getAll() {
        return tourRatingService.lookupAll().stream().map(tr -> assembler.toModel(tr)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RatingDto getRating(@PathVariable("id") Integer id) {
        return assembler.toModel(tourRatingService.lookupRatingById(id)
                .orElseThrow(() -> new NoSuchElementException("Rating " + id + " not found"))
        );
    }


    /**
     * Exception handler if NoSuchElementException is thrown in this Controller
     *
     * @param ex exception
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();
    }
}