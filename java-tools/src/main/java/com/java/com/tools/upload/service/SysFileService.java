package com.java.com.tools.upload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.com.tools.upload.pojo.SysFileDomain;
import com.java.com.tools.upload.pojo.SysFileVo;
import org.springframework.web.multipart.MultipartFile;

public interface SysFileService {

    String[] IMG_TYPES = {".png", ".jpg", ".jpeg", ".gif", ".bmp"};

    SysFileVo fileUpload(MultipartFile file);
}
