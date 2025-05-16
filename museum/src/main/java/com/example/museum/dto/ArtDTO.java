package com.example.museum.dto;

import lombok.Data;

@Data
public class ArtDTO {
    private Long id;
    private String imgUrl;
    private String title;
    private String dynasty;
    private String dateRange;

    public ArtDTO(Long id, String imgUrl, String title, String dynasty, String dateRange) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.title = title;
        this.dynasty = dynasty;
        this.dateRange = dateRange;
    }
}
