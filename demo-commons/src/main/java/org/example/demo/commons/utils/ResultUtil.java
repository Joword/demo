package org.example.demo.commons.utils;

import com.bgi.aigi.common.ResultCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Joword
 * @date: 2023/7/12 14:42
 * @version: 0.1.0
 * @description: return dataframe
 */
@Getter
@Setter
@ToString
public class ResultUtil<T> {

    private Integer code;

    private String msg;

    private T data;

    public static <T> ResultUtil<T> successResult(T data) {
        ResultUtil<T> resultUtil = new ResultUtil<T>();
        resultUtil.setCode(ResultCode.RESULT_SUCC);
        resultUtil.setMsg("success");
        resultUtil.setData(data);
        return resultUtil;
    }

    public static <S> ResultUtil<S> failedResult(Integer code, String msg, S data) {
        ResultUtil<S> resultUtil = new ResultUtil<S>();
        resultUtil.setCode(code);
        resultUtil.setMsg(msg);
        resultUtil.setData(data);
        return resultUtil;
    }
}
