package com.west.lake.blog.model.entity;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 实体基类
 *
 * @author futao
 * Created on 2018/9/22-15:17.
 */
//@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 7360778825666258407L;
    /**
     * 唯一主键id
     */
    private String id;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 最后修改时间
     */
    private Timestamp lastModifyTime;

}
