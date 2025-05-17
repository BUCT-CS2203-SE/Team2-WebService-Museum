package com.example.museum.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.museum.dto.RelicSearchDTO;
import com.example.museum.mapper.RelicSearchMapper;
import com.example.museum.service.MyUserService;
import com.example.museum.service.RelicSearchService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class RelicSearchServiceImpl implements RelicSearchService {
    @Autowired
    private RelicSearchMapper rsmp;
    @Autowired
    private MyUserService muce;

    @Override
    public Map<String,Object> searchRelic(RelicSearchDTO dto){
        Map<String,Object> ans = new HashMap<>();
        ans.put("total", rsmp.CountSearchRelicl(dto));
        int of = (dto.getPagesize() * (dto.getPageindex() - 1));
        ans.put("data", rsmp.SearchRelic(dto, of));
        return ans;
    }
    @Override
    public Map<String,Object> getDetailInfo(Long rid,String username){
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> ans = mapper.convertValue(rsmp.getDetailInfo(rid) , new TypeReference<>(){});
        ans.put("favorited", rsmp.isFavoriteRelic(username, rid));
        ans.put("images", rsmp.getImgUrlLById(rid));
        ans.put("related", rsmp.getRelatedRelic(rid));
        return ans;
    }
    @Override
    public Boolean changeFavRelic(Long rid,String username, Boolean fav){
        Long uid = muce.getIdByAccount(username);
        Boolean has = rsmp.isFavoriteRelic(username, rid);
        if(has){ //已收藏
            if(!fav) rsmp.delFavRelic(uid, rid);
        }else{
            if(fav) rsmp.addFavRelic(uid, rid, LocalDateTime.now());
        }
        return true;
    }
    @Override
    public List<Map<String,Object>> searchMyFavRelic(String username,String relicname,String time){
        return rsmp.getUserFavRelic(username, relicname, time);
    }
    @Override
    public Boolean changeHistory(String username,Long rid,Boolean del){
        Long uid = muce.getIdByAccount(username);
        Boolean has = rsmp.HasRelicHistory(uid, rid);
        if(has){ //不存在历史
            if(del) rsmp.delHistoryRelic(uid, rid); //删
            else rsmp.updateHistoryRelic(uid, rid); //不删，更新
        }else{ //存在历史
            if(!del) rsmp.addHistoryRelic(uid, rid); //不删，新增
        }
        return true;
    }
    @Override
    public List<Map<String,Object>> getUserHistory(String username,String relicname,String time){
        return rsmp.getUserHistory(username, relicname, time);
    }
}
