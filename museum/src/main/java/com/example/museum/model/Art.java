package com.example.museum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Art {
    private Long id;
    private String Classifications;
    private String Artist;
    private String Description;
    private String Credit;
    private String Materials;
    private String Dimensions;
    private String Dynasty;
    private String Title;
    private String ImgUrl;
    private String Museum;
    private String main_start;
    private String main_end;

}
