package org.example.demo.manager.model;

import lombok.Data;

import java.util.List;

/**
 * @author Joword
 * @date: 2023/8/7 10:41
 * @version: 0.1.0
 * @description: chat introduce part
 */
@Data
public class ChatIntroductionDO {
    private String summary;
    private List<ChatAnswerDO> chatHistory;
}
