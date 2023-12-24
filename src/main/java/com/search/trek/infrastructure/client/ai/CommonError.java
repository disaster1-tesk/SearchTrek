package com.search.trek.infrastructure.client.ai;


public enum CommonError implements IError {
    API_HOST_NOT_FOUND("500", "API host not found"),
    SYS_ERROR("500", "系统繁忙"),
    PARAM_ERROR("501", "参数异常"),
    RETRY_ERROR("502", "请求异常，请重试~"),
    SUCCESS("0000", "请求成功"),
    PART_SUCCESS("0001", "请求部分成功"),
    ERROR("1111", "请求失败"),
    SYSTEM_ERROR("1000", "系统异常"),
    BUSSINESS_ERROR("2001", "业务逻辑错误"),
    VERIFY_CODE_ERROR("2002", "业务参数错误"),
    SMS_SEND_ERROR("2003", "短信发送失败"),
    UNIQUE_ERROR("2004", "唯一性约束错误"),
    MOBILE_NOT_EXISTS("2005", "手机号不存在，请先进行注册!"),
    RUN_OUT_ERROR("2006", "次数已耗尽"),
    REJECTED_ERROR("2100", "服务端拒绝"),
    DataBase_ERROR("3001", "数据库连接异常"),
    SESSION_ERROR("3002", "会话信息过期"),
    License_ERROR("3003", "License没有提交"),
    Permission_ERROR("3004", "无访问权限"),
    PermPage_ERROR("3014", "暂无查看权限"),      // 暂无查看权限页面跳转
    LoginReDo_ERROR("3005", "账号已在其它地方登录"),
    LOGIN_NONE_ERROR("3006", "未登录"),
    INVALID_TOKEN_ERROR("3007", "无效的token信息"),
    ROBOT_TERMINAL_FORBIDDEN("3008", "禁用机器人终端"),
    WECHAT_MINIAPP_NOT_AUTHORIZED("3009", "微信小程序未授权"),
    WECHAT_MINIAPP_NOT_BIND("3010", "微信小程序未绑定"),
    SECURITY_ERROR("4001", "数据安全校验失败"),
    H5SESSION_ERROR("4003", "接口会话超时"),
    CLIENT_ERROR("4004", "客户端版本过高"),
    DBINIT_ERROR("6001", "数据库未初始化"),
    TENANT_DISABLE_USER("7001", "人员被组织禁用"),
    OBJECT_IS_NULL("7002", "数据对象不存在"),
    OBJECT_IS_EXIST("7003", "数据对象已存在"),
    REDIS_KEY_EXPIRE("8001", "redis的key已过期"),

    //注意：chat专用，其他地方不要用
    CHAT_COMMON_WARN("8002", "chat表单插件提交异常，前端用普通错误显示"),
    CHAT_PERMISSION_ERROR("8003", "无访问权限"),

    //检查产品(应用支付前、购买容量、安装试用期应用)
    ADD_RESOURCE_BEFORE_PRODUCT_INVALID("5001", "没有生效的产品，请先购买产品"),

    HTTP_NOT_MODIFIED("304", "Not Modified"),
    HTTP_BAD_REQUEST("400", "Bad Request"),
    HTTP_NOT_FOUND("404", "Not Found"),
    HTTP_INTERNAL_SERVER_ERROR("500", "Internal Server Error");


    private final String code;
    private final String msg;

    CommonError(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }



    @Override
    public String msg() {
        return this.msg;
    }

    @Override
    public String code() {
        return this.code;
    }
}
