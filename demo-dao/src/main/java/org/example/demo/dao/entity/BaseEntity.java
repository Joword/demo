package org.example.demo.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import java.util.Date;

/**
 * @Author: Joword<joword @ my.swjtu.edu.cn>
 * @ClassName: BaseEntity
 * @Version: 1.0.0
 * @Date: 2024/8/19 22:46
 * @Desc: BaseEntity
 */
@Data
@ToString
abstract class BaseEntity {

    @TableId(type = IdType.AUTO)
    @Column(name = "`id`")
    private Long id;
    
    @Column(name = "`version`")
    @Version
    private Long version;
    
    @Column(name = "`created_by`",updatable = false)
    @TableField(value = "`created_by`",fill = FieldFill.INSERT)
    private String createdBy;
    
    @Column(name = "`created_time`",updatable = false)
    @TableField(value = "`created_time`",fill = FieldFill.INSERT)
    private Date createdTime;
    
    @Column(name = "`updated_by`",insertable = false)
    @TableField(value = "`updated_by`",fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;
    
    @Column(name = "`updated_time`",insertable = false)
    @TableField(value = "`updated_time`",fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;
    
    @Column(name = "`deleted`")
    @TableLogic
    private Boolean deleted;
}
