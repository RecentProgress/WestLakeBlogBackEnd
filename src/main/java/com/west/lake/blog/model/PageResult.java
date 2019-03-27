package com.west.lake.blog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 分页
 *
 * @author futao
 * Created on 2019-03-27.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    /**
     * 总数
     */
    private int total;

    /**
     * 数据
     */
    private List<T> list;

}
