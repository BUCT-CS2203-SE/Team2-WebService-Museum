package com.example.museum.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.museum.dto.ArtDTO;
import com.example.museum.dto.RelicSearchDTO;
import com.example.museum.mapper.ArtMapper;
import com.example.museum.service.RelicSearchService;

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
        return List.of("大英博物馆");
    }

    @GetMapping("/timeline")
    public List<ArtDTO> getTimelineData() {
        return artMapper.findTimelineData();
    }

    @Autowired
    private RelicSearchService rsce;

    @PostMapping("/relic_search")
    public ResponseEntity<Map<String,Object>> getSer(@RequestBody RelicSearchDTO dto){
        System.out.println("正在请求： "+dto);
        return ResponseEntity.ok(rsce.searchRelic(dto));
    }
    @PostMapping("/relic_detail")
    public ResponseEntity<Map<String,Object>> getdetail(@RequestBody Map<String,String> info){
        System.out.println("Get Request: " +info);
        Long rid = Long.parseLong(info.get("id"));
        String username = info.get("username");
        Map<String,Object> ans = rsce.getDetailInfo(rid, username);
        return ResponseEntity.ok(ans);
    }
    @PostMapping("relic_isfav")
    public ResponseEntity<Map<String,Object>> changeFav(@RequestBody Map<String,String> info){
        System.out.println("Get Request: " +info); 
        Long rid = Long.parseLong(info.get("id"));
        String username = info.get("username");
        Boolean fav = Boolean.parseBoolean(info.get("fav"));
        return ResponseEntity.ok(Map.of("ans",rsce.changeFavRelic(rid, username, fav)));
    }
}
