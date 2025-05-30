package com.example.museum.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**文物搜索响应结构体 */
public class RelicItemVO {
    /**文物id */
    private Long id;
    /**文物图片位置 */
    private String src;
    /**文物标题 */
    private String title;
}
