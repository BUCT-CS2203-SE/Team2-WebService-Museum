package com.example.museum.service;

import java.util.Map;

import com.example.museum.dto.CommentDTO;

public interface CommentsService {
    /**
     * 获取评论区数据
     * @param rid 文物id
     * @return Map<String,Object>
     */
    Map<String,Object> getCommentsById(Long rid);
    /**
     * 新增评论
     * @param dto 请求数据结构
     * @return Boolean
     */
    Boolean addComment(CommentDTO dto);
}
