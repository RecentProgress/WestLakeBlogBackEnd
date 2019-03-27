package com.west.lake.blog.model.entity;

import lombok.Data;

/**
 * 标签
 *
 * @author futao
 * Created on 2019-03-23.
 */
@Data
public class Tag extends BaseEntity {

    private String tagName;

    private String type;
}
