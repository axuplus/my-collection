package com.utils.security.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("owner_company")
@Data
public class Company {
    private Long id;
    private Long userId;
    private String companyName;
    private String companyAddress;
    private Long licenseImg;
    private Integer state;
    private String createTime;
}
