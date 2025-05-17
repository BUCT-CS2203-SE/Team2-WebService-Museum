package com.example.museum.service;

import java.util.List;
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
    /**
     * 查找用户评论信息
     * @param uname 用户名 精确
     * @param rname 文物名 不定
     * @param content 内容 不定
     * @param time 截止时间 不定
     * @return List
     */
    List<Map<String,Object>> getUserComments(String uname,String rname,String content,String time);
    /**
     * 按评论id删除评论
     * @param id 评论id
     * @return bool
     */
    Boolean delUserCommentsById(Long id);
}
