package org.example.demo.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @Author: Joword<joword @ my.swjtu.edu.cn>
 * @ClassName: UserEntity
 * @Version: 1.0.0
 * @Date: 2024/8/19 22:55
 * @Desc:
 */
@Setter
@Getter
@ToString
@TableName
@Table
public class UserEntity {

    @Column(name = "`user_account`")
    private String userAccount;


    @Column(name = "`user_company_id`")
    private String userCompanyId;


    @Column(name = "`user_name`")
    private String userName;


    @Column(name = "`supplier_id`")
    private String supplierId;


    @Column(name = "`phone`")
    private String phone;


    @Column(name = "`email`")
    private String email;


    @Column(name = "`is_develop_user`")
    private Boolean isDevelopUser;

    /**
     * 状态 (0:启用；1:停用;2:锁定)
     */
    @Column(name = "`status`")
    private String status;
}
