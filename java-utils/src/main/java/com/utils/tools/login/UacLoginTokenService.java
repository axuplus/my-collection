package com.utils.tools.login;

public interface UacLoginTokenService {

    com.utils.tools.login.UacLoginTokenVo generateToken(com.utils.tools.login.UacUser uacUser);

    com.utils.tools.login.UacLoginTokenVo refreshToken(Long userId);

    void deleteToken(Long userId);

}
