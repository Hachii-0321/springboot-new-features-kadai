package com.example.samuraitravel.form;

import lombok.Data;

@Data
public class ReviewRegisterForm {
    private Integer houseId;

    private Integer userId;

    private Integer reviewRating;

    private String reviewContent;

}
