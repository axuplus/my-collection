package com.utils.annotation;

public enum PermissionType {
    ADD("add"),             // 添加权限
    DEL("delete"),          // 删除权限
    EDIT("edit"),           // 编辑权限
    QUERY("query"),         // 查看权限
    SET("set"),             // 角色设置
    ACTIVE("active");       // 激活状态
    private String type;

    PermissionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
