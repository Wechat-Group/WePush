package com.fangxuele.tool.push.ui.dialog.importway.config;

import lombok.Data;

@Data
public class DingImportConfig {

    /**
     * 1:全部，2：按部门
     */
    private Integer userType;

    /**
     * 部门id
     */
    private Long deptId;
}
