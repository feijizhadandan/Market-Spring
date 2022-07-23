package com.zhen.framework.security.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户类
 */

@Data
@TableName("sys_user")
public class User implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String userName;
    private String nickName;
    private String userType;
    private String email;
    private String phone;
    private Character sex;
    private String password;
    private Character status;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Character delFlag;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    @Version
    private Long version;
}
