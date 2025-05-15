package com.example.museum.service;

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
}
