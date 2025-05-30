package com.example.museum.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.museum.dto.ArtDTO;

@Mapper
public interface ArtMapper {

    // 获取所有文物类别（去重）
    @Select("SELECT DISTINCT Classifications FROM Arts_withimg WHERE Classifications IS NOT NULL AND Classifications != ''")
    List<String> findAllClassifications();

    // 获取所有朝代（去重，去空）
    @Select("SELECT DISTINCT Dynasty FROM Arts_withimg WHERE Dynasty IS NOT NULL AND Dynasty != ''")
    List<String> findAllDynasty();

    // 获取所有博物馆（去重）?
    @Select("SELECT DISTINCT Museum FROM Arts_withimg")
    List<String> findAllMuseums();

    // 获取时间轴数据：每个朝代取一件文物
    @Select("""
        SELECT 
            CAST(a.id AS CHAR) AS id,
            a.ImgUrl AS imgUrl,
            a.Title AS title,
            a.Dynasty AS dynasty,
            CONCAT('约', a.main_start, '年-', a.main_end, '年') AS dateRange
        FROM Arts_withimg a
        INNER JOIN (
            SELECT MIN(id) AS id
            FROM Arts_withimg
            WHERE Dynasty IS NOT NULL AND Dynasty != '' AND main_start IS NOT NULL
            GROUP BY Dynasty, main_start, main_end
            ) AS t ON a.id = t.id
        WHERE 
        a.Dynasty IS NOT NULL 
        AND a.Dynasty != '' 
        AND a.main_start IS NOT NULL
        ORDER BY a.main_start ASC;


    """)
    List<ArtDTO> findTimelineData();
    


    @Select("""
        SELECT 
        CAST(a.id AS CHAR) AS id,
        a.ImgUrl AS src,
        a.Title AS title
        FROM Arts_withimg a
        WHERE a.Dynasty IS NOT NULL AND a.Dynasty != ''
        AND a.main_start <= #{end}
        AND a.main_end >= #{sta}
    """)
    List<Map<String, Object>> findArtInDateRange(@Param("sta") int sta, @Param("end") int end);
}
