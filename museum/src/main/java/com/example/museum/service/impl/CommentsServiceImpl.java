package com.example.museum.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.museum.dto.CommentDTO;
import com.example.museum.mapper.CommentsMapper;
import com.example.museum.service.CommentsService;

@Service
@Transactional
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsMapper cmmp;

    @Override
    public Map<String,Object> getCommentsById(Long rid){
        Map<String,Object> ans = new HashMap<>();
        ans.put("name", cmmp.getRelicNameById(rid));
        ans.put("comments", cmmp.getCommentByRelicId(rid));
        return ans;
    }
    @Override
    public Boolean addComment(CommentDTO dto){
        return cmmp.addComments(dto);
    }
    @Override
    public List<Map<String,Object>> getUserComments(String uname,String rname,String content,String time){
        return cmmp.getUserComments(uname,rname,content,time);
    }
    @Override
    public Boolean delUserCommentsById(Long id){
        return cmmp.delByCommentsId(id);
    }
}
