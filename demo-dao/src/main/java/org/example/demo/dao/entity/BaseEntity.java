package org.example.demo.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * @author Joword
 * @date: 2023/8/2 09:43
 * @version: 0.1.0
 * @description: base entity
 */
@Data
public class BaseEntity {

    @Column(name = "`id`")
    @TableId(type = IdType.AUTO)
    private String id;

    @Version
    private String version;

    @Column(name = "`status`", updatable = false)
    private Integer status;

    @Temporal(TemporalType.DATE)
    @Column(name = "`create_time`", updatable = false)
    @TableField(value = "`create_time`", fill = FieldFill.INSERT)
    private String createTime;

    @Temporal(TemporalType.DATE)
    @Column(name = "`update_time`", updatable = false)
    @TableField(value = "`update_time`", fill = FieldFill.INSERT)
    private String updateTime;
}
