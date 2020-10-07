package com.java.com.tools.style;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.xiaoleilu.hutool.io.FileUtil;

public class StyleTest {
    public static void main(String[] args) {
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file("test.xlsx"));
    }

}
