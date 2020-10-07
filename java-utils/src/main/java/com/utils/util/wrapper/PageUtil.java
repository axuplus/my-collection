package com.utils.util.wrapper;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageUtil {



    /**
     * The total row. 总条数
     */
    @ApiModelProperty(value = "总条数", name = "totalRow")
    private int totalRow;

    /**
     * The total page.总页数
     */
    @ApiModelProperty(value = "总页数", name = "totalPage")
    private int totalPage;

    /**
     * The start. 开始条数
     */
    @ApiModelProperty(value = "开始条数", name = "pageNum")
    private int pageNum;


    /**
     * The page size.每页条数
     */
    @ApiModelProperty(value = "每页条数", name = "pageSize")
    private int pageSize = 10;


    /**
     * Instantiates a new page util.
     */
    public PageUtil(int total, int pageNum, int pageSize) {
        this.totalRow = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPage = (total + pageSize - 1) / pageSize;
    }

    /**
     * Instantiates a new page util.
     *
     * @param pageNum the current page
     */
    public PageUtil(int pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * Instantiates a new page util.
     *
     * @param pageNum the current page
     * @param pageSize    the page size
     */
    public PageUtil(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

}
