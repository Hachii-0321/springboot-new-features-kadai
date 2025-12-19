package com.example.samuraitravel.form;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewEditForm {

    private Integer id;

    private Integer reviewRating;

    private String reviewContent;
}