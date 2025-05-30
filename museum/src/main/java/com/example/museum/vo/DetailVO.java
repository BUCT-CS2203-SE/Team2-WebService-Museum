package com.example.museum.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVO {
    /**文物名 */
    private String name;
    /**文物类型 */
    private String type;
    /**文物题词 */
    private String credit;
    /**文物时代 */
    private String era;
    /**文物作家 */
    private String atrist;
    /**文物规模 */
    private String scale;
    /**文物描述 */
    private String description;
    /**文物材料 */
    private String material;
    /**来源 */
    private String placeori;
}
