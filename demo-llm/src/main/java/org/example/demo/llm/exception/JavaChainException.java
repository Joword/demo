package org.example.demo.llm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Joword
 * @date: 2023/11/17 17:14
 * @version:
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JavaChainException extends RuntimeException{
    private String msg;
    private int code;

    public JavaChainException(IError error) {
        super(error.msg());
        this.code = error.code();
        this.msg = error.msg();
    }

    public JavaChainException(IError error, String messageDetail) {
        super(error.msg());
        this.code = error.code();
        this.msg = error.msg() + " ,detail: " + messageDetail;
    }

    public JavaChainException(String msg) {
        super(msg);
        this.code = CommonError.SYS_ERROR.code();
        this.msg = msg;
    }
    
}
