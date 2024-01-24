package org.example.demo.manager.model;

import lombok.Data;

import java.util.List;

/**
 * @author Joword
 * @date: 2023/8/3 17:45
 * @version: 0.1.0
 * @description: Evidence criteria
 */
@Data
public class EvidenceDO {
    private String sentence;
    private List<String> gene;
    private List<String> variant;
    private List<String> disease;
    private Integer like;
}
