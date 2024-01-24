package org.example.demo.llm.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Joword
 * @date: 2023/11/30 21:59
 * @version: 0.1.0
 * @description: send message
 */
@Data
public class SendMessage implements Serializable {
    private List<JSONObject> messages;
}
