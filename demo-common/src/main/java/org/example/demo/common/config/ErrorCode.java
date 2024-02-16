package org.example.demo.common.config;

/**
 * @author Joword
 * @date: 2024/2/16 10:39
 * @version: 0.1.0
 * @description: enum error code
 */
public enum ErrorCode {
    
    SYS000(0,"系统正常")
    ,SYS001(1,"系统错误")
    ,SYS003(3,"用户未登录")
    ,SYS004(4,"程序发生未处理的异常")
    ,SYS005(5,"部分参数为空")
    ,SYS006(6,"当前查询结果不存在")
    ,SYS007(7,"当前内容不为空")
    ,SYS008(8,"部分参数错误")
    ,SYS009(9,"已存在")
    ,SYS010(10,"不可重复")
    ,SYS011(11,"数据已过期")
    ,SYS012(12,"状态不正确")
    ,SYS013(13,"权限不足")
    ,SYS014(14,"调用网关发生异常")
    ,SYS015(15,"数据不存在")
    ,SYS016(16,"数据不一致")
    ,SYS017(17,"暂不可用")
    ,SYS018(18,"调用产品中心发生异常")
    ,SYS019(19,"session已过期")
    ,SYS020(20,"账号不存在")
    ,SYS021(21,"密码错误")
    ,SYS022(22,"账号或密码错误")
    ,SYS023(23,"状态禁用")
    ,SYS024(24,"产品码不正确")
    ,SYS025(25,"旧版产品码，不支持注册菜单。请申请新的产品码")
    ,SYS026(26,"当前版本已经存在")
    ,SYS027(27,"未全部完成更新")
    ,SYS028(28,"无效的验证码")
    ,SYS029(29,"数据已绑定使用")
    ,SYS033(33,"数据被引用")
    ,SYS034(34,"可以使用")
    ,SYS035(35,"验证码不正确");
    
    ErrorCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    public int getCode() {return code;}

    public String getMsg() {return msg;}
}
