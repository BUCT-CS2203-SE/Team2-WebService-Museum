package com.example.museum.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
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
    /**
     * 获取用户的全部评论
     * @param username 用户名，精确
     * @param relicname 文物名 可模糊
     * @param content 评论内容 可模糊
     * @param time 截止时间 可空
     * @return
     */
    List<Map<String,Object>> getUserComments(@Param("un")String username,@Param("rn")String relicname,
        @Param("con")String content,@Param("ti")String time);
    /**
     * 按评论id删除
     * @param id 评论id
     * @return bool
     */
    @Delete("delete from comment_relic where id =#{id}")
    Boolean delByCommentsId(@Param("id") Long id);
}
