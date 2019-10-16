package com.west.lake.blog.model.entity;

import com.west.lake.blog.model.entity.enums.TagType;
import lombok.*;

import javax.validation.constraints.Size;

/**
 * 标签
 *
 * @author futao
 * Created on 2019-03-23.
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends BaseEntity {

    /**
     * 标签名称
     */
    @Size(min = 1, max = 10)
    private String tagName;

    /**
     * 标签类型
     */
    private int type;
}
