package com.coderise.saas.huawei.service.util;

public enum HuaWeiSaasResult {

    /*
    公共

            000000 成功
            000001 鉴权失败
            000002 请求参数不合法
            000003 实例ID不存在（商品续费、过期、资源释放接口可能返回）
            000004 请求处理中
            000005 其它服务内部错误
    新购商品
            000100 无可用实例资源分配
     */


    COMMON_SUCCESS("000000", "Success"),
    COMMON_AUTH_FAILED("000001", "Auth Failed"),
    COMMON_PARAMETER_ILLEGAL("000002","Parameter illegal"),
    COMMON_INSTANCE_ID_NOT_FOUND("000003","Instance id not found"),
    COMMON_REQUEST_PROCESSING("000004","Request Processing"),
    COMMON_INTERNAL_SERVER_ERROR("000005", "Internal Server Error"),
    NEWBUY_NO_RESOURCE("000100","No Resource")
    ;
    public String message;

    public String code;

    HuaWeiSaasResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
