package org.example.demo.manager.model;

import lombok.Data;

import java.util.List;

/**
 * @author Joword
 * @date: 2023/8/3 17:39
 * @version: 0.1.0
 * @description: article object
 */
@Data
public class ArticleDO {
    private String relevance;
    private String title;
    private String authors;
    private String pmid;
    private String pmcid;
    private String date;
    private String journal;
    private String cite;
    private String impactFactor;
    private List<BaseCriteriaDO> rules;
}
