package com.utils.util;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;

/**
 * 图片工具类
 */
public class PictureUtils {

    /**
     * 指定大小进行缩放
     */
    public static void thumbnailWithSize(String filePath, String thumbnailPath, int width, int height) throws IOException {
        Thumbnails.of(filePath).size(width, height).toFile(thumbnailPath);
    }

    /**
     * 按照比例进行缩放
     */
    public static void thumbnailWithScale(String filePath, String thumbnailPath, float scale) throws IOException {
        Thumbnails.of(filePath).scale(scale).toFile(thumbnailPath);
    }


}
