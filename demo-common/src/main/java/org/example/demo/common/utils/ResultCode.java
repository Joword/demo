package org.example.demo.common.utils;

/**
 * @author Joword
 * @date: 2024/2/29 00:00
 * @version: 0.1.0
 * @description: result code
 */
public interface ResultCode {
    Integer RESULT_SUCC = 1000;
    /*服务异常*/
    Integer SERVER_EXCEPTION = 1100;
    Integer SOCKET_EXCEPTION = 1200;
    Integer LOGOUT = 1300;

    /*用户相关的返回码*/
    Integer USERNAME_ISEXTRA = 2001;
    Integer USERNAME_ERROR = 2002;
    Integer PASSWORD_ERROR = 2003;
    Integer LOGINTOKEN_ERROR = 2004;
    Integer REGISTERCODE_ISEXTRA = 2005;//注册验证已发送过,且在一分钟后才能再次发送
    Integer SENDEMAIL_ERROR = 2006;//发送邮件失败
    Integer EMAIL_ISEXTRA = 2007;//注册邮箱已存在
    Integer REGISTERCODE_ERROR = 2008;//注册验证码错误
    Integer EMAILORPASSWORD_ERROR = 2009;//邮箱账号或密码错误
    Integer EMAILUSER_ISEXTRA = 2010;//邮箱用户不存在
    Integer EMAILCODE_HADSEND = 2011;//邮件已发送
    Integer MANUAL_ISEXTRA = 2012;//非解读
    Integer VARIANTID_ISEXTRA = 2013;//VARIANTID不存在
    Integer RSID_ISEXTRA = 2014;//RSID不存在
    Integer HGVSC_ISEXTRA = 2015;//RSID不存在
    Integer REGISTER_ERROR = 2016;//用户注册凭证过期或不存在
    
    //email标题内容有误
    Integer EMAIL_MESSAGE_ERROR = 2018;
    //非开发者用户
    Integer ISNOT_DEVELOPER = 2017;
    Integer EMAIL_AVAILABLE = 2020;
    Integer EMAIL_CODE_OUTDATE = 2021;
    Integer EMAIL_EXCEPTION = 2022;
    Integer EMAIL_NULL = 2023;
    Integer HIGHLIGHT_EXCEPTION = 2024;
    Integer JSON_NO_KEY = 2025;
    Integer HIGHLIGHT_IMAGES_EXCEPTION = 2026;
    Integer USERID_NOTEXIST = 2027; //用户id不能为空;
    Integer PMID_NOTEXIST = 2028; //用户id不能为空;
    /* 位点不能为空 */
    Integer VARIANT_NONEXIST = 2030;
    /* 参考基因组不能为空 */
    Integer GENOME_VERSION_NONEXIST = 2031;
    /* ACMG版本号不能为空 */
    Integer ACMG_VERSION_NONEXIST = 2032;

    /**
     * 解读人员不再使用
     */
    Integer PARSER_REMOVED = 2100;
    /**
     * 开发人员不再使用
     */
    Integer DEVELOPER_REMOVED = 2101;

    /*系统内部服务异常*/
    Integer IMAGE_BIG = 3001;
    Integer DATA_EXCEPTION = 3002;
    Integer GENE_ISNULL = 3003;
    Integer RSID_EXCEPTION = 3004;

    Integer GROUP_ISEXTRA = 4001; //组名已存在
    Integer GROUPNUM_ISEXTRA = 4002; //组员人数超出最大限定值
    Integer GENE_NONEXIST = 4003; //基因不在系统列表中

    /*数据库数据异常：5000-5999*/
    Integer DATA_NONEXIST = 5000;
    Integer DATA_ISEXTRA = 5001;

    Integer REDIS_EXCEPTION = 5002;

    /*参考文献爬虫异常*/
    Integer SELENIUM_CRAWLER_EXCEPTION = 6001;

    Integer POPULATION_FREQUENCY = 7001;

    /*总结报告参数*/
    Integer SUMMARY_REPORT_EXCEPTION = 8000;
    Integer SUMMARY_REPORT_NULL = 8010;
}
