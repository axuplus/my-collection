package com.java.com.tools.location.dto;

import lombok.Data;

import java.util.List;

@Data
public class MapDto implements java.io.Serializable {

    private List<result> result;

    @Data
    public static class result{
        private String title;
        private lnglat location;
        @Data
        public static class lnglat {
            private String lng;
            private String lat;
        }
    }
}
