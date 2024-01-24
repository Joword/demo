package org.example.demo.manager.model;

import lombok.Data;

/**
 * @author Joword
 * @date: 2023/8/3 17:44
 * @version: 0.1.0
 * @description: basic criteria data structure
 */
@Data
public class BaseCriteriaDO {
    private String name;
    private String strength;
    private EvidenceDO evidence;
    private Integer like;
}
