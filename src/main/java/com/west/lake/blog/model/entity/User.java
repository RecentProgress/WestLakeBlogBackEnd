package com.west.lake.blog.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.west.lake.blog.annotation.EnumStatus;
import com.west.lake.blog.model.entity.enums.UserSexEnum;
import com.west.lake.blog.model.entity.enums.UserStatusEnum;
import lombok.*;

import javax.validation.constraints.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 用户
 *
 * @author futao
 * Created on 2019-03-20.
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    /**
     * 用户名
     */
    @Min(value = 2, message = "{01001.user.name.length.illegal}")
    @Max(value = 8, message = "{01001.user.name.length.illegal}")
    private String userName;

    /**
     * 密码
     */
    @JSONField(serialize = false)
    private String password;

    @Min(value = 11, message = "{01006.mobile.length.illegal}")
    @Max(value = 11, message = "{01006.mobile.length.illegal}")
    private String mobile;

    /**
     * 邮件地址
     */
    @Email(message = "{email.format.error}")
    @Max(value = 50, message = "{01007.email.too.long}")
    private String email;

    /**
     * 用户状态
     */
    @EnumStatus(UserStatusEnum.class)
    private int status;

    /**
     * 用户性别
     */
    @EnumStatus(UserSexEnum.class)
    private int sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 简介
     */
    @Max(value = 255, message = "{01008.user.desc.too.long}")
    private String desc = "这个家伙很懒...";

    /**
     * 上次登陆时间
     */
    private Timestamp lastLoginTime;

    /**
     * 登陆系统次数
     */
    private int loginTimes;
}
