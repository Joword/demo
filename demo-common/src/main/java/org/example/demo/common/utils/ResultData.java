package org.example.demo.common.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Joword
 * @date: 2024/2/28 23:37
 * @version: 0.1.0
 * @description:
 */
@Getter
@Setter
@ToString
public class ResultData<T> {

    private Integer code;

    private String msg;

    private T data;

    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultUtil = new ResultData<T>();
        resultUtil.setCode(ResultCode.RESULT_SUCC);
        resultUtil.setMsg("success");
        resultUtil.setData(data);
        return resultUtil;
    }

    public static <S> ResultData<S> failure(Integer code, String msg, S data) {
        ResultData<S> resultUtil = new ResultData<S>();
        resultUtil.setCode(code);
        resultUtil.setMsg(msg);
        resultUtil.setData(data);
        return resultUtil;
    }
    
}
