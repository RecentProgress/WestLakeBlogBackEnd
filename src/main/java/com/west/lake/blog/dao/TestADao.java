package com.west.lake.blog.dao;

import com.west.lake.blog.model.entity.TestA;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @author futao
 * @date 2019/12/19.
 */
public interface TestADao {

    @Insert("INSERT INTO wlb_test_a(id,data,create_date_time,update_date_time) VALUES(#{id},#{data},#{createDateTime},#{updateDateTime})")
    void insert(TestA testA);

    @Select("SELECT * FROM wlb_test_a WHERE id=#{id}")
    TestA selectById(String id);
}
