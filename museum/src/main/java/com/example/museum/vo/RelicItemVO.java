package com.example.museum.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**文物搜索响应结构体 */
public class RelicItemVO {
    private Long id;
    private String src;
    private String title;
}
