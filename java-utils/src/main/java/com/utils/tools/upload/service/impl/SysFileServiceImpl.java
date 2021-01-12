package com.utils.tools.upload.service.impl;



import com.utils.tools.upload.enums.PubErrorCodeEnum;
import com.utils.tools.upload.exception.PubBizException;
import com.utils.tools.upload.mapper.SysFileMapper;
import com.utils.tools.upload.pojo.SysFileDomain;
import com.utils.tools.upload.pojo.SysFileVo;
import com.utils.tools.upload.service.SysFileService;
import com.utils.util.PathUtils;
import com.utils.util.PictureUtils;
import com.utils.util.StringUtils;
import com.utils.util.id.IdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static com.zaxxer.hikari.util.ClockSource.currentTime;


@Service
@Transactional(rollbackFor = Exception.class)
public class SysFileServiceImpl implements SysFileService {

    @Value(value = "${upload-file.imgPath}")
    private String imgPath;

    @Value(value = "${upload-file.imgUrl}")
    private String imgUrl;

    @Value(value = "${upload-file.imgThumbPath}")
    private String imgThumbPath;

    @Value(value = "${upload-file.imgThumbUrl}")
    private String imgThumbUrl;

    @Autowired
    private SysFileMapper sysFileMapper;


    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public SysFileVo fileUpload(MultipartFile file) {
        SysFileVo sysFileVo = new SysFileVo();
        if (file.isEmpty()) {
            logger.info("上传文件为空");
            throw new PubBizException(PubErrorCodeEnum.PUB10000004);
        }
        logger.info("imgPath {}", imgPath);
        String fileName = file.getOriginalFilename();
        logger.info("fileName {}", fileName);
        String suffix = PathUtils.getExtension(fileName);

        String filePath;
        String thumbName;
        String fileUrl;
        String thumbFilePath;
        String thumbUrl;

        fileName = StringUtils.getRandomString(30) + suffix;
        thumbName = StringUtils.getRandomString(30) + ".png";

        if (!Arrays.asList(IMG_TYPES).contains(suffix)) {
            throw new PubBizException(PubErrorCodeEnum.PUB10000005);
        }
        filePath = imgPath + File.separator + fileName;

        File dest = new File(filePath);
        logger.info("save dest {}", dest.getPath());
        try {
            file.transferTo(dest);

            fileUrl = imgUrl + fileName;
            thumbFilePath = imgThumbPath + File.separator + thumbName;
            thumbUrl = imgThumbUrl + thumbName;
            PictureUtils.thumbnailWithScale(filePath, thumbFilePath, 0.25f);

            SysFileDomain filesDomain = new SysFileDomain();
            try {
                filesDomain.setId(new IdWorker().nextId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            filesDomain.setFileName(fileName);
            filesDomain.setFileSize(file.getSize());
            filesDomain.setFilePath(filePath);
            filesDomain.setFileSuffix(suffix);
            filesDomain.setFileUrl(fileUrl);
            filesDomain.setFileType(1);
            filesDomain.setThumbName(thumbName);
            filesDomain.setThumbPath(thumbFilePath);
            filesDomain.setThumbUrl(thumbUrl);
            filesDomain.setCreateTime(currentTime());
//            insert(filesDomain);
            sysFileVo.setId(filesDomain.getId());
            sysFileVo.setFileUrl(fileUrl);
            sysFileVo.setThumbUrl(thumbUrl);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new PubBizException(PubErrorCodeEnum.PUB10000001);
        }
        return sysFileVo;
    }
}
