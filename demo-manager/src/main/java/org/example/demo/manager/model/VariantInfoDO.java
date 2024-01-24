package org.example.demo.manager.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @author Joword
 * @date: 2023/8/3 15:57
 * @version: 0.1.0
 * @description: variant info
 */
@Data
public class VariantInfoDO {

    private JSONObject dataAll;
    private String variantId;
    private String gene;
    private String chgvs;
    private String phgvs;
    private String guideline;
    private CriteriaDO criteria;
    private List<ArticleDO> article;
}
