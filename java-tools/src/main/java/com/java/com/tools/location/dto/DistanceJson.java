package com.java.com.tools.location.dto;

import lombok.Data;

import java.util.List;

@Data
public class DistanceJson implements java.io.Serializable {

    private List<results> result;

    @Data
    public static class results {
        private List<route> routes;

        @Data
        public static class route {
            private String mode;
            private Double distance;
        }
    }
}
