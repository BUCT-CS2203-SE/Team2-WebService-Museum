package com.example.museum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**文物搜索请求结构体 */
public class RelicSearchDTO {
    private String type;        // 文物类型
    private String time;        // 文物时代
    private String museum;      // 博物馆名称
    private Integer sortby;     // 排序方式：null/0=按id, 1=名称, 2=年代
    private String name;        // 按文物名称模糊查询
    private String artist;      // 作家名称
    private Integer pageindex;  // 当前页码
    private Integer pagesize;   // 每页数量
}
