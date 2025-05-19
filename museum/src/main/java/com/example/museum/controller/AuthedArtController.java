package com.example.museum.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.museum.service.RelicSearchService;

@RestController
@RequestMapping("/AuthRelic")
public class AuthedArtController {
    
    @Autowired
    private RelicSearchService rsce;

    @PostMapping("/relic_detail")
    public ResponseEntity<Map<String,Object>> getdetail(@RequestBody Map<String,String> info){
        System.out.println("Get Request: " +info);
        Long rid = Long.parseLong(info.get("id"));
        String username = info.get("username");
        Map<String,Object> ans = rsce.getDetailInfo(rid, username);
        return ResponseEntity.ok(ans);
    }
    @PostMapping("/relic_isfav")
    public ResponseEntity<Map<String,Object>> changeFav(@RequestBody Map<String,String> info){
        System.out.println("Get Request: " +info); 
        Long rid = Long.parseLong(info.get("id"));
        String username = info.get("username");
        Boolean fav = Boolean.parseBoolean(info.get("fav"));
        return ResponseEntity.ok(Map.of("ans",rsce.changeFavRelic(rid, username, fav)));
    }
    @PostMapping("/rehistory")
    public ResponseEntity<Map<String,Object>> addRelicHistory(@RequestBody Map<String,String> info){
        Long rid = Long.parseLong(info.get("rid"));
        String username = info.get("username");
        return ResponseEntity.ok(Map.of("ans",rsce.changeHistory(username, rid, false)));
    }
}
