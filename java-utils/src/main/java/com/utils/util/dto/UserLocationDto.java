package com.utils.util.dto;

import lombok.Data;

@Data
public class UserLocationDto implements java.io.Serializable {
    private static final long serialVersionUID = 4001338043065432869L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 经度
     */
    private Double lng;
    /**
     * 纬度
     */
    private Double lat;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;

}
