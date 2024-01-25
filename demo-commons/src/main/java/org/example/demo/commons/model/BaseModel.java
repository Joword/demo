package org.example.demo.commons.model;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author Joword
 * @date: 2023/8/1 18:12
 * @version: 0.1.0
 * @description: base model
 */
@Data
public class BaseModel {
    @Column(name = "`status`")
    private int status;
    @Column(name = "`create_time`")
    private String createTime;
    @Column(name = "`update_time`")
    private String updateTime;
}
