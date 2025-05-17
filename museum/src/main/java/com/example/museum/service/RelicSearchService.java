package com.example.museum.service;

import java.util.List;
import java.util.Map;

import com.example.museum.dto.RelicSearchDTO;

public interface RelicSearchService {
    /**
     * 文物搜索
     * @param dto 前端请求数据包
     * @return Map<String,Object>
     */
    Map<String,Object> searchRelic(RelicSearchDTO dto);
    /**
     * 文物详情
     * @param rid   文物id
     * @param username 用户名
     * @return Map<String,Object>
     */
    Map<String,Object> getDetailInfo(Long rid,String username);
    /**
     * 修改收藏信息
     * @param rid 文物id
     * @param username 用户名
     * @param fav 是否收藏
     * @return Boolean
     */
    Boolean changeFavRelic(Long rid,String username, Boolean fav);
    /**
     * 模糊查询用户收藏信息
     * @param username 用户名
     * @param relicname 文物名
     * @param time 截止时间
     * @return List
     */
    List<Map<String,Object>> searchMyFavRelic(String username,String relicname,String time);
    /**
     * 改变浏览记录状态
     * @param username 用户名 
     * @param rid 文物id
     * @param del 删除/更新
     * @return bool
     */
    Boolean changeHistory(String username,Long rid,Boolean del);
    /**
     * 模糊查找用户历史记录
     * @param username 用户名
     * @param relicname 文物名
     * @param time 截止时间
     * @return list
     */
    List<Map<String,Object>> getUserHistory(String username,String relicname,String time);
}
