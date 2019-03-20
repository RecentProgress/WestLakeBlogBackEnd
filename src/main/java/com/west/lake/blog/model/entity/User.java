package com.west.lake.blog.model.entity;

import lombok.*;

/**
 * @author futao
 * Created on 2019-03-20.
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    private String userName;
}
