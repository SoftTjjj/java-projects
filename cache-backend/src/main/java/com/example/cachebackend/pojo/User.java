package com.example.cachebackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_user")
public class User {
    @TableId(value="id",type= IdType.AUTO)
    private Long id;
    private String phone;
    private String password;
    private String nickname;
    private Date createTime;
    private String avatar;

}
