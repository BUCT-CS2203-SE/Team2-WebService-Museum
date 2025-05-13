package com.example.museum.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;

import com.example.museum.mapper.ArtMapper;
import com.example.museum.model.Art;
@GetMapping("/relic")
public class ArtController {
    private final ArtMapper artMapper;

    public ArtController(ArtMapper artMapper) {
        this.artMapper = artMapper;
    }

    @GetMapping("/getWenWu")
    public List<Art> getAll() {
        return artMapper.findAll();
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
