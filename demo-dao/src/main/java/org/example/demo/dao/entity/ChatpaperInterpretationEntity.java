package org.example.demo.dao.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author Joword
 * @date: 2023/12/22 09:48
 * @version: 0.1.0
 * @description: 
 */
@Data
public class ChatpaperInterpretationEntity {
    private String variantId;
    private String cHGVS;
    private String genomeVersion;
    private String gene;
    private String rsId;
    private String mutationType;
    private String pHGVS;
    private String gHGVS;
    private String exon;
    private String intron;
    private String metAcmg;
    private String pathogenicity;
    private String summaryEn;
    private String summaryCn;
    private JSONObject acmgs;
    private String createTime;
    private String updateTime;
}
