package com.juanjaun.ph.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRegion implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;
    private Long pid;
    private String name;
    private Integer treeLevel;
    private Integer leaf;
    private Long sort;
}
