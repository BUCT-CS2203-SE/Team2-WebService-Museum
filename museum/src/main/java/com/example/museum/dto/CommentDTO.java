package com.example.museum.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    /**文物id */
    private Long id;
    /**用户名 */
    private String username;
    /**评论内容 */
    private String contents;
    /**评论时间 */
    private LocalDateTime datetime;
}
