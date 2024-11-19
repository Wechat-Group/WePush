package com.fangxuele.tool.push.ui.dialog.importway.config;

import lombok.Data;

@Data
public class WxMpImportConfig {

    /**
     * 1:全部，2：标签取并集，3:标签取交集
     */
    private Integer userType;

    /**
     * 标签id
     */
    private Long tagId;
}
