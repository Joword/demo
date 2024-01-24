package org.example.demo.llm.exception;

/**
 * @author Joword
 * @date: 2023/11/6 10:18
 * @version: 0.1.0
 * @description: 
 */
public enum CommonError implements IError {
    API_KEYS_NOT_NULL(1100, "API KEYs can not be null."),
    NO_ACTIVE_API_KEYS(1100, "No available api keys"),
    NOT_SUPPORT(1101, "该功能未被支持"),
    SYS_ERROR(1010, "系统繁忙"),
    PARAM_ERROR(1011, "参数异常"),
    RETRY_ERROR(1012, "请求异常，请重试！"),
    VECTOR_ERROR(1013, "向量库操作失败"),
    OPENAI_AUTHENTICATION_ERROR(1014, "身份验证无效/提供的API密钥不正确/您必须是组织成员才能使用API"),
    OPENAI_LIMIT_ERROR(1015, "达到请求的速率限制/您超出了当前配额，请检查您的计划与账单详细信息/发动机当前过载，请稍后再试"),
    OPENAI_SERVER_ERROR(1020, "服务请求异常!"),
    ;
    
    private int code;
    private String msg;
    
    CommonError(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
    @Override
    public String msg(){
        return this.msg;
    }
    @Override
    public int code(){
        return this.code;
    }
    
}
