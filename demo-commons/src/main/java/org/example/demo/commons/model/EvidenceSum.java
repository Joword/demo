package org.example.demo.commons.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Joword
 * @date: 2023/9/19 09:45
 * @version: 0.1.0
 * @description: summary abstract entity
 */
@Data
@ApiModel(value = "Summary entity")
public class EvidenceSum {
    @ApiModelProperty("基因")
    private String gene;
    @ApiModelProperty("Human Genome Variation Socirty")
    private String hgvs;
    @ApiModelProperty("c点")
    private String chgvs;
    @ApiModelProperty("p点")
    private String phgvs;
    private String hgvs1;
    private String hgvs2;
    private String phenotype;
    private String race;
    @ApiModelProperty("样本数")
    private BigDecimal sample;
    @ApiModelProperty("纯合子")
    private BigDecimal homo;
    @ApiModelProperty("杂合子")
    private BigDecimal heter;
    private String score;
    @ApiModelProperty("人群频率")
    private String af;
    private String inheritance;
    private BigDecimal sampleInheritance;
    private String patientRate;
    private String controlRate;
    private double oddRate;
    private String ps4CI;
    private String BP2allele2;
    @ApiModelProperty("疾病输入")
    private String disease;
    @ApiModelProperty("BA1证据项的倍数关系")
    private String ba1threshold;
    @ApiModelProperty("PM2 Supporting证据项描述")
    private String pm2SupportingDesc;
}
