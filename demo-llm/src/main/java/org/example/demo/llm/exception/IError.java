package org.example.demo.llm.exception;

/**
 * @author Joword
 * @date: 2023/11/6 10:15
 * @version: 0.1.0
 * @description: Long-chain model interface
 */
public interface IError {
    String msg();
    
    int code();
}
