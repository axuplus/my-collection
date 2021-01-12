package com.utils.tools.research.pojo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("msg")
public class Msg {
    private Long id;
    private String title;
    private String content;
    private Integer type;
    private Long createTime;
    private String expand;
    @TableLogic
    private Integer isDelete;
}
