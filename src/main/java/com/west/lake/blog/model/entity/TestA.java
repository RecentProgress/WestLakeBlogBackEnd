package com.west.lake.blog.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author futao
 * @date 2019/12/19.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestA {
    private String id;
    private String data;
    private Date createDateTime;
    private Date updateDateTime;
}
