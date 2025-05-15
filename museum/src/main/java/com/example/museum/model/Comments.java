package com.example.museum.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
    /**评论id */
    private Long id;
    /**发布人 */
    private String author;
    /**头像url */
    private String avatar;
    /**评论内容 */
    private String content;
    /**发布时间 */
    private LocalDateTime datetime;
}
