package com.example.museum.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.museum.mapper.ArtMapper;

@RestController
@RequestMapping("/test")
@ResponseBody
public class ArtController {
    private final ArtMapper artMapper;

    public ArtController(ArtMapper artMapper) {
        this.artMapper = artMapper;
    }


    @GetMapping("/relic_types")
    public List<String> getAllClassifications() {
        return artMapper.findAllClassifications();
    }

    @GetMapping("/relic_times")
    public List<String> getAllDynasty() {
        return artMapper.findAllDynasty();
    }

    @GetMapping("/relic_museums")
    public List<String> getAllMuseums() {
        return artMapper.findAllMuseums();
    }

    @GetMapping("/timeline")
    public List<Map<String, Object>> getTimelineData() {
        return artMapper.findTimelineData();
    }
}
