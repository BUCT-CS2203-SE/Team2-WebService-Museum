package com.example.museum.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.museum.dto.RelicSearchDTO;
import com.example.museum.vo.DetailVO;
import com.example.museum.vo.RelicItemVO;

@Mapper
public interface RelicSearchMapper {
    /**
     * 查询某一一页结果数据
     * @param dto   前端数据请求头
     * @param offsize   offsize 偏移量，需手动计算
     * @return
     */
    List<RelicItemVO> SearchRelic(@Param("dto")RelicSearchDTO dto,@Param("offsize")int offsize);
    /**
     * 搜索函数对应的查询总记录数
     * @param dto   前端数据请求头
     * @return Long 返回总记录数
     */
    Long CountSearchRelicl(@Param("dto")RelicSearchDTO dto);
    /**
     * 获取某一个文物的具体信息
     * @param id 文物id
     * @return DetailVO
     */
    DetailVO getDetailInfo(@Param("id") Long id);
    /**
     * 获取图片列表(目前只含有一张)
     * @param id 文物id
     * @return List<String>
     */
    List<String> getImgUrlLById(@Param("id") Long id);
    /**
     * 判断是否收藏文物
     * @param account 用户账号名
     * @param rid    文物id·
     * @return
     */
    Boolean isFavoriteRelic(@Param("account") String account,@Param("rid") Long rid);
    /**
     * 添加收藏
     * @param uid 用户id
     * @param rid 文物id
     * @return bool
     */
    Boolean addFavRelic(@Param("uid") Long uid,@Param("rid") Long rid,@Param("time") LocalDateTime time);
    /**
     * 删除收藏
     * @param uid 用户id
     * @param rid 文物id
     * @return bool
     */
    Boolean delFavRelic(@Param("uid") Long uid,@Param("rid") Long rid);
    /**
     * 获取相关文物信息（按作家、材料、名称、类别）比分
     * @param id 文物id
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> getRelatedRelic(@Param("id") Long id);
    /**
     * 模糊查询用户的所有收藏
     * @param username 用户名具体值
     * @param relicname 文物名模糊查询，可能为null
     * @param time  截止时间，可能为null
     * @return List
     */
    List<Map<String,Object>> getUserFavRelic(@Param("un")String username,@Param("rn")String relicname,@Param("ti") String time);
    /**
     * 判断是否存在浏览历史
     * @param uid 用户id
     * @param rid 文物id
     * @return bool
     */
    Boolean HasRelicHistory(@Param("uid")Long uid,@Param("rid")Long rid);
    /**
     * 添加浏览历史
     * @param uid 用户id
     * @param rid 文物id
     * @return bool
     */
    Boolean addHistoryRelic(@Param("uid")Long uid,@Param("rid")Long rid);
    /**
     * 更新浏览时间
     * @param uid 用户id
     * @param rid 文物id
     * @return bool
     */
    Boolean updateHistoryRelic(@Param("uid")Long uid,@Param("rid")Long rid);
    /**
     * 删除记录
     * @param uid 用户id
     * @param rid 文物id
     * @return bool
     */
    @Delete("delete from history_relic where uid = #{uid} and rid = #{rid}")
    Boolean delHistoryRelic(@Param("uid")Long uid,@Param("rid")Long rid);
    /**
     * 模糊查找用户记录
     * @param username 用户名
     * @param relicname 文物名
     * @param time 截止时间
     * @return list
     */
    List<Map<String,Object>> getUserHistory(@Param("un")String username,@Param("rn")String relicname,@Param("ti")String time);
}
