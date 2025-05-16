package com.example.museum.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.museum.dto.ArtDTO;

@Mapper
public interface ArtMapper {

    // 获取所有文物类别（去重）
    @Select("SELECT DISTINCT Classifications FROM art")
    List<String> findAllClassifications();

    // 获取所有朝代（去重，去空）
    @Select("SELECT DISTINCT Dynasty FROM art WHERE Dynasty IS NOT NULL AND Dynasty != ''")
    List<String> findAllDynasty();

    // 获取所有博物馆（去重）?
    @Select("SELECT DISTINCT location FROM art")
    List<String> findAllMuseums();

    // 获取时间轴数据：每个朝代取一件文物
    @Select("""
        SELECT a.id,a.ImgUrl,a.Title,a.Dynasty,
        SUBSTRING(
            a.Dynasty,
            LOCATE('（', a.Dynasty) + 1,
            LOCATE('）', a.Dynasty) - LOCATE('（', a.Dynasty) - 1
        ) AS date_range
        FROM art a
        INNER JOIN (
            SELECT MIN(id) AS id
            FROM art
            WHERE Dynasty IS NOT NULL AND Dynasty != ''
            GROUP BY Dynasty
            ) AS t ON a.id = t.id
            WHERE a.Dynasty IS NOT NULL AND a.Dynasty != ''
        ORDER BY
            CASE
            WHEN a.Dynasty LIKE '%公元前%' THEN 
                -CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(
                    SUBSTRING(
                        a.Dynasty,
                        LOCATE('（', a.Dynasty) + 1,
                        LOCATE('）', a.Dynasty) - LOCATE('（', a.Dynasty) - 1
                    ),
                    '-', 1), '前', -1
                ) AS UNSIGNED)
            ELSE 
                CAST(SUBSTRING_INDEX(
                    SUBSTRING(
                        a.Dynasty,
                        LOCATE('（', a.Dynasty) + 1,
                        LOCATE('）', a.Dynasty) - LOCATE('（', a.Dynasty) - 1
                    ),
                    '-', 1) AS UNSIGNED)
        END;

    """)
    List<ArtDTO> findTimelineData();
    
}
