package com.example.museum.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


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
        SELECT 
        id, ImgUrl, Title, Dynasty,REGEXP_SUBSTR(Dynasty, '（[^）]+）', 1, 1) AS date_range
        FROM (
            SELECT id, ImgUrl, Title, Dynasty,
            CASE 
                WHEN Dynasty LIKE '%公元前%' THEN -CAST(REGEXP_SUBSTR(Dynasty, '[0-9]+') AS UNSIGNED)
                ELSE CAST(REGEXP_SUBSTR(Dynasty, '[0-9]+') AS UNSIGNED)
            END AS first_number
            FROM art
        WHERE id IN (
            SELECT MIN(id)
            FROM art
            GROUP BY Dynasty
            )
        ) AS subquery
        ORDER BY first_number;
    """)
    List<Map<String, Object>> findTimelineData();

}
