package org.example.demo.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: Joword<joword @ my.swjtu.edu.cn>
 * @ClassName: CompanyInfoEntity
 * @Version: 1.0.0
 * @Date: 2024/8/19 22:49
 * @Desc:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class CompanyInfoEntity extends BaseEntity {

    @Column(name = "`supplier_id`")
    private String supplierId;


    @Column(name = "`company_name`")
    private String companyName;


    @Column(name = "`company_id`")
    private String companyId;


    @Column(name = "`company_code`")
    private String companyCode;

 
    @Column(name = "`company_abbreviation`")
    private String companyAbbreviation;


    @Column(name = "`company_english_name`")
    private String companyEnglishName;


    @Column(name = "`province`")
    private String province;


    @Column(name = "`city`")
    private String city;
    
    @Column(name = "`district`")
    private String district;
    
    @Column(name = "`detailed_address`")
    private String detailedAddress;


    @Column(name = "`zip_code`")
    private String zipCode;
    
    @Column(name = "`switchboard`")
    private String switchboard;
    
    @Column(name = "`fax`")
    private String fax;


    @Column(name = "`website`")
    private String website;
    
    @Column(name = "`duns_number`")
    private String dunsNumber;
    
    @Column(name = "`social_credit_code`")
    private String socialCreditCode;


    @Column(name = "`register_time`")
    private Date registerTime;

    @Column(name = "`currency_type`")
    private String currencyType;


    @Column(name = "`registered_capital`")
    private String registeredCapital;


    @Column(name = "`company_nature`")
    private Integer companyNature;


    @Column(name = "`company_nature_specific`")
    private Integer companyNatureSpecific;


    @Column(name = "`country`")
    private String country;


    @Column(name = "`list_flag`")
    private Boolean listFlag;


    @Column(name = "`business_type`")
    private Integer businessType;
    
    @Column(name = "`main_business`")
    private String mainBusiness;

    @Column(name = "`legal_person`")
    private String legalPerson;

    @Column(name = "`supplier_status`")
    private Integer supplierStatus;
}
