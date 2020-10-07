package com.utils.util.token;

import lombok.Data;

@Data
public class TokenObject implements java.io.Serializable {

    private String secretKey;
    private Long userId;

    public TokenObject() {

    }

    public TokenObject(String secretKey, Long userId) {
        this.secretKey = secretKey;
        this.userId = userId;
    }

}
