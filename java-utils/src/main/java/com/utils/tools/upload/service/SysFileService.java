package com.utils.tools.upload.service;


import com.utils.tools.upload.pojo.SysFileVo;
import org.springframework.web.multipart.MultipartFile;

public interface SysFileService {

    String[] IMG_TYPES = {".png", ".jpg", ".jpeg", ".gif", ".bmp"};

    SysFileVo fileUpload(MultipartFile file);
}
