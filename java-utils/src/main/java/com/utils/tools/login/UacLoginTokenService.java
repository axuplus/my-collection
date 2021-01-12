package com.utils.tools.login;

public interface UacLoginTokenService {

    UacLoginTokenVo generateToken(UacUser uacUser);

    UacLoginTokenVo refreshToken(Long userId);

    void deleteToken(Long userId);

}
