package com.example.museum.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.museum.dto.CommentDTO;
import com.example.museum.model.Comments;

@Mapper
public interface CommentsMapper {
    /**
     * 获取文物所有评论
     * @param id 文物id
     * @return List<Comments>
     */
    List<Comments> getCommentByRelicId(@Param("rid") Long rid);
    /**
     * 新增评论
     * @param dto 请求数据
     * @return Boolean
     */
    Boolean addComments(CommentDTO dto);
    /**
     * 按Id查询文物名称
     * @param id 文物id
     * @return String
     */
    @Select("select Title from art where id = #{id}")
    String getRelicNameById(@Param("id")Long id);
}
