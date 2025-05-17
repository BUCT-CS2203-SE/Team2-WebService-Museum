package com.example.museum.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.museum.dto.CommentDTO;
import com.example.museum.service.CommentsService;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentsService csce;

    @PostMapping("/search")
    public ResponseEntity<Map<String,Object>> SearchComments(@RequestBody Map<String,String> info){
        Long id = Long.parseLong(info.get("id"));
        return ResponseEntity.ok(csce.getCommentsById(id));
    }
    
    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> AddComments(@RequestBody CommentDTO dto){
        System.out.println("\n\nGet Post Data: "+dto);
        return ResponseEntity.ok(Map.of("ans",csce.addComment(dto)));
    }

    @PostMapping("/mycomts")
    public ResponseEntity<List<Map<String,Object>>> getUserComments(@RequestBody Map<String,String> info){
        String uname = info.get("username");
        String rname = info.get("relicname");
        String cont = info.get("content");
        String ti = info.get("time");
        return ResponseEntity.ok(csce.getUserComments(uname, rname, cont, ti));
    }
    @PostMapping("/discomts")
    public ResponseEntity<Map<String,Object>> DelComments(@RequestBody Map<String,String> info){
        Long id = Long.parseLong(info.get("id"));
        return ResponseEntity.ok(Map.of("ans",csce.delUserCommentsById(id)));
    }
}
