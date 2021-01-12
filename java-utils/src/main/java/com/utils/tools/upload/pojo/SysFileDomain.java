package com.utils.tools.upload.pojo;


import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "sys_file")
public class SysFileDomain  {

    private Long id;

    private String fileName;

    private String fileSuffix;

    private Long fileSize;

    private Integer fileType;

    private String filePath;

    private String fileUrl;

    private String thumbName;

    private String thumbPath;

    private String thumbUrl;

    private Long createTime;


}
