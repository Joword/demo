package org.example.demo.common.exception;

/**
 * @author Joword
 * @date: 2024/2/16 17:08
 * @version: 0.1.0
 * @description: demo runtime exception
 */
public class DemoException extends RuntimeException{
    
    public DemoException(String msg){ super(msg); }
    
    public  DemoException(String msg, Throwable e){ super(msg, e); }
    
    public DemoException(Throwable e){ super(e); }

    public DemoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
