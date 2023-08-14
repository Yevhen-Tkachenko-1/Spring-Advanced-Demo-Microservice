package com.yevhent.spring.advanced.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DifficultyConverter implements AttributeConverter<Difficulty, String> {

    @Override
    public String convertToDatabaseColumn(Difficulty attribute) {
        return attribute.getLabel();
    }

    @Override
    public Difficulty convertToEntityAttribute(String dbData) {
        return Difficulty.findByLabel(dbData);
    }
}