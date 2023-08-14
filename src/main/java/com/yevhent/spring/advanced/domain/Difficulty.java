package com.yevhent.spring.advanced.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Difficulty {
    EASY("Easy"),
    MEDIUM("Medium"),
    DIFFICULT("Difficult"),
    VARIES("Varies");

    private final String label;

    public static Difficulty findByLabel(String byLabel) {
        for (Difficulty r : Difficulty.values()) {
            if (r.label.equalsIgnoreCase(byLabel))
                return r;
        }
        return null;
    }
}