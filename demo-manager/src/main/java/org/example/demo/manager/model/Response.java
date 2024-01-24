package org.example.demo.manager.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author Joword
 * @date: 2023/12/4 10:10
 * @version: 0.1.0
 * @description: response
 */
@Data
public class Response {
    private String sessionId;
    private String tag;
    private String model;
    private JSONObject content;
}
