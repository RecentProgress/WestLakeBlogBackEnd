package com.west.lake.blog.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.west.lake.blog.annotation.EnumStatus;
import com.west.lake.blog.foundation.exception.ErrorMessage;
import com.west.lake.blog.model.entity.enums.UserSexEnum;
import com.west.lake.blog.model.entity.enums.UserStatusEnum;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;

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
    @Size(max = 8, min = 2, message = "{01001.user.name.length.illegal}")
    private String userName;

    /**
     * 密码
     */
    @JSONField(serialize = false)
    private String password;

    @Size(min = 11, max = 11, message = "{01006.mobile.length.illegal}")
    private String mobile;

    /**
     * 邮件地址
     */
    @Email(message = "{01002.email.format.error}")
    @Size(max = 50, message = "{01007.email.too.long}")
    private String email;

    /**
     * 用户状态
     */
    @EnumStatus(value = UserStatusEnum.class, message = ErrorMessage.LogicErrorMessage.USER_STATUS_ILLEGAL)
    private int status;

    /**
     * 用户性别
     */
    @EnumStatus(value = UserSexEnum.class, message = ErrorMessage.LogicErrorMessage.USER_SEX_ILLEGAL)
    private int sex;

    /**
     * 生日
     */
    @Past
    private Date birthday;

    /**
     * 简介
     */
    @Size(max = 255, message = "{01008.user.desc.too.long}")
    private String desc = "这个家伙很懒...";

    /**
     * 当前登录时间
     */
    @JSONField(serialize = false)
    private Timestamp currentLoginTime;

    /**
     * 上次登陆时间
     */
    private Timestamp lastLoginTime;

    /**
     * 登陆系统次数
     */
    private int loginTimes;

    /**
     * 预注册时间
     */
    private Timestamp preRegisterTime;

    public User(String id) {
        this.setId(id);
    }

}
